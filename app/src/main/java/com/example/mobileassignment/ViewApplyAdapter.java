package com.example.mobileassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileassignment.models.cardViewApplication;

import java.util.ArrayList;

public class ViewApplyAdapter extends RecyclerView.Adapter<ViewApplyAdapter.ExampleViewHolder1> {

    private ArrayList<cardViewApplication> data1;
    private OnItemClickListener ltn;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ViewApplyAdapter.OnItemClickListener ltnr){
        ltn = ltnr;
    }

    class ExampleViewHolder1 extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView address;
        public TextView age;
        public TextView status;

        public ExampleViewHolder1(View itemView, final OnItemClickListener ltnr){
            super(itemView);
            username = itemView.findViewById(R.id.usernname);
            address = itemView.findViewById(R.id.userAddress);
            age = itemView.findViewById(R.id.userAge);
            status = itemView.findViewById(R.id.statusTxt);

            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    if(ltnr != null){
                        int position = getAdapterPosition();
                        ltnr.onItemClick(position);
                    }
                }
            });
        }
    }

    public ViewApplyAdapter(ArrayList<cardViewApplication> applyL){
        data1= applyL;
    }

    @NonNull
    @Override
    public ExampleViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_application_list, parent,false);
        ExampleViewHolder1 evh = new ExampleViewHolder1(v, ltn);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder1 holder, int position) {
        cardViewApplication currentItem1 = data1.get(position);

        holder.username.setText(currentItem1.getUserName());
        holder.address.setText(currentItem1.getUserAddress());
        holder.age.setText(currentItem1.getUserAge());
        holder.status.setText(currentItem1.getStatus());
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }
}