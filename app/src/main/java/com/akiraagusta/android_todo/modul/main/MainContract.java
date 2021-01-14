package com.akiraagusta.android_todo.modul.main;

public interface MainContract {
    interface View {
        void whenUserLogin();
        void whenUserNotLogin();
    }

    interface Presenter {
        void checkIsUserLogin();
    }

    interface Interactor {
        boolean isUserLogin();
    }
}
