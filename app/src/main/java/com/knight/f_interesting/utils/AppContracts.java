package com.knight.f_interesting.utils;

import com.knight.f_interesting.R;

public class AppContracts {
    public static int orderStatus(int status){
        int result;
        switch (status){
            case 1:
                result = R.string.order_status_1;
                break;
            case 2:
                result = R.string.order_status_2;
                break;
            case 3:
                result = R.string.order_status_3;
                break;
            case 4:
                result = R.string.order_status_4;
                break;
            default:
                result = R.string.order_status_0;
                break;
        }
        return result;
    }
}
