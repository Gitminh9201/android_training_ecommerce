package com.knight.f_interesting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.mvp.cart.CartContract;
import com.knight.f_interesting.utils.AppSizes;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ViewHolder> {

    private List<Product> products;
    private List<Cart> carts;
    private Context context;
    private CartContract.Presenter presenter;

    public ProductCartAdapter(Context context, List<Product> products, List<Cart> carts, CartContract.Presenter presenter) {
        this.products = products;
        this.context = context;
        this.carts = carts;
        this.presenter = presenter;
    }

    public void changeData(List<Product> products, List<Cart> carts) {
        this.products = products;
        this.carts = carts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext()).load(Client.url()
                + products.get(position).getImage()
        ).into(holder.imageView);
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(AppSizes.getScreenWidth() / 4, AppSizes.getScreenWidth() / 4));
        holder.txtPrice.setText(AppUtils.currencyVN(products.get(position).getPrice() * carts.get(position).getQuantity()));
        holder.txtName.setText(products.get(position).getTitle());
        if (carts.get(position).getProductId() == products.get(position).getId())
            holder.txtQty.setText(String.valueOf(carts.get(position).getQuantity()));
        else holder.txtQty.setText("0");

        holder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.db.updateCart(products.get(position).getId(), 0);
                products.remove(position);
                carts.remove(position);
                notifyDataSetChanged();
                presenter.refresh(products, carts);
            }
        });

        holder.abatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = carts.get(position).getQuantity() - 1;
                if(qty <= 0){
                    AppUtils.db.updateCart(products.get(position).getId(), 0);
                    products.remove(position);
                    carts.remove(position);
                }
                else{
                    AppUtils.db.updateCart(products.get(position).getId(), qty);
                    carts.get(position).setQuantity(qty);
                    holder.txtPrice.setText(AppUtils.currencyVN(
                            products.get(position).getPrice() * carts.get(position).getQuantity()));
                }
                notifyDataSetChanged();
                presenter.refresh(products, carts);
            }
        });

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = carts.get(position).getQuantity() + 1;
                AppUtils.db.updateCart(products.get(position).getId(), qty);
                carts.get(position).setQuantity(qty);

                holder.txtPrice.setText(AppUtils.currencyVN(
                        products.get(position).getPrice() * carts.get(position).getQuantity()));
                notifyDataSetChanged();
                presenter.refresh(products, carts);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtPrice;
        TextView txtName;
        TextView txtQty;
        Button increment;
        Button abatement;
        ImageButton ibRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_cart);
            txtPrice = itemView.findViewById(R.id.txt_price_cart);
            txtQty = itemView.findViewById(R.id.txt_qty_cart);
            txtName = itemView.findViewById(R.id.txt_name_cart);
            increment = itemView.findViewById(R.id.btn_increment);
            ibRemove = itemView.findViewById(R.id.ib_remove_cart);
            abatement = itemView.findViewById(R.id.btn_abatement);
        }
    }
}
