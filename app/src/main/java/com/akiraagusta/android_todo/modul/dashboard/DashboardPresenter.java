package com.akiraagusta.android_todo.modul.dashboard;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.model.Task;

import java.util.List;

public class DashboardPresenter implements DashboardContract.Presenter {
    private DashboardContract.View view;
    private DashboardInteractor interactor;

    public DashboardPresenter(DashboardContract.View view, DashboardInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void getFullname() {
        view.startLoading();
        interactor.getUser(new RequestCallback<Profile>() {

            @Override
            public void requestSuccess(Profile response) {
                view.setUser(response);
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
               view.showError(errorMessage);
               view.endLoading();
            }
        });
    }

    @Override
    public void setTask() {
        view.startLoading();
        interactor.requestTasks(new RequestCallback<List<Task>>() {
            @Override
            public void requestSuccess(List<Task> data) {
                view.endLoading();
                view.setTask(data);
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showError(errorMessage);
            }
        });
    }

    @Override
    public void editTaskCheckBox(int id, String status) {
        view.startLoading();
        interactor.editTask(id, status, new RequestCallback<String>() {
            @Override
            public void requestSuccess(String message) {
                view.endLoading();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.showError(errorMessage);
                view.endLoading();
            }
        });
    }

}
