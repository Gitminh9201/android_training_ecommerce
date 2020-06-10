package com.knight.f_interesting.utils;

import android.app.Activity;
import android.content.Intent;

public class Route {
    public static void goToDetail(Activity fist, Class second, int id){
        Intent intent = new Intent(fist, second);
        intent.putExtra("id", id);
        fist.startActivity(intent);
    }
    public static int inDetail(Activity activity){
        Intent intent = activity.getIntent();
        return intent.getIntExtra("id", 1);
    }
}
