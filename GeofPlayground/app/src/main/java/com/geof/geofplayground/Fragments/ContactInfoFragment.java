package com.geof.geofplayground.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geof.geofplayground.Activities.MainActivity;
import com.geof.geofplayground.Core.MainActivityContract;
import com.geof.geofplayground.Core.MainActivityPresenter;
import com.geof.geofplayground.Model.ContactInfo;
import com.geof.geofplayground.Model.Contacts;
import com.geof.geofplayground.R;
import com.geof.geofplayground.Utils.Config;
import com.geof.geofplayground.Utils.ContactInfoRecyclerViewAdapter;
import com.geof.geofplayground.Utils.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContactInfoFragment extends Fragment implements MainActivityContract.View, MainActivityContract.Presenter, ContactInfoRecyclerViewAdapter.onContactViewListener, ValueEventListener {

    private static String qKey;
    private Query qry;
    public MainActivityPresenter mPresenter;
    public DatabaseReference mReference;
    public RecyclerView mRecyclerView;
    public ContactInfoRecyclerViewAdapter cInfRecyclerViewAdapter;
    public TextView head;
    public TextView body;
    public Button listView;
    public ArrayList<Contacts> contacts;
    public ContactInfo contactInfo;
    public ArrayList<ContactInfo> fetchedData;
    public ArrayList<ContactInfo> finalData;
    public ContactInfo cInfo;
    public List<HashMap<String, String>> infoList;
    ObjectMapper mapper;
    public ArrayList<ContactInfo> initialData;
    private Boolean runQuery;
    public Context ctx;

    public ContactInfoFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        runQuery = true;
        View view = inflater.inflate(R.layout.contact_info_fragment, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.contactRecView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);
        mPresenter = new MainActivityPresenter(this);
        if(qKey != null){
            mPresenter.readContact(mReference);
        }
        listView = (Button)view.findViewById(R.id.btn_listview);
        cInfo = new ContactInfo();
        head = (TextView)view.findViewById(R.id.tv_name);
        body = (TextView)view.findViewById(R.id.tv_number);
        fetchedData = new ArrayList<ContactInfo>();
        initialData = new ArrayList<>();

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)ctx).setViewPager(0);
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
        if(runQuery && qKey != null){
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);
            cInfRecyclerViewAdapter = new ContactInfoRecyclerViewAdapter(initialData, this,this.getActivity());
            mRecyclerView.setAdapter(cInfRecyclerViewAdapter);
            infoList = new ArrayList<>();
            Query query = FirebaseDatabase.getInstance().getReference("User").orderByChild("key").equalTo(qKey);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        Contacts cntact = ds.getValue(Contacts.class);
                        Gson gson = new Gson();
                        String json = gson.toJson(cntact);
                        Map<String, String> map = new HashMap<>();
                        Iterator<String> sIterator = null;
                        try {
                            sIterator = new JSONObject(json).keys();
                            int i = 0;
                            while (sIterator.hasNext()) {
                                String key = sIterator.next();
                                String value = new JSONObject(json).getString(key);
                                map.put(key, value);
                                if(!key.equals("key")){
                                    fetchedData.add(new ContactInfo(Util.ResponseFormatter(key), value));
                                }
                                finalData = fetchedData;
                                String json2 = gson.toJson(fetchedData);
                                System.out.println("loop data"+ json2);
                                i++;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String json2 = gson.toJson(finalData);
                        System.out.println("post loop data"+ json2);
                    }
                    cInfRecyclerViewAdapter = new ContactInfoRecyclerViewAdapter(finalData, cInfRecyclerViewAdapter.mOnContactViewListener, getActivity());
                    mRecyclerView.setAdapter(cInfRecyclerViewAdapter);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.d("test",error.toString());
                }
            });
            runQuery = false;
            qKey = null;
        }
    }

    @Override
    public void onContactViewClick(int position) {

    }

    @Override
    public void createNewContact(DatabaseReference reference, Contacts contacts) {

    }

    @Override
    public void readContact(DatabaseReference reference) {

    }

    @Override
    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull @NotNull DatabaseError error) {
        Log.d("Database error =====",error.toString());
    }

    public static void setKey(String ky) {
        qKey = ky;
    }

}
