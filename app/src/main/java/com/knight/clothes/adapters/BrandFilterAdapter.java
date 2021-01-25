package com.knight.clothes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.clothes.R;
import com.knight.clothes.models.Brand;

import java.util.List;

public class BrandFilterAdapter extends RecyclerView.Adapter<BrandFilterAdapter.ViewHolder>{

    private List<Brand> brands;
    private Context context;
    private int current;

    public BrandFilterAdapter(Context context, List<Brand> brands){
        this.context = context;
        this.brands = brands;
        this.current = -1;
    }

    public void changeData(List<Brand> brands){
        this.brands = brands;
    }

    public void changeIndex(int index){
        this.current = index;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand_filter,
                parent, false);

        return new BrandFilterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt.setText(brands.get(position).getTitle());
        if(current == position){
            holder.iv.setVisibility(View.VISIBLE);
            holder.txt.setTextColor(holder.itemView.getResources().getColor(R.color.colorPrimary));
        }
        else{
            holder.iv.setVisibility(View.GONE);
            holder.txt.setTextColor(holder.itemView.getResources().getColor(R.color.colorBlack));
        }
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt;
        ImageView iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt_title_brand);
            iv = itemView.findViewById(R.id.iv_choose_filter);
        }
    }
}
