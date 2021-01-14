package com.akiraagusta.android_todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akiraagusta.android_todo.model.Task;
import com.akiraagusta.android_todo.databinding.ItemTaskBinding;
import com.akiraagusta.android_todo.modul.dashboard.DashboardActivity;

import java.util.List;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.ViewHolder> {
    private DashboardActivity view;
    private List<Task> task;
    private LayoutInflater layoutInflater;
    private final ListItemClickListener mOnClickListener;

    public ListTaskAdapter(DashboardActivity view, List<Task> task, LayoutInflater layoutInflater, ListTaskAdapter.ListItemClickListener onClickListener) {
        this.view = view;
        this.task = task;
        this.layoutInflater = layoutInflater;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTaskBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.binding.setTask(task.get(position));
        if(task.get(position).getTitle() != null)
            holder.binding.taskTitle.setText(task.get(position).getTitle());

        if(task.get(position).getDescription() != null)
            holder.binding.taskDescription.setText(task.get(position).getDescription());

        holder.binding.taskTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int id = task.get(position).getTask_id();
                view.onItemChecked(id, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(task != null){
            return task.size();
        }
        else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemTaskBinding binding;

        public ViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            itemView.setOnClickListener(this);
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {
            int position = task.get(getAdapterPosition()).getTask_id();
            mOnClickListener.onListItemClick(position);
        }

    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }
}