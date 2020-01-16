package com.example.to_do;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//
 class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

     public interface OnLongClickListener{
         void onItemLongClicked(int position);
     }

     OnLongClickListener longClickListener;
     List<String> items;
     public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
         this.items = items;
         this.longClickListener = longClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
         return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy access to view that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

         TextView tv_item;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
             tv_item.setText(item);
             tv_item.setOnLongClickListener(new View.OnLongClickListener() {
                 @Override
                 public boolean onLongClick(View v) {
                     longClickListener.onItemLongClicked(getAdapterPosition());
                     return true;
                 }
             });
        }
    }
}
