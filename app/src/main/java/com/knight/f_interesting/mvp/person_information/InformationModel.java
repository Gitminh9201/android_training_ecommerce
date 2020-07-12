package com.knight.f_interesting.mvp.person_information;

import android.net.Uri;
import android.util.Log;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.buses.ContextBus;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;
import com.knight.f_interesting.utils.AppUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationModel implements InformationContract.Model {
    @Override
    public void getUser(final OnFinishedListener onFinishedListener) {
        User user = UserBus.current();
        if (user != null && user.getName() != null) {
            onFinishedListener.onFinish(user);
        } else requestApi(onFinishedListener);
    }

    private void requestApi(final OnFinishedListener onFinishedListener) {
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(AppClient.headers());
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onFinish(response.body().getData());
                    UserBus.publish(response.body().getData());
                }
                else
                    onFinishedListener.onFinish(new User());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void uploadAvatar(final OnFinishedListener onFinishedListener, Uri uri) {
        if (uri == null) onFinishedListener.onFailure(new Throwable("Can't upload this image!"));
        File file = new File(AppUtils.getRealPathFromURI(uri));
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

    @Override
    public void updateUser(final OnFinishedListener onFinishedListener, User user) {
        Map<String, String> params = new HashMap<>();
        if (user.getName() != null) params.put("name", user.getName());
        if (user.getEmail() != null) params.put("email", user.getEmail());
        if (user.getBirthday() != null) params.put("birthday", user.getBirthday());
        if (user.getNumberPhone() != null) params.put("numberphone", user.getNumberPhone());
        APIInterface api = AppClient.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.uploadUser(AppClient.headers(), params);
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                if (response.body() != null && response.body().getStatus() == 1){
                    onFinishedListener.onFinish(response.body().getData());
                    UserBus.publish(response.body().getData());
                }
                else{
                    Log.e("Err", response.body().getMsg());
                    onFinishedListener.onFinish(new User());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
