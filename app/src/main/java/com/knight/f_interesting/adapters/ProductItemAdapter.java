package com.knight.f_interesting.adapters;

import android.app.Activity;
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
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.AppSizes;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

import java.util.List;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.ViewHolder> {

    private List<Product> products;
    private Context context;
    private boolean replace;

    public ProductItemAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
        this.replace = false;
    }

    public ProductItemAdapter(List<Product> products, Context context, boolean replace) {
        this.products = products;
        this.context = context;
        this.replace = replace;
    }

    public void changeData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product item = products.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtBrand.setText("Brand");
        holder.txtPrice.setText(String.valueOf(AppUtils.currencyVN(item.getPrice())));
        if (item.getPriceCompare() > 0)
            holder.txtPriceCompare.setText(String.valueOf(AppUtils.currencyVN(item.getPriceCompare())));
        Glide.with(holder.imageView).load(AppClient.url()
                + item.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (replace)
                    Router.navigator(Router.PRODUCT_DETAIL, (Activity) holder.itemView.getContext(),
                            new String[]{String.valueOf(item.getId()), "true"});
                else
                    Router.navigator(Router.PRODUCT_DETAIL, (Activity) holder.itemView.getContext(),
                            new String[]{String.valueOf(item.getId()),});
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtTitle;
        TextView txtBrand;
        TextView txtPrice;
        TextView txtPriceCompare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_item);
            txtTitle = itemView.findViewById(R.id.txt_title_product_item);
            txtBrand = itemView.findViewById(R.id.txt_brand_product_item);
            txtPrice = itemView.findViewById(R.id.txt_price_product_item);
            txtPriceCompare = itemView.findViewById(R.id.txt_price_compare_product_item);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(AppSizes.getScreenWidth() / 3,
                    AppSizes.getScreenWidth() / 3));
            itemView.setLayoutParams(new LinearLayout.LayoutParams(AppSizes.getScreenWidth() / 3,
                    (int) (AppSizes.getScreenWidth() / 3 + heightItemInView(txtTitle)
                            + heightItemInView(txtBrand) + heightItemInView(txtPrice))));
        }

        private float heightItemInView(TextView txt) {
            return txt.getTextSize() * 2;
        }
    }
}
