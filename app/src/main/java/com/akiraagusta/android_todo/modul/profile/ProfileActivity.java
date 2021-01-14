package com.akiraagusta.android_todo.modul.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.modul.dashboard.DashboardActivity;
import com.akiraagusta.android_todo.modul.edit_profile.EditProfileActivity;
import com.akiraagusta.android_todo.modul.login.LoginActivity;
import com.akiraagusta.android_todo.R;
import com.akiraagusta.android_todo.databinding.ActivityProfileBinding;
import com.akiraagusta.android_todo.util.UtilProvider;
import com.akiraagusta.android_todo.modul.upload_profile_image.UploadProfileImageActivity;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View, View.OnClickListener {
    private ProfileContract.presenter presenter;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setContentView(binding.getRoot());

        presenter = new ProfilePresenter(this, new ProfileInteractor(UtilProvider.getSharedPreferencesUtil()));
        initView();
    }

    private void initView(){
        presenter.setProfile();
        binding.pageTitle.setText("Profile");
        binding.signOutButton.setOnClickListener(this);
        binding.backButton.setOnClickListener(this);
        binding.profile.setOnClickListener(this);
        binding.uploadProfileButton.setOnClickListener(this);
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
    public void onClick(View v) {
        if(v.getId() == binding.signOutButton.getId()){
            onButtonSignOutClick();
        }
        if(v.getId() == binding.backButton.getId()){
            onBackButtonClick();
        }
        if(v.getId() == binding.profile.getId()){
            onEditProfileClick();
        }
        if(v.getId() == binding.uploadProfileButton.getId()){
            onUploadProfileClick();
        }
    }

    private void onUploadProfileClick() {
        finish();
        startActivity(new Intent(this, UploadProfileImageActivity.class));
    }

    private void onHomeButtonClick() {
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }

    private void onEditProfileClick() {
        finish();
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    public void onButtonSignOutClick(){
        showAlertDialog();
    }

    public void onBackButtonClick(){
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setProfile(Profile user) {
        binding.profileName.setText(user.getFull_name());
        binding.setUser(user);
        if(user.getGender() != null)
            binding.gender.setText(user.getGender());
        if(user.getBirth_date()!=null){
            String[] date = user.getBirth_date().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));
            CharSequence dateFormatted = DateFormat.format("EEE, d MMM yyyy", calendar);
            binding.birthDate.setText(dateFormatted);
        }
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to Sign Out ?");
        builder.setPositiveButton(Html.fromHtml("<font color='#FBB308'>Yes</font>"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                presenter.logout();
            }
        });
        builder.setNegativeButton(Html.fromHtml("<font color='#EB5334'>No</font>"), null);
        builder.create();
        builder.show();
    }

    @Override
    public void redirectToLogin() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
