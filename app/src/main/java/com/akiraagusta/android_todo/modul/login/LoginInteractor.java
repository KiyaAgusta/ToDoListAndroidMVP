package com.akiraagusta.android_todo.modul.login;

import com.akiraagusta.android_todo.api_response.LoginResponse;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class LoginInteractor implements LoginContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public LoginInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void requestLogin(String email, String password, final RequestCallback<LoginResponse> requestCallback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "/api/login")
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        if(response == null){
                            requestCallback.requestFailed("Null Response");
                        }
                        else {
                            requestCallback.requestSuccess(response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if(anError.getErrorCode() == 401)
                            requestCallback.requestFailed("Wrong Email or Password");
                        else if(anError.getErrorCode() == 500)
                            requestCallback.requestFailed("Server Error. Please check your internet connection");
                        else
                            requestCallback.requestFailed(anError.getMessage());
                    }
                });
    }

    @Override
    public void saveToken(String token) {
        sharedPreferencesUtil.setToken(token);
    }
}

