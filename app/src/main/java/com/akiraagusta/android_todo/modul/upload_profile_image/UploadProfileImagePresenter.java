package com.akiraagusta.android_todo.modul.upload_profile_image;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;

import java.io.File;

public class UploadProfileImagePresenter implements UploadProfileImageContract.Presenter {
    private UploadProfileImageContract.View view;
    private UploadProfileImageInteractor interactor;

    public UploadProfileImagePresenter(UploadProfileImageContract.View view, UploadProfileImageInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void setProfile() {
        view.startLoading();
        interactor.requestProfile(new RequestCallback<Profile>() {
            @Override
            public void requestSuccess(Profile data) {
                view.setProfile(data);
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
    public void saveProfile(File file) {
        view.startLoading();
        interactor.uploadImage(file, new RequestCallback<String>() {
            @Override
            public void requestSuccess(String message) {
                view.editProfileSuccess(message);
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
