package com.akiraagusta.android_todo.modul.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.akiraagusta.android_todo.modul.dashboard.DashboardActivity;
import com.akiraagusta.android_todo.modul.login.LoginActivity;
import com.akiraagusta.android_todo.R;
import com.akiraagusta.android_todo.util.UtilProvider;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, new MainInteractor(UtilProvider.getSharedPreferencesUtil()));
        presenter.checkIsUserLogin();
    }

    @Override
    public void whenUserLogin() {
        finish();
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    }

    @Override
    public void whenUserNotLogin() {
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}
