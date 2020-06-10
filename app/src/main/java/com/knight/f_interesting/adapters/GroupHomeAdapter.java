package com.knight.f_interesting.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.customs.RecyclerItemClickListener;
import com.knight.f_interesting.models.Group;
import com.knight.f_interesting.mvp.detail.DetailActivity;
import com.knight.f_interesting.utils.Router;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Group group = groups.get(position);
        ProductItemAdapter productItemAdapter = new ProductItemAdapter(group.getProducts(), this.context);
        holder.txtTitle.setText(group.getTitle());
        holder.recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(llm);
        holder.recyclerView.setAdapter(productItemAdapter);

        holder.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                holder.itemView.getContext(), holder.recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Router.goToDetail((Activity) holder.itemView.getContext(), DetailActivity.class, group.getProducts().get(position).getId());
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
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
