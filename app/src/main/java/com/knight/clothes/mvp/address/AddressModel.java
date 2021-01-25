package com.knight.clothes.mvp.address;

import android.content.Context;
import android.util.Log;

import com.knight.clothes.models.Address;
import com.knight.clothes.utils.AppShared;

public class AddressModel implements AddressContract.Model{
    @Override
    public void getData(OnFinishedListener onFinishedListener, Context context) {
        Address data = AppShared.getAddress(context);
        if(data != null)Log.e("data model:", data.getPhone());
        onFinishedListener.onFinished(data);
    }
}
