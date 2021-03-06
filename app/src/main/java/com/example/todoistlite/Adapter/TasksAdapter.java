package com.example.todoistlite.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoistlite.AddNewTask;
import com.example.todoistlite.MainActivity;
import com.example.todoistlite.Model.ToDoModel;
import com.example.todoistlite.R;
import com.example.todoistlite.Utilities.DatabaseHandler;
import com.example.todoistlite.ui.home.HomeFragment;
import com.example.todoistlite.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<HomeViewModel> tasksList = new ArrayList<>();
    private HomeFragment homeFragment;
    private MainActivity activity;
    private DatabaseHandler db;


    public TasksAdapter(DatabaseHandler db,HomeFragment homeFragment){
        this.db = db;
        this.homeFragment = homeFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.task_checkbox);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db.openDatabase();
        HomeViewModel item = tasksList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
        // poate reusesc sa fac un seter si pentru prioritate


    }

    @Override
    public int getItemCount() {
        //System.out.println(tasksList + "I'm here!" );
        return tasksList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void editItem(int position){
        HomeViewModel item = tasksList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public void setTasks(List<HomeViewModel> tasksList){
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }


}
