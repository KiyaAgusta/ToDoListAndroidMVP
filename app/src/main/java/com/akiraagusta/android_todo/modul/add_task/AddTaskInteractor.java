package com.akiraagusta.android_todo.modul.add_task;

import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class AddTaskInteractor implements AddTaskContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;

    public AddTaskInteractor(SharedPreferencesUtil sharedPreferencesUtil) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void addTask(Task task, final RequestCallback<String> requestCallback) {
        AndroidNetworking.post(ApiConstant.BASE_URL + "/api/task")
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("title", task.getTitle())
                .addBodyParameter("description", task.getDescription())
                .addBodyParameter("due_date", task.getDue_date())
                .addBodyParameter("status", "false")
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        if(response == null){
                            requestCallback.requestFailed("Failed To Add Task!");
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
                            requestCallback.requestFailed("Failed To Add Task !");
                    }
                });
    }

}