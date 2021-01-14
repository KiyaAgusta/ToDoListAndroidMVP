package com.akiraagusta.android_todo.modul.register;

import com.akiraagusta.android_todo.api_response.RegisterResponse;
import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.model.User;

public interface RegisterContract {
    interface View {
        void startLoading();
        void endLoading();
        void registerSuccess();
        void registerFailed(String message);
    }

    interface Presenter {
        void register(User newUser);
    }

    interface Interactor {
        void requestRegister(User newUser, RequestCallback<RegisterResponse> requestCallback);
    }
}
