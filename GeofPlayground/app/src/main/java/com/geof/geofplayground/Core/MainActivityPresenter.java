package com.geof.geofplayground.Core;

import com.geof.geofplayground.Model.Contacts;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivityPresenter implements MainActivityContract.Presenter, MainActivityContract.onOperationListener{

    private MainActivityContract.View mView;
    private MainActivityInteractor mInteractor;

    public MainActivityPresenter(MainActivityContract.View mView) {
        this.mView = mView;
        this.mInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewContact(DatabaseReference reference, Contacts contacts) {
        mInteractor.performCreateContact(reference,contacts);
    }

    @Override
    public void readContact(DatabaseReference reference) {
        mInteractor.performReadContact(reference);
    }

    @Override
    public void onSuccess() {
        mView.onCreateContractSuccess();
    }

    @Override
    public void onFailure() {
        mView.onCreateContractFail();
    }

    @Override
    public void onStart() {
        mView.onProcessStart();
    }

    @Override
    public void onEnd() {
        mView.onProcessEnd();
    }

    @Override
    public void onRead(ArrayList<Contacts> contacts) {
        mView.onContactRead(contacts);
    }
}
