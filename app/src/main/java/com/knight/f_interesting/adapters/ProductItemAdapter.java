package com.knight.f_interesting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.Size;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;

    public ProductItemAdapter(List<Product> products, Context context){
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product item = products.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtBrand.setText("Brand");
        holder.txtPrice.setText(String.valueOf(item.getPrice()) + ".đ");
        holder.txtPriceCompare.setText(String.valueOf(item.getPriceCompare()) + ".đ");
        Glide.with(holder.imageView).load(Client.BASE_URL + APIInterface.middleUrl
            + item.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtTitle;
        TextView txtBrand;
        TextView txtPrice;
        TextView txtPriceCompare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(Size.getScreenWidth() / 3,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView = itemView.findViewById(R.id.iv_product_item);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(Size.getScreenWidth() / 3,
                    Size.getScreenWidth() / 3));
            txtTitle = itemView.findViewById(R.id.txt_title_product_item);
            txtBrand = itemView.findViewById(R.id.txt_brand_product_item);
            txtPrice = itemView.findViewById(R.id.txt_price_product_item);
            txtPriceCompare = itemView.findViewById(R.id.txt_price_compare_product_item);
        }
    }
}
