package com.akiraagusta.android_todo.modul.edit_profile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.akiraagusta.android_todo.R;
import com.akiraagusta.android_todo.databinding.ActivityEditProfileBinding;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.modul.change_password.ChangePasswordActivity;
import com.akiraagusta.android_todo.util.UtilProvider;
import com.akiraagusta.android_todo.modul.profile.ProfileActivity;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View, View.OnClickListener, View.OnTouchListener{
    private EditProfileContract.Presenter presenter;
    private ActivityEditProfileBinding binding;
    private String gender = "-";
    private String birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new EditProfilePresenter(this, new EditProfileInteractor(UtilProvider.getSharedPreferencesUtil()));
        initView();

    }

    private void initView(){
        presenter.setProfile();
        binding.backButton.setOnClickListener(this);
        binding.saveButton.setOnClickListener(this);
        binding.changePwd.setOnClickListener(this);
        binding.radioFemale.setOnClickListener(this);
        binding.radioMale.setOnClickListener(this);
        binding.radioOther.setOnClickListener(this);
        binding.birthDate.setOnTouchListener(this);
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
        if(v.getId() == binding.backButton.getId()){
            onBackButtonClick();
        }
        if(v.getId() == binding.saveButton.getId()){
            onSaveButtonClick();
        }
        if(v.getId() == binding.changePwd.getId()){
            onChangePasswordButtonClick();
        }
    }

    private void onBirthDateClick() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.MyDatePickerSpinnerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month+1;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month-1);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                birthDate = year+"-"+month+"-"+day;
                CharSequence date = DateFormat.format("EEE, d MMM yyyy", calendar);
                binding.birthDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final long today = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMaxDate(today);
        datePickerDialog.show();
    }

    private void onChangePasswordButtonClick() {
        finish();
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    private void onSaveButtonClick() {
        int selectedId = binding.radioButton.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        if(radioButton != null)
            gender = radioButton.getText().toString();
        else
            gender = null;
        Profile profile = new Profile(binding.editFullName.getText().toString(),
                gender, birthDate,
                binding.editEmail.getText().toString(),
                binding.editPhoneNumber.getText().toString());
        presenter.saveProfile(profile);
    }

    public void onBackButtonClick(){
        finish();
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void editProfileSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void setProfile(Profile user) {
        binding.setUser(user);
        if(user.getGender() != null){
            if(user.getGender().equalsIgnoreCase("male"))
                binding.radioMale.setChecked(true);
            else if(user.getGender().equalsIgnoreCase("female"))
                binding.radioFemale.setChecked(true);
            else if(user.getGender().equalsIgnoreCase("other"))
                binding.radioOther.setChecked(true);
        }
        if(user.getBirth_date() != null){
            String[] date = user.getBirth_date().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[2]));
            CharSequence dateFormatted = DateFormat.format("EEE, d MMM yyyy", calendar);
            binding.birthDate.setText(dateFormatted);
            binding.birthDate.setInputType(InputType.TYPE_NULL);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == binding.birthDate.getId()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onBirthDateClick();
                    break;
            }
        }
        return false;
    }
}