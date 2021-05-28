package com.geof.geofplayground.Core;

import com.geof.geofplayground.Model.Contacts;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public interface MainActivityContract {

    interface View{
        void onCreateContractSuccess();
        void onCreateContractFail();
        void onProcessStart();
        void onProcessEnd();
        void onContactRead(ArrayList<Contacts>contacts);
    }

    interface Presenter{
        void createNewContact(DatabaseReference reference, Contacts contacts);
        void readContact(DatabaseReference reference);
    }

    interface Interactor{
        void performCreateContact(DatabaseReference reference, Contacts contacts);
        void performReadContact(DatabaseReference reference);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
        void onRead(ArrayList<Contacts>contacts);
    }
}
