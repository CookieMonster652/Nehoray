package com.time.timing.UI.ManagerRequest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.time.timing.Data.DataManager;
import com.time.timing.UserModel;
import com.time.timing.Utils.SpacingItemDecorator;
import com.time.timing.databinding.ShiftnameBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ShiftNameVH extends RecyclerView.ViewHolder {

    public ShiftnameBinding binding;
    private ShuftNameAdapter shuftNameAdapter;
    private CollectionReference ManageUserRef;
    private FirebaseAuth Mauth;


    public ShiftNameVH(@NonNull ShiftnameBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.UserRecyclerView.setHasFixedSize(true);
        shuftNameAdapter = new ShuftNameAdapter();

        var space = new SpacingItemDecorator(0, 8, 0, 8);
        binding.UserRecyclerView.setAdapter(shuftNameAdapter);
        binding.UserRecyclerView.addItemDecoration(space);
        ManageUserRef  = FirebaseFirestore.getInstance().collection(DataManager.ManageUsers);
        Mauth = FirebaseAuth.getInstance();
    }

    public void SetUserList(Context context, String Shift, String StartDate, String EndDate){

        binding.UserRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        var FirebaseUser = Mauth.getCurrentUser();
        if(FirebaseUser != null){
            ManageUserRef.document(FirebaseUser.getUid()).collection(StartDate+"-"+EndDate)
                    .document(Shift).collection(DataManager.Users)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                return;
                            }
                            if(!value.isEmpty()){
                                for(var ds : value.getDocumentChanges()){
                                    if(ds.getType() == DocumentChange.Type.ADDED || ds.getType() == DocumentChange.Type.MODIFIED || ds.getType() == DocumentChange.Type.REMOVED){
                                        shuftNameAdapter.setList(value.toObjects(UserModel.class));
                                        shuftNameAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    });
        }
    }


}
