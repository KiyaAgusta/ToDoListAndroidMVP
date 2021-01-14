package com.akiraagusta.android_todo.modul.change_password;

import com.akiraagusta.android_todo.callback.RequestCallback;

public interface ChangePasswordContract {
    interface View {
        void showError(String message);
        void changePasswordSuccess(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter {
        void savePassword(String[] password);
    }

    interface Interactor {
        void changePassword(String[] password, RequestCallback<String> requestCallback);
    }
}
