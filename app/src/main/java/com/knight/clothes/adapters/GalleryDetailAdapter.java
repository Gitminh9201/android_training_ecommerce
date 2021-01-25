package com.knight.clothes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.knight.clothes.R;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.models.Gallery;

import java.util.List;

public class GalleryDetailAdapter extends PagerAdapter {

    private List<Gallery> gallery;
    private Context context;

    public GalleryDetailAdapter(Context context, List<Gallery> gallery){
        this.context = context;
        this.gallery = gallery;
    }

    public void changeData(List<Gallery> gallery){
        this.gallery = gallery;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }

    @Override
    public int getCount() {
        return gallery.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_gallery, null);
        ImageView imageView = view.findViewById(R.id.iv_item_gallery);
        ProgressBar progressBar = view.findViewById(R.id.pb_load_gallery_detail);

        Glide.with(view).load(AppClient.url()
                + gallery.get(position).getImage())
                .placeholder(R.drawable.border_image_brand_home)
                .into(imageView);
        progressBar.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }
}
