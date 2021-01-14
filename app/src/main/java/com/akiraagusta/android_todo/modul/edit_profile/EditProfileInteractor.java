package com.akiraagusta.android_todo.modul.edit_profile;

import android.util.Log;

import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.api_response.UserResponse;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class EditProfileInteractor implements EditProfileContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public EditProfileInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
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
                            Log.d("tag", "response null");
                        }
                        else {
                            requestCallback.requestSuccess(response.user);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to load data !");
                        Log.d("tag", "error gan" + anError.getMessage() + anError.getErrorCode());
                    }
                });
    }

    @Override
    public void editProfile(Profile profile, final RequestCallback<String> requestCallback) {
        AndroidNetworking.put(ApiConstant.BASE_URL + "/api/user")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("full_name", profile.getFull_name())
                .addBodyParameter("gender", profile.getGender())
                .addBodyParameter("birth_date", profile.getBirth_date())
                .addBodyParameter("email", profile.getEmail())
                .addBodyParameter("phone_number", profile.getPhone_number())
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
                            requestCallback.requestFailed("Unauthorized !");
                        else
                            requestCallback.requestFailed("Failed to save data !");
                    }
                });
    }
}