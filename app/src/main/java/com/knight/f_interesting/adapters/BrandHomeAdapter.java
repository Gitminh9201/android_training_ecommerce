package com.knight.f_interesting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.utils.AppSizes;

import java.util.ArrayList;
import java.util.List;

public class BrandHomeAdapter extends BaseAdapter {

    private List<Brand> brands;
    private Context context;
    LayoutInflater inflater;


    public BrandHomeAdapter(Context context) {
        this.brands = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void addItem(Brand brand) {
        this.brands.add(brand);
    }

    @Override
    public int getCount() {
        return brands.size();
    }

    @Override
    public Object getItem(int position) {
        return brands.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.brand_home, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.iv_brand_home);
            viewHolder.textView = convertView.findViewById(R.id.txt_brand_home);
            viewHolder.imageView.setLayoutParams(
                    new LinearLayout.LayoutParams((AppSizes.getScreenWidth() - (int) convertView.getResources()
                    .getDimension(R.dimen.card_margin) * 8) / 4,
                    (int) (AppSizes.getScreenWidth() - AppSizes.convertDpToPx(context, (int) (convertView.getResources()
                            .getDimension(R.dimen.padding_grid_home) * 2) + (int) convertView.getResources()
                            .getDimension(R.dimen.card_margin) * 8)) / 4));
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.textView.setText(brands.get(position).getTitle());
        Glide.with(convertView).load(Client.url()
                + brands.get(position).getImage())
                .into(viewHolder.imageView);
        convertView.setLayoutParams(
                new LinearLayout.LayoutParams(AppSizes.getScreenWidth() / 4,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
