package com.example.todoistlite.ui.priority_3;




import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoistlite.Adapter.PriorityThreeAdapter;
import com.example.todoistlite.Adapter.TasksAdapter;
import com.example.todoistlite.DialogCloseListener;
import com.example.todoistlite.MainActivity;
import com.example.todoistlite.R;
import com.example.todoistlite.Utilities.DatabaseHandler;
import com.example.todoistlite.ui.inbox.InboxViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Priority_3_Fragment extends Fragment implements DialogCloseListener {

    private Priority_3_ViewModel priority_3_viewModel;
    private RecyclerView taskRecyclerView;
    private MainActivity activity;
    private PriorityThreeAdapter tasksAdapter;
    private DatabaseHandler db;

    private List<Priority_3_ViewModel>taskList = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priority_3_viewModel =
                new ViewModelProvider(this).get(Priority_3_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_priority3, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        priority_3_viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        db = new DatabaseHandler(getContext());
        db.openDatabase();


        //taskList = new ArrayList<>();

        taskRecyclerView = root.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //change to getContext()
        tasksAdapter = new PriorityThreeAdapter(db, Priority_3_Fragment.this);
        taskRecyclerView.setAdapter(tasksAdapter);

        taskList = db.getAllTasksP3();
        int taskListSize = taskList.size();
        for (int i = 0; i <= taskListSize; i++){
            for (Priority_3_ViewModel task:taskList){
                if (task.getPriorrity() != 3 ){
                    taskList.remove(task);

                    break;
                }
                else {
                    tasksAdapter.setTasks(taskList);
                    tasksAdapter.notifyDataSetChanged();
                }
            }
        }


        Collections.reverse(taskList);
        //tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();



        return root;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "Priority_3_Fragment", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasksP3();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }


}