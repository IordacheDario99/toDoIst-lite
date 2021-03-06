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
import com.example.todoistlite.ui.priority_3.Priority_3_Fragment;
import com.example.todoistlite.ui.priority_3.Priority_3_ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PriorityThreeAdapter extends RecyclerView.Adapter<PriorityThreeAdapter.ViewHolder> {
    private List<Priority_3_ViewModel> tasksList = new ArrayList<>();
    private Priority_3_Fragment priority_3_fragment;
    private MainActivity activity;
    private DatabaseHandler db;


    public PriorityThreeAdapter(DatabaseHandler db,Priority_3_Fragment homeFragment){
        this.db = db;
        this.priority_3_fragment = priority_3_fragment;
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
        Priority_3_ViewModel item = tasksList.get(position);
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
        Priority_3_ViewModel item = tasksList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task",item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public void setTasks(List<Priority_3_ViewModel> tasksList){
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }


}
