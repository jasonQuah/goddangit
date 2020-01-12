package com.example.mobileassignment.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileassignment.R;
import com.example.mobileassignment.models.Category;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ExampleViewHolder> {
    private ArrayList<Category> categoryList;
    private OnItemClickListener ltn;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener ltnr){
        ltn = ltnr;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ExampleViewHolder(View itemView, final OnItemClickListener ltnr){
            super(itemView);
            name = itemView.findViewById(R.id.categoryName);

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

    public CategoryAdapter(ArrayList<Category> cateList){
        categoryList= cateList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v, ltn);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Category currentItem = categoryList.get(position);

        holder.name.setText(currentItem.getName());

    }
    

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
