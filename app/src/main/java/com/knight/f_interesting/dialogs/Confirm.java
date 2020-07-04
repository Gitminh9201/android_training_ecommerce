package com.knight.f_interesting.dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.knight.f_interesting.R;

public class Confirm extends DialogFragment {

    private TextView txtTitle;
    private Button btnNext;
    private Button btnCancel;

    private String title;
    private View.OnClickListener action;

    public Confirm(String title, View.OnClickListener onClickListener){
        this.title = title;
        action = onClickListener;
    }

    private void init(View view){
        txtTitle = view.findViewById(R.id.txt_title_confirm);
        btnCancel = view.findViewById(R.id.btn_cancel_confirm);
        btnNext = view.findViewById(R.id.btn_accept_confirm);

        txtTitle.setText(title);
    }
    private void listener(View view){
        btnNext.setOnClickListener(action);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_confirm, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        init(v);
        listener(v);

        return v;
    }
}
