package com.knight.f_interesting.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.models.Group;

import java.util.List;

public class GroupHomeAdapter extends RecyclerView.Adapter<GroupHomeAdapter.ViewHolder> {

    private List<Group> groups;
    private Context context;

    public GroupHomeAdapter(List<Group> groups, Context context){
        this.groups = groups;
        this.context = context;
    }

    public void changeData(List<Group> groups){
        this.groups = groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groups.get(position);
        ProductItemAdapter productItemAdapter = new ProductItemAdapter(group.getProducts(), this.context);
        holder.txtTitle.setText(group.getTitle());
        holder.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(llm);
        holder.recyclerView.setAdapter(productItemAdapter);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title_group);
            recyclerView = itemView.findViewById(R.id.rv_group_product);
        }
    }
}
