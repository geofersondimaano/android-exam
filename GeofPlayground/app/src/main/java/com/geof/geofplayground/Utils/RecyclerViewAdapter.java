package com.geof.geofplayground.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geof.geofplayground.Activities.MainActivity;
import com.geof.geofplayground.Fragments.ContactInfoFragment;
import com.geof.geofplayground.Model.Contacts;
import com.geof.geofplayground.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    public ArrayList<Contacts> contacts;
    public onContactViewListener mOnContactViewListener;
    public Context context;
    public int datasize;


    public RecyclerViewAdapter(ArrayList<Contacts> contacts, onContactViewListener onContactViewListener, Context ctx){
        this.contacts = contacts;
        this.mOnContactViewListener = onContactViewListener;
        this.context = ctx;
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, mOnContactViewListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {

        holder.mName.setText(contacts.get(position).getfName() + contacts.get(position).getlName());
        holder.mNumber.setText(contacts.get(position).getNumber());
        holder.mViewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ContactInfoFragment.setKey(contacts.get(position).getKey());
                ((MainActivity)context).setViewPager(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mName;
        public TextView mNumber;
        public TextView mAddress;
        public Button mViewBtn;
        public onContactViewListener mListner;

        public RecyclerViewHolder(@NonNull @NotNull View itemView, onContactViewListener onContactViewListener) {
            super(itemView);

            this.mListner = onContactViewListener;

            mName = (TextView)itemView.findViewById(R.id.tv_name);
            mNumber = (TextView)itemView.findViewById(R.id.tv_number);
            mViewBtn = (Button)itemView.findViewById(R.id.btn_view);
        }

        @Override
        public void onClick(View v) {
            mListner.onContactViewClick(getAdapterPosition());
        }
    }

    public interface onContactViewListener{
        void onContactViewClick(int position);
    }
}
