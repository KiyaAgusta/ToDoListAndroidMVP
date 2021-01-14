package com.akiraagusta.android_todo.modul.edit_task;

import com.akiraagusta.android_todo.api_response.ResponseMessage;
import com.akiraagusta.android_todo.api_response.TaskResponse;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.util.SharedPreferencesUtil;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class EditTaskInteractor implements EditTaskContract.Interactor {
    private SharedPreferencesUtil sharedPreferencesUtil;
    private int id;

    public EditTaskInteractor(SharedPreferencesUtil sharedPreferencesUtil, int id) {
        this.sharedPreferencesUtil = sharedPreferencesUtil;
        this.id = id;
    }

    @Override
    public void requestTask(final RequestCallback<Task> requestCallback) {
        AndroidNetworking.get(ApiConstant.BASE_URL + "/api/task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(TaskResponse.class, new ParsedRequestListener<TaskResponse>() {
                    @Override
                    public void onResponse(TaskResponse response) {
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
    public void editTask(Task task, final RequestCallback<String> requestCallback) {
        AndroidNetworking.put(ApiConstant.BASE_URL + "/api/task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .addBodyParameter("title", task.getTitle())
                .addBodyParameter("description", task.getDescription())
                .addBodyParameter("due_date", task.getDue_date())
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

    @Override
    public void deleteTask(final RequestCallback<String> requestCallback) {
        AndroidNetworking.delete(ApiConstant.BASE_URL + "/api/task/" + id)
                .addHeaders("Authorization", "Bearer " + sharedPreferencesUtil.getToken())
                .build()
                .getAsObject(ResponseMessage.class, new ParsedRequestListener<ResponseMessage>() {
                    @Override
                    public void onResponse(ResponseMessage response) {
                        if(response == null){
                            requestCallback.requestFailed("Failed To Delete");
                        }
                        else {
                            requestCallback.requestSuccess(response.message);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        requestCallback.requestFailed(anError.getMessage());
                    }
                });
    }
}