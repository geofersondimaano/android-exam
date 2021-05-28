package com.geof.geofplayground.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.geof.geofplayground.Core.MainActivityContract;
import com.geof.geofplayground.Core.MainActivityPresenter;
import com.geof.geofplayground.Fragments.ContactInfoFragment;
import com.geof.geofplayground.Fragments.ContactListFragment;
import com.geof.geofplayground.Fragments.CreateContactFragment;
import com.geof.geofplayground.Model.Contacts;
import com.geof.geofplayground.R;
import com.geof.geofplayground.Utils.Config;
import com.geof.geofplayground.Utils.RecyclerViewAdapter;
import com.geof.geofplayground.Utils.SectionStatePagerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CreateContactFragment.createContactFragmentListener , MainActivityContract.View {

    public MainActivityPresenter mPresenter;
    public ProgressBar mProgressBar;
    public DatabaseReference mReference;
    public RecyclerView mRecyclerView;
    public RecyclerViewAdapter recyclerViewAdapter;
    private SectionStatePagerAdapter mSectionStatePagerAdapter;
    public ViewPager mViewPager;
    private ContactListFragment contactListFragment;
    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerview);
        mReference = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);
        mPresenter = new MainActivityPresenter(this);
        mSectionStatePagerAdapter = new SectionStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager = findViewById(R.id.fragContainer);
        setupViewPagerAdapter(mViewPager);


    }

    private void setupViewPagerAdapter(ViewPager viewPager){
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ContactListFragment(ctx), "FirstFragment");
        adapter.addFragment(new CreateContactFragment(ctx), "SecondFragment");
        adapter.addFragment(new ContactInfoFragment(ctx), "ThirdFragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }


    @Override
    public void saveContact(Contacts contacts) {
        String key = mReference.push().getKey();
        Contacts newContact = new Contacts(contacts.getfName(), contacts.getlName(), contacts.getBirthday(),contacts.getAge(),contacts.getEmail(), contacts.getNumber(), contacts.getAddress(),contacts.getContactPerson(), contacts.getCpNumber(), key);
        mPresenter.createNewContact(mReference,newContact);
    }

    @Override
    public void onCreateContractSuccess() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewPager(0);
            }
        }, 100);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Successfully Added!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateContactFragment.clearInputCache();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onCreateContractFail() {
        Toast.makeText(MainActivity.this, "Unable to process your request", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
//        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProcessEnd() {
//        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onContactRead(ArrayList<Contacts> contacts) {
    }

}