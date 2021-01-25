package com.knight.clothes.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.knight.clothes.R;
import com.knight.clothes.utils.Router;

public class RequestLogin  extends DialogFragment {

    private String title;
    private Activity activity;

    public RequestLogin(String title, Activity activity){
        this.activity = activity;
        this.title = title;
    }

    private TextView txtTitle;
    private Button btnDispose;
    private Button btnAccept;

    private void init(View view){
        txtTitle = view.findViewById(R.id.txt_title_alert);
        btnAccept = view.findViewById(R.id.btn_accept_alert);
        btnDispose = view.findViewById(R.id.btn_dispose_alert);

        txtTitle.setText(title);
    }

    private void listener(final View view, final DialogFragment fragment){
        btnDispose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.dismiss();
                Router.navigator(Router.LOGIN, getActivity(), null);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_request_login, container, false);

        init(v);
        listener(v, this);

        return v;
    }
}