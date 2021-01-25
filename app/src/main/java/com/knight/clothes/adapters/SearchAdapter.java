package com.knight.clothes.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.clothes.R;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.ProductSuggest;
import com.knight.clothes.utils.Router;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<ProductSuggest> suggests;
    private View.OnClickListener action;
    private Activity activity;

    public SearchAdapter(List<ProductSuggest> suggests, View.OnClickListener action, Activity activity) {
        this.suggests = suggests;
        this.action = action;
        this.activity = activity;
    }

    public void refresh(List<ProductSuggest> suggests) {
        this.suggests = suggests;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductSuggest item = suggests.get(position);
        holder.txtTitle.setText(item.getTitle());
        Glide.with(holder.imageView).load(AppClient.url()
                + item.getImage()).into(holder.imageView);
        if (item.getImage() == null)
            holder.view.setOnClickListener(action);
        else holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.PRODUCT_DETAIL, activity,
                        new String[]{String.valueOf(item.getId())});
            }
        });
    }

    @Override
    public int getItemCount() {
        return suggests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ImageView imageView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_suggest);
            imageView = itemView.findViewById(R.id.iv_suggest);
            this.view = itemView;
        }
    }
}
