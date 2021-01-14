package com.akiraagusta.android_todo.modul.edit_task;

import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.callback.RequestCallback;

public class EditTaskPresenter implements EditTaskContract.Presenter {
    private EditTaskContract.View view;
    private EditTaskInteractor interactor;

    public EditTaskPresenter(EditTaskContract.View view, EditTaskInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setTask() {
        view.startLoading();
        interactor.requestTask(new RequestCallback<Task>() {
            @Override
            public void requestSuccess(Task task) {
                view.setTask(task);
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
    public void editTask(Task task) {
        view.startLoading();
        interactor.editTask(task, new RequestCallback<String>() {
            @Override
            public void requestSuccess(String message) {
                view.editTaskSuccess(message);
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
    public void deleteTask() {
        view.startLoading();
        interactor.deleteTask(new RequestCallback<String>() {
            @Override
            public void requestSuccess(String message) {
                view.deleteTaskSuccess(message);
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
    public void shareTask() {
        view.startLoading();
        interactor.requestTask(new RequestCallback<Task>() {
            @Override
            public void requestSuccess(Task task) {
                view.shareTask(task);
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