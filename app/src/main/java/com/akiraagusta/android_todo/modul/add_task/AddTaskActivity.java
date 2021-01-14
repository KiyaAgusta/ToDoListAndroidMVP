package com.akiraagusta.android_todo.modul.add_task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akiraagusta.android_todo.R;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.databinding.ActivityAddTaskBinding;
import com.akiraagusta.android_todo.util.UtilProvider;
import com.akiraagusta.android_todo.modul.dashboard.DashboardActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements AddTaskContract.View, View.OnClickListener, View.OnTouchListener{
    private AddTaskContract.Presenter presenter;
    private ActivityAddTaskBinding binding;
    private String dueDate;
    private int tempHour, tempMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new AddTaskPresenter(this, new AddTaskInteractor(UtilProvider.getSharedPreferencesUtil()));
        initView();
    }

    private void initView(){
        binding.pageTitle.setText("Add Task");
        binding.backButton.setOnClickListener(this);
        binding.addButton.setOnClickListener(this);
        binding.dueDate.setOnTouchListener(this);
        binding.radioReminder.setOnClickListener(this);
    }

    private void onDueDateClick() {
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
                CharSequence date = DateFormat.format("EEE, d MMM yyyy", calendar);
                dueDate = year+"-"+month+"-"+day+" ";
                getTimePicker(date);
            }
        }, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final long today = System.currentTimeMillis() - 1000;
        datePickerDialog.getDatePicker().setMinDate(today);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();

    }

    public void getTimePicker(final CharSequence date) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(this, R.style.MyDatePickerSpinnerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                dueDate += selectedHour + ":" + selectedMinute + ":00";
                binding.dueDate.setText(date + " " + selectedHour + ":" + selectedMinute+":00");
                tempHour = selectedHour;
                tempMinutes = selectedMinute;
            }
        }, hour, minute, true);
        mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
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
        if(v.getId() == binding.addButton.getId()){
            onAddButtonClick();
        }
    }

    public void onBackButtonClick(){
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void onAddButtonClick(){
        int selectedId = binding.radioButton.getCheckedRadioButtonId();
        final RadioButton radioButton = findViewById(selectedId);
        if(radioButton != null)
            setReminder();

        Task task = new Task(
                binding.editTitle.getText().toString(),
                binding.editDescription.getText().toString(),
                dueDate
        );
        presenter.addTask(task);
    }

    public void setReminder() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, tempHour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, tempMinutes);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addTaskSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == binding.dueDate.getId()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onDueDateClick();
                    break;
            }
        }
        return false;
    }

}