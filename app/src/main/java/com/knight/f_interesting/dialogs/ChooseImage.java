package com.knight.f_interesting.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.knight.f_interesting.R;
import com.knight.f_interesting.utils.AppSizes;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class ChooseImage extends DialogFragment {

    private int CODE_CHOOSE_IMAGE = 9630;
    private int CODE_MAKE_IMAGE = 7410;

    private LinearLayout llChooseImage;
    private LinearLayout llMakeImage;

    private void init(View view) {
        llChooseImage = view.findViewById(R.id.ll_choose_image);
        llChooseImage.setLayoutParams(
                new LinearLayout.LayoutParams(AppSizes.getScreenWidth() * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT));
        llMakeImage = view.findViewById(R.id.ll_make_image);
        llMakeImage.setLayoutParams(
                new LinearLayout.LayoutParams(AppSizes.getScreenWidth() * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void listener(final View view, final DialogFragment fragment) {
        llChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImage = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(chooseImage, CODE_CHOOSE_IMAGE);
            }
        });
        llMakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent makeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(makeImage, CODE_MAKE_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 9630:
                    Uri selectedImage = data.getData();
                    File image = new File(selectedImage.toString());
                    Log.e("CHOOSE", image.toString());
                    break;
                case 7410:
//                    Uri uriImage = data.getData();
//                    Log.e("MAKE", data.getData().toString());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_choose_image, container, false);

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        init(v);
        listener(v, this);

        return v;
    }
}
