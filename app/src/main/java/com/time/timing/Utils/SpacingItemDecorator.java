package com.time.timing.Utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final @NonNull int RightSpaceHeight ;
    private final @NonNull int LeftSpaceHeight;
    private final @NonNull int BottomSpaceHeight;
    private final @NonNull int TopSpaceHeight;

    @Override
    public void getItemOffsets(@androidx.annotation.NonNull Rect outRect, @androidx.annotation.NonNull View view, @androidx.annotation.NonNull RecyclerView parent, @androidx.annotation.NonNull RecyclerView.State state) {
        outRect.right = RightSpaceHeight;
        outRect.left = LeftSpaceHeight;
        outRect.top = TopSpaceHeight;
        outRect.bottom = BottomSpaceHeight;
    }
}
