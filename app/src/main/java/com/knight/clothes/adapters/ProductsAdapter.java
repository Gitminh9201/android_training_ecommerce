package com.knight.clothes.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.clothes.R;
import com.knight.clothes.adapters.loadmores.LoadMoreProducts;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.Product;
import com.knight.clothes.utils.AppSizes;
import com.knight.clothes.utils.AppUtils;
import com.knight.clothes.utils.Router;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter {

    private List<Product> products;
    private boolean replace;

    private final int TYPE_LOADING = 0, TYPE_ITEM = 1;
    private final int PAGINATE = 10;

    private LoadMoreProducts loadMore;
    private boolean isLoading;
    private int lastVisible;
    private int totalVisible;

    public ProductsAdapter(List<Product> products, boolean replace, RecyclerView recyclerView, final LoadMoreProducts loadMore){
        this.loadMore = loadMore;
        this.products = products;
        final GridLayoutManager layoutManager = (GridLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore.onLoadMore(layoutManager.getItemCount(), layoutManager.findLastVisibleItemPosition());
                totalVisible = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalVisible <= (lastVisible + PAGINATE)){
                    System.out.println("TRUE");
                    isLoading = true;
                }
            }
        });
    }

    public void changeData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product,parent,false);
            return new ItemViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_more,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder)
        {
            final Product item = products.get(position);
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.txtTitle.setText(item.getTitle());
            if(item.getBrand() != null) viewHolder.txtBrand.setText(item.getBrand().getTitle());
            else  viewHolder.txtBrand.setText("Unnamed");
            viewHolder.txtPrice.setText(String.valueOf(AppUtils.currencyVN(item.getPrice())));
            if (item.getPriceCompare() > 0)
                viewHolder.txtPriceCompare.setText(String.valueOf(AppUtils.currencyVN(item.getPriceCompare())));
            Glide.with(viewHolder.imageView).load(AppClient.url()
                    + item.getImage()).into(viewHolder.imageView);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (replace)
                        Router.navigator(Router.PRODUCT_DETAIL, (Activity) viewHolder.itemView.getContext(),
                                new String[]{String.valueOf(item.getId()), "true"});
                    else
                        Router.navigator(Router.PRODUCT_DETAIL, (Activity) viewHolder.itemView.getContext(),
                                new String[]{String.valueOf(item.getId()),});
                }
            });
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return products.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= products.size() ? TYPE_LOADING : TYPE_ITEM;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_circular);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtBrand;
        TextView txtPrice;
        TextView txtPriceCompare;

        public ItemViewHolder(@NonNull View itemView) {
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
