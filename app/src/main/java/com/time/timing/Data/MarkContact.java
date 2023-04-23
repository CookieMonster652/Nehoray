package com.time.timing.Data;

import com.time.timing.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class MarkContact {

    private static List<ContactModel> contact = new ArrayList<>();

    public static List<ContactModel> SetMarkNumber(ContactModel contactModel) {
        contact.add(contactModel);
        return contact;
    }

    public static List<ContactModel> GetContact(){
            return contact;
    }

    public static void ClearContact(){
        contact.clear();
    }
}
