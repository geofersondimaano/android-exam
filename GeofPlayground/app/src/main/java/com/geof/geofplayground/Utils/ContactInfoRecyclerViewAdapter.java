package com.geof.geofplayground.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geof.geofplayground.Model.ContactInfo;
import com.geof.geofplayground.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContactInfoRecyclerViewAdapter extends RecyclerView.Adapter<ContactInfoRecyclerViewAdapter.ContactInfoRecyclerViewHolder>{

    public ArrayList<ContactInfo> contactInfo;
    public onContactViewListener mOnContactViewListener;
    public Context context;
    public int datasize;



    public ContactInfoRecyclerViewAdapter(ArrayList<ContactInfo> contInfo, onContactViewListener onContactViewListener, Context ctx){
        this.contactInfo = contInfo;
        this.mOnContactViewListener = onContactViewListener;
        this.context = ctx;
    }

    @NonNull
    @NotNull
    @Override
    public ContactInfoRecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_info_layout,null);
        ContactInfoRecyclerViewHolder recyclerViewHolder = new ContactInfoRecyclerViewHolder(view, mOnContactViewListener);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactInfoRecyclerViewAdapter.ContactInfoRecyclerViewHolder holder, int position) {
        holder.mName.setText(contactInfo.get(position).getHeader());
        holder.mNumber.setText(contactInfo.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        if(contactInfo == null){
            return 0;
        }
        return contactInfo.size();
    }

    public class ContactInfoRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mName;
        public TextView mNumber;
        public onContactViewListener mListner;

        public ContactInfoRecyclerViewHolder(@NonNull @NotNull View itemView, onContactViewListener onContactViewListener) {
            super(itemView);

            this.mListner = onContactViewListener;

            mName = (TextView)itemView.findViewById(R.id.tv_header);
            mNumber = (TextView)itemView.findViewById(R.id.tv_body);
        }

        public ContactInfoRecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
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
