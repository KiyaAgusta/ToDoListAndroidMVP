package com.akiraagusta.android_todo.modul.change_password;

import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class ChangePasswordInteractor implements ChangePasswordContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public ChangePasswordInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void changePassword(String[] password, final RequestCallback<String> requestCallback) {
        AndroidNetworking.put(ApiConstant.BASE_URL + "/api/user/password")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("oldPassword", password[0])
                .addBodyParameter("newPassword", password[1])
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        if(response == null){
                            requestCallback.requestFailed("Null Response");
                        }
                        else {
                            requestCallback.requestSuccess(response.message);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() == 401)
                            requestCallback.requestFailed("Old password doesn't match !");
                        else
                            requestCallback.requestFailed("Failed to load data !");
                    }
                });
    }
}

