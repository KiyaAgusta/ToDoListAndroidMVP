package com.akiraagusta.android_todo.modul.dashboard;


import com.akiraagusta.android_todo.api_response.ListTaskResponse;
import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.api_response.UserResponse;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.List;

public class DashboardInteractor implements DashboardContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public DashboardInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void getUser(final RequestCallback<Profile> requestCallback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "/api/user")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(UserResponse.class, new ParsedRequestListener<UserResponse>() {
                    @Override
                    public void onResponse(UserResponse response) {
                        if(response != null){
                            requestCallback.requestSuccess(response.user);
                        }
                        else {
                            requestCallback.requestFailed("Null Response");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to load data !");
                    }
                });
    }

    @Override
    public void requestTasks(final RequestCallback<List<Task>> requestCallback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "/api/task")
                .build()
                .getAsObject(ListTaskResponse.class, new ParsedRequestListener<ListTaskResponse>() {
                    @Override
                    public void onResponse(ListTaskResponse response) {
                        if(response == null){
                            requestCallback.requestFailed("Null Response");
                        }
                        else {
                            requestCallback.requestSuccess(response.tasks);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed("Failed to load data !");
                    }
                });
    }

    @Override
    public void editTask(int id, String status, final RequestCallback<String> requestCallback) {
        AndroidNetworking.put(ApiConstant.BASE_URL + "/api/task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("status", status)
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        if(response == null){
                            requestCallback.requestFailed("Failed To Edit Task!");
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
                            requestCallback.requestFailed("Failed To Edit Task !");
                    }
                });
    }

}
