package com.akiraagusta.android_todo.modul.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akiraagusta.android_todo.model.User;
import com.akiraagusta.android_todo.modul.login.LoginActivity;
import com.akiraagusta.android_todo.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View, View.OnClickListener {
    private RegisterContract.Presenter presenter;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.baseLayout.backButton.setEnabled(false);

        presenter = new RegisterPresenter(this, new RegisterInteractor());
        initView();
    }

    private void initView(){
        binding.baseLayout.pageTitle.setText("Sign Up");
        binding.baseLayout.backButton.setOnClickListener(this);
        binding.registerButton.setOnClickListener(this);
    }

    @Override
    public void startLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void registerSuccess() {
        finish();
        Toast.makeText(this,"Register Success !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void registerFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.registerButton.getId()){
            onButtonRegisterClick();
        }
        if(v.getId() == binding.baseLayout.backButton.getId()){
           onBackButtonClick();
        }
    }

    public void onButtonRegisterClick(){
        if(binding.registerFullName.getText().toString().isEmpty()  || binding.registerNumber.getText().toString().isEmpty()
                || binding.registerEmail.getText().toString().isEmpty() || binding.registerPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "All fields must not be empty.", Toast.LENGTH_SHORT).show();
        }else{
            User user = new User(binding.registerFullName.getText().toString(),
                    binding.registerEmail.getText().toString(),
                    binding.registerPassword.getText().toString(),
                    binding.registerNumber.getText().toString());
            presenter.register(user);
        }
    }

    public void onBackButtonClick(){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
