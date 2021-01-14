package com.akiraagusta.android_todo.modul.dashboard;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.callback.RequestCallback;

import java.util.List;

public interface DashboardContract {
    interface View {
        void setUser(Profile user);
        void setTask(List<Task> task);
        void onItemChecked(int id, boolean isChecked);
        void showError(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter {
        void getFullname();
        void setTask();
        void editTaskCheckBox(int id, String status);
    }

    interface Interactor {
        void getUser(RequestCallback<Profile> requestCallback);
        void requestTasks(RequestCallback<List<Task>> requestCallback);
        void editTask(int id, String status, RequestCallback<String> requestCallback);
    }
}
