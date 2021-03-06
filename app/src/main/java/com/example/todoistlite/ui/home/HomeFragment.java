package com.example.todoistlite.ui.home;




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

import com.example.todoistlite.Adapter.TasksAdapter;
import com.example.todoistlite.DialogCloseListener;
import com.example.todoistlite.MainActivity;
import com.example.todoistlite.R;
import com.example.todoistlite.Utilities.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements DialogCloseListener {

    private HomeViewModel homeViewModel;
    private RecyclerView taskRecyclerView;
    private MainActivity activity;
    private TasksAdapter tasksAdapter;
    private DatabaseHandler db;

    private List<HomeViewModel>taskList = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        tasksAdapter = new TasksAdapter(db, HomeFragment.this);
        taskRecyclerView.setAdapter(tasksAdapter);

//        HomeViewModel hTask = new HomeViewModel();
//        hTask.setTask("This is a test task");
//        hTask.setStatus(0);
//        hTask.setId(1);
//        hTask.setPriorrity(0);
//
//
//
//        taskList.add(hTask);
//        taskList.add(hTask);
//        taskList.add(hTask);
//        taskList.add(hTask);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();



        return root;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getContext(), "DAAAAAA", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }


}