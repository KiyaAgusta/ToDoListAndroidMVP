package com.akiraagusta.android_todo.modul.login;

import com.akiraagusta.android_todo.api_response.LoginResponse;
import com.akiraagusta.android_todo.callback.RequestCallback;

public interface LoginContract {
    interface View {
        void startLoading();
        void endLoading();
        void loginSuccess();
        void loginFailed(String message);
        void register();
    }

    interface Presenter {
        void login(String email, String password);
        void register();
    }

    interface Interactor {
        void requestLogin(String email, String password, RequestCallback<LoginResponse> requestCallback);
        void saveToken(String token);
    }
}
