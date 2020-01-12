package com.example.mobileassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileassignment.models.ViewJob;

import java.util.ArrayList;

public class ViewJobAdapter extends RecyclerView.Adapter<ViewJobAdapter.ExampleViewHolder> {

    private ArrayList<ViewJob> data;
    private OnItemClickListener ltn;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener ltnr){
        ltn = ltnr;
    }


    class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView position;
        public TextView comName;
        public TextView desc;

        public ExampleViewHolder(View itemView, final OnItemClickListener ltnr){
            super(itemView);
            position = itemView.findViewById(R.id.jobProfile);
            comName = itemView.findViewById(R.id.companyName);
            desc = itemView.findViewById(R.id.jobDescription);

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

    public ViewJobAdapter(ArrayList<ViewJob> jobL){
        data= jobL;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_job_list, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, ltn);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ViewJob currentItem = data.get(position);

        holder.position.setText(currentItem.getPosition());
        holder.comName.setText(currentItem.getComName());
        holder.desc.setText(currentItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
