package com.akiraagusta.android_todo.modul.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akiraagusta.android_todo.adapter.ListTaskAdapter;
import com.akiraagusta.android_todo.constant.ApiConstant;
import com.akiraagusta.android_todo.databinding.ActivityDashboardBinding;
import com.akiraagusta.android_todo.model.Profile;
import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.modul.add_task.AddTaskActivity;
import com.akiraagusta.android_todo.util.UtilProvider;
import com.akiraagusta.android_todo.modul.edit_task.EditTaskActivity;
import com.akiraagusta.android_todo.modul.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View, View.OnClickListener, ListTaskAdapter.ListItemClickListener {
    private DashboardContract.Presenter presenter;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new DashboardPresenter(this, new DashboardInteractor(UtilProvider.getSharedPreferencesUtil()));
        initView();
    }

    private void initView(){
        presenter.getFullname();
        presenter.setTask();
        binding.profileImage.setOnClickListener(this);
        binding.listTask.setLayoutManager(new LinearLayoutManager(this));
        binding.fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.profileImage.getId()){
            onProfileClick();
        }
        if(v.getId() == binding.fab.getId()){
            onAddButtonClick();
        }
    }

    public void onProfileClick(){
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void onAddButtonClick(){
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void setUser(Profile user){
        binding.profileName.setText(user.getFull_name());
        if(user.getProfile_picture() != null){
            binding.profileImage.setBackground(null);
            Picasso.get()
                    .load(ApiConstant.BASE_URL + "/" + user.getProfile_picture())
                    .fit()
                    .into(binding.profileImage);
        }
    }

    @Override
    public void setTask(List<Task> task) {
        if(task.size()>0)
            binding.listTask.setAdapter(new ListTaskAdapter(this, task, getLayoutInflater(), this));
        else
            Toast.makeText(this, "No Task Made Yet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("task_id", position);
        startActivity(intent);
    }

    @Override
    public void onItemChecked(int id, boolean isChecked) {
        String status;

        if(isChecked)
            status = "true";
        else
            status = "false";

        Log.d("LOG ID HARUS BENER", String.valueOf(id));
        presenter.editTaskCheckBox(id, status);
    }
}
