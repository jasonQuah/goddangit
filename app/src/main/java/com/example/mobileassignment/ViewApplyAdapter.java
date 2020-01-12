package com.example.mobileassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileassignment.models.cardViewApplication;

import java.util.ArrayList;

public class ViewApplyAdapter extends RecyclerView.Adapter<ViewApplyAdapter.ExampleViewHolder> {

    private ArrayList<cardViewApplication> data;
    private ViewApplyAdapter.OnItemClickListener ltn;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ViewApplyAdapter.OnItemClickListener ltnr){
        ltn = ltnr;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView address;
        public TextView age;

        public ExampleViewHolder(View itemView, final ViewApplyAdapter.OnItemClickListener ltnr){
            super(itemView);
            username = itemView.findViewById(R.id.username);
            address = itemView.findViewById(R.id.userAddress);
            age = itemView.findViewById(R.id.userAge);

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
        data= applyL;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_application_list, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, ltn);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        cardViewApplication currentItem = data.get(position);

        holder.username.setText(currentItem.getUserName());
        holder.address.setText(currentItem.getUserAddress());
        holder.age.setText(currentItem.getUserAge());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}