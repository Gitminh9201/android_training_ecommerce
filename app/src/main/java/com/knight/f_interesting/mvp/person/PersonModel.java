package com.knight.f_interesting.mvp.person;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.ContextBus;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonModel implements PersonContract.Model {
    @Override
    public void getUser(final OnFinishedListener onFinishedListener, Context context) {
        User user = UserBus.current();
        if (user != null && user.getName() != null) {
            onFinishedListener.onFinish(user);
        } else requestApi(onFinishedListener, context);
    }

    @Override
    public void uploadAvatar(final OnFinishedListener onFinishedListener, Uri uri) {
        if (uri == null) return;
        File file = new File(getRealPathFromURI(uri));
        RequestBody requestBody = RequestBody.create(
                MediaType.parse(ContextBus.current().getContentResolver().getType(uri)),
                file);
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.uploadAvatar(AppClient.headers(), filePart);
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinish(response.body().getData());
                else
                    onFinishedListener.onFinish(new User());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = ContextBus.current().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void requestApi(final OnFinishedListener onFinishedListener, Context context) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1)
                    onFinishedListener.onFinish(response.body().getData());
                else
                    onFinishedListener.onFinish(new User());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
