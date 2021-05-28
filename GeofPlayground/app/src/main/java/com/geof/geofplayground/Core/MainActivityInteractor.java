package com.geof.geofplayground.Core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geof.geofplayground.Model.Contacts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private MainActivityContract.onOperationListener mListener;
    private ArrayList<Contacts> contacts = new ArrayList<>();

    public MainActivityInteractor(MainActivityContract.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performCreateContact(DatabaseReference reference, Contacts contacts) {

        mListener.onStart();
        reference.child(contacts.getKey()).setValue(contacts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    mListener.onSuccess();
                    mListener.onEnd();
                } else {
                    mListener.onFailure();
                    mListener.onEnd();
                }
            }
        });
    }

    @Override
    public void performReadContact(DatabaseReference reference) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                Contacts contact = snapshot.getValue(Contacts.class);
                contacts.add(contact);
                mListener.onRead(contacts);


            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
