package com.knight.clothes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.clothes.R;
import com.knight.clothes.models.Category;

import java.util.List;

public class CategoryStoreAdapter extends RecyclerView.Adapter<CategoryStoreAdapter.ViewHolder> {

    private List<Category> categories;
    private Context context;
    private int index = 0;

    public void changeIndex(int index){
        this.index = index;
        notifyDataSetChanged();
    }

    public void changeData(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    public CategoryStoreAdapter(List<Category> categories, Context context){
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTitle.setText(categories.get(position).getTitle());
        if(index == position) holder.itemView.setBackgroundColor
                (holder.itemView.getResources().getColor(R.color.colorWhite));
        else holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_category);
        }
    }
}
