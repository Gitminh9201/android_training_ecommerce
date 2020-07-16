package com.knight.f_interesting.utils;

import com.knight.f_interesting.R;

public class AppContracts {

    public static final int PAGINATE_ROW = 10;
    public static final int PAGINATE_GRID_TWO = 12;
    public static final int PAGINATE_GRID_THREE = 15;

    public static int orderStatus(int status){
        int result;
        switch (status){
            case 0:
                result = R.string.order_status_1;
                break;
            case 1:
                result = R.string.order_status_2;
                break;
            case 2:
                result = R.string.order_status_3;
                break;
            case 3:
                result = R.string.order_status_4;
                break;
            default:
                result = R.string.order_status_0;
                break;
        }
        return result;
    }

    public static int orderStatusBorder(int status){
        int result;
        switch (status){
            case 0:
                result = R.drawable.bg_border_order_status_1;
                break;
            case 1:
                result = R.drawable.bg_border_order_status_2;
                break;
            case 2:
                result = R.drawable.bg_border_order_status_3;
                break;
            case 3:
                result = R.drawable.bg_border_order_status_4;
                break;
            default:
                result = R.drawable.bg_border_order_status_0;
                break;
        }
        return result;
    }

    public static int orderStatusBackground(int status){
        int result;
        switch (status){
            case 0:
                result = R.drawable.bg_order_status_1;
                break;
            case 1:
                result = R.drawable.bg_order_status_2;
                break;
            case 2:
                result = R.drawable.bg_order_status_3;
                break;
            case 3:
                result = R.drawable.bg_order_status_4;
                break;
            default:
                result = R.drawable.bg_order_status_0;
                break;
        }
        return result;
    }
}
