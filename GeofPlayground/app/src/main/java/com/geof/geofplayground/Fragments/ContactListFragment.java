package com.geof.geofplayground.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geof.geofplayground.Activities.MainActivity;
import com.geof.geofplayground.Core.MainActivityContract;
import com.geof.geofplayground.Core.MainActivityPresenter;
import com.geof.geofplayground.Model.Contacts;
import com.geof.geofplayground.R;
import com.geof.geofplayground.Utils.Config;
import com.geof.geofplayground.Utils.RecyclerViewAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactListFragment extends Fragment implements MainActivityContract.View, RecyclerViewAdapter.onContactViewListener{

    public MainActivityPresenter mPresenter;
    public DatabaseReference mReference;
    public RecyclerView mRecyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;
    public ArrayList<Contacts> mContact;
    public Button loadList;
    public Button addContact;
    public Boolean dataLoaded;
    public Context ctx;

    public ContactListFragment(Context context) {
        this.ctx = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_contact_fragment, container, false);
        loadList = (Button)view.findViewById(R.id.btn_load);
        addContact = (Button)view.findViewById(R.id.btn_addcntcs);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recylerview);
        mContact = new ArrayList<Contacts>();
        mReference = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);
        mPresenter = new MainActivityPresenter(this);
        dataLoaded=false;
        mPresenter.readContact(mReference);
        mContact.clear();
        loadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.readContact(mReference);
                mContact.clear();
            }
        });
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)ctx).setViewPager(1);
            }
        });
        return view;
    }

    @Override
    public void onCreateContractSuccess() {

    }

    @Override
    public void onCreateContractFail() {

    }

    @Override
    public void onProcessStart() {

    }

    @Override
    public void onProcessEnd() {

    }

    @Override
    public void onContactRead(ArrayList<Contacts> contacts) {

            this.mContact = contacts;
            recyclerViewAdapter = new RecyclerViewAdapter(mContact, this, this.getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(recyclerViewAdapter);
            mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onContactViewClick(int position) {

    }

}
