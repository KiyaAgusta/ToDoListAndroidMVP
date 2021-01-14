package com.akiraagusta.android_todo.modul.upload_profile_image;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;

import java.io.File;

public interface UploadProfileImageContract {
    interface View {
        void setProfile(Profile profile);
        void showError(String message);
        void editProfileSuccess(String message);
        void startLoading();
        void endLoading();
    }

    interface Presenter {
        void setProfile();
        void saveProfile(File file);
    }

    interface Interactor {
        void requestProfile(RequestCallback<Profile> requestCallback);
        void uploadImage(File file, RequestCallback<String> requestCallback);
    }
}
