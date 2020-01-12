package com.example.mobileassignment.myApply;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileassignment.R;
import com.example.mobileassignment.jobList.Job;

import java.util.ArrayList;


public class myApplyAdapter extends RecyclerView.Adapter<myApplyAdapter.ExampleViewHolder> {
    private ArrayList<Job> jobList;
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
        public TextView location;
        public TextView condition;

        public ExampleViewHolder(View itemView, final OnItemClickListener ltnr){
            super(itemView);
            position = itemView.findViewById(R.id.JobPosition);
            comName = itemView.findViewById(R.id.CompanyName);
            location = itemView.findViewById(R.id.Location);
            condition = itemView.findViewById(R.id.Condition);

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

    public myApplyAdapter(ArrayList<Job> jobL){
        jobList= jobL;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_joblist, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, ltn);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Job currentItem = jobList.get(position);

        holder.position.setText(currentItem.getPosition());
        holder.comName.setText(currentItem.getComName());
        holder.location.setText(currentItem.getLocation());
        holder.condition.setText(currentItem.getCondition());
    }
    

    @Override
    public int getItemCount() {
        return jobList.size();
    }
}
