package com.knight.f_interesting.utils;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.dialogs.Confirm;

import java.text.NumberFormat;
import java.util.Locale;

public class AppUtils {

    static public DatabaseHandler db;

    static public String currencyVN(int price){
        if(price < 0)return "0 Đ";
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(locale);
        return currencyVN.format(price).toUpperCase();
    }

    static public void setUpDB(Context context){
        db = new DatabaseHandler(context);
    }

    static public void snackbar(View view, int id){
        Snackbar.make(view.findViewById(id), view.getResources().getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
    static public void snackbar(View view, int id, String msg){
        Snackbar.make(view.findViewById(id), msg,
                Snackbar.LENGTH_LONG).show();
    }
    
    static public void showDialogConfirm(String title, View.OnClickListener action, FragmentManager manager){
        DialogFragment dialogFragment = new Confirm(title, action);
        dialogFragment.show(manager.beginTransaction(), "dialog_confirm");
    }

    static public void hideDialog(FragmentManager manager, String tag){
        Fragment prev = manager.findFragmentByTag(tag);
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }
}
