package com.akiraagusta.android_todo.modul.upload_profile_image;

import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.api_response.UserResponse;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.io.File;

public class UploadProfileImageInteractor implements UploadProfileImageContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public UploadProfileImageInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestProfile(final RequestCallback<Profile> requestCallback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "/api/user")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(UserResponse.class, new ParsedRequestListener<UserResponse>() {
                    @Override
                    public void onResponse(UserResponse response) {
                        if(response == null){
                            requestCallback.requestFailed("Null Response");
                        }
                        else {
                            requestCallback.requestSuccess(response.user);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to load data !");
                    }
                });
    }

    @Override
    public void uploadImage(File file, final  RequestCallback<String> requestCallback) {
        AndroidNetworking.upload(ApiConstant.BASE_URL + "/api/user/image")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addMultipartFile("profile_picture", file)
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        requestCallback.requestSuccess(response.message);
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("failed to upload image");
                    }
                });
    }
}

