package com.akiraagusta.android_todo.modul.add_task;

import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.callback.RequestCallback;

public interface AddTaskContract {
    interface View {
        void addTaskSuccess(String message);
        void showError(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter {
        void addTask(Task task);
    }

    interface Interactor {
        void addTask(Task task, RequestCallback<String> requestCallback);
    }
}
