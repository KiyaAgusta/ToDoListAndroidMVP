package com.akiraagusta.android_todo.modul.edit_profile;

import com.akiraagusta.android_todo.callback.RequestCallback;
import com.akiraagusta.android_todo.model.Profile;

public class EditProfilePresenter implements EditProfileContract.Presenter {
    private EditProfileContract.View view;
    private EditProfileInteractor interactor;

    public EditProfilePresenter(EditProfileContract.View view, EditProfileInteractor interactor) {
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
    public void saveProfile(Profile profile) {
        view.startLoading();
        interactor.editProfile(profile, new RequestCallback<String>() {
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