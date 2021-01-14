package com.akiraagusta.android_todo.modul.profile;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;

public interface ProfileContract {
    interface View {
        void showError(String message);
        void setProfile(Profile user);
        void showAlertDialog();
        void redirectToLogin();
        void startLoading();
        void endLoading();
    }

    interface presenter {
        void setProfile();
        void logout();
    }

    interface Interactor {
        void requestProfile(RequestCallback<Profile> requestCallback);
        void logout();
    }
}
