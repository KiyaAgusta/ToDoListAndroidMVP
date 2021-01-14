package com.akiraagusta.android_todo.modul.edit_profile;

import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.model.Profile;

public interface EditProfileContract {
    interface View {
        void setProfile(Profile profile);
        void showError(String message);
        void editProfileSuccess(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter {
        void setProfile();
        void saveProfile(Profile profile);
    }

    interface Interactor {
        void requestProfile(RequestCallback<Profile> requestCallback);
        void editProfile(Profile profile, RequestCallback<String> requestCallback);
    }
}
