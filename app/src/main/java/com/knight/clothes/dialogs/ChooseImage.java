package com.knight.clothes.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.knight.clothes.R;
import com.knight.clothes.mvp.person.PersonContract;
import com.knight.clothes.mvp.person_information.InformationContract;
import com.knight.clothes.utils.AppSizes;

import java.io.ByteArrayOutputStream;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class ChooseImage extends DialogFragment {

    private int CODE_CHOOSE_IMAGE = 9630;
    private int CODE_MAKE_IMAGE = 7410;

    private LinearLayout llChooseImage;
    private LinearLayout llMakeImage;
    private PersonContract.Presenter presenterPerson;
    private InformationContract.Presenter presenterInfo;

    private Activity activity;

    public ChooseImage(Activity activity, PersonContract.Presenter presenter) {
        this.activity = activity;
        this.presenterPerson = presenter;
    }

    public ChooseImage(Activity activity, InformationContract.Presenter presenter) {
        this.activity = activity;
        this.presenterInfo = presenter;
    }

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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    chooseImage();
                    return;
                }
                int result = ContextCompat.checkSelfPermission(getContext(),
                        READ_EXTERNAL_STORAGE);
                if (result == PackageManager.PERMISSION_GRANTED) {
                    chooseImage();
                } else {
                    requestPermissions(new String[]{
                            READ_EXTERNAL_STORAGE}, CODE_CHOOSE_IMAGE);
                }
            }
        });
        llMakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resultCamera = ContextCompat.checkSelfPermission(getContext(),
                        CAMERA);
                int resultWrite = ContextCompat.checkSelfPermission(getContext(),
                        WRITE_EXTERNAL_STORAGE);
                if (resultCamera == PackageManager.PERMISSION_GRANTED
                        && resultWrite == PackageManager.PERMISSION_GRANTED) {
                    makeImage();
                } else {
                    requestPermissions(new String[]{
                            CAMERA, WRITE_EXTERNAL_STORAGE}, CODE_MAKE_IMAGE);
                }
            }
        });
    }

    public void chooseImage() {
        Intent chooseImage = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chooseImage, CODE_CHOOSE_IMAGE);
    }

    public void makeImage() {
        Intent makeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (makeImage.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(makeImage, CODE_MAKE_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        if (requestCode == CODE_CHOOSE_IMAGE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(activity.getApplicationContext(), R.string.permission_denied,
                        Toast.LENGTH_LONG).show();
            }
        else if (requestCode == CODE_MAKE_IMAGE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                makeImage();
            } else {
                Toast.makeText(activity.getApplicationContext(), R.string.permission_denied,
                        Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CODE_CHOOSE_IMAGE) {
                Uri makeImage = data.getData();
                if(presenterPerson != null)
                presenterPerson.uploadAvatar(makeImage);
                else presenterInfo.uploadAvatar(makeImage);
                dismiss();
            } else if (requestCode == CODE_MAKE_IMAGE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), imageBitmap, "Title", null);
                    Uri uri = Uri.parse(path);
                    if(presenterPerson != null)
                        presenterPerson.uploadAvatar(uri);
                    else presenterInfo.uploadAvatar(uri);
                    dismiss();
                }
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
