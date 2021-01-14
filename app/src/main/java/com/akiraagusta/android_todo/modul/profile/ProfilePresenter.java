package com.akiraagusta.android_todo.modul.profile;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.callback.RequestCallback;

public class ProfilePresenter implements ProfileContract.presenter {
    private ProfileContract.View view;
    private ProfileInteractor interactor;

    public ProfilePresenter(ProfileContract.View view, ProfileInteractor interactor) {
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
    public void logout() {
        interactor.logout();
        view.redirectToLogin();
    }
}
