package com.knight.f_interesting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Gallery;
import com.knight.f_interesting.utils.AppSizes;

import java.util.List;

public class GalleryItemAdapterDetail extends RecyclerView.Adapter<GalleryItemAdapterDetail.ViewHolder> {
    private List<Gallery> galleries;
    private int selected;

    public GalleryItemAdapterDetail(List<Gallery> galleries){
        this.galleries =galleries;
        this.selected = -1;
    }

    public GalleryItemAdapterDetail(List<Gallery> galleries, int selected){
        this.galleries = galleries;
        this.selected = selected;
    }

    public void changeData(List<Gallery> galleries, int selected){
        this.galleries = galleries;
        this.selected = selected;
        notifyDataSetChanged();
    }

    public void changeData(List<Gallery> galleries){
        this.galleries = galleries;
        notifyDataSetChanged();
    }

    public void changeData(int selected){
        this.selected = selected;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Gallery item = galleries.get(position);
        if(selected == position || (selected == -1 && position == 0)) {
            holder.layout.setBackgroundResource(R.drawable.bg_border_order_status_2);
        }else holder.layout.setBackgroundResource(R.color.colorWhite);
        Glide.with(holder.itemView).load(AppClient.url()
                + item.getImage())
                .into(holder.image);
        holder.itemView.setLayoutParams(
                new FrameLayout.LayoutParams(AppSizes.getScreenWidth()  /6,
                        AppSizes.getScreenWidth()  / 6));
        holder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        ProgressBar progressBar;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.rl_layout_item_gallery);
            image = itemView.findViewById(R.id.iv_item_gallery);
            progressBar = itemView.findViewById(R.id.pb_load_gallery_detail);
        }
    }
}
