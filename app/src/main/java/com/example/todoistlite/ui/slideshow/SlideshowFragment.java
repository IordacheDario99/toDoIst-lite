package com.example.todoistlite.ui.slideshow;

import android.content.DialogInterface;
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

import com.example.todoistlite.Adapter.PriorityTwoAdapter;
import com.example.todoistlite.Adapter.TasksAdapter;
import com.example.todoistlite.DialogCloseListener;
import com.example.todoistlite.MainActivity;
import com.example.todoistlite.R;
import com.example.todoistlite.Utilities.DatabaseHandler;
import com.example.todoistlite.ui.gallery.GalleryViewModel;
import com.example.todoistlite.ui.inbox.InboxViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideshowFragment extends Fragment implements DialogCloseListener {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView taskRecyclerView;
    private MainActivity activity;
    private PriorityTwoAdapter tasksAdapter;
    private DatabaseHandler db;

    private List<SlideshowViewModel>taskList = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        tasksAdapter = new PriorityTwoAdapter(db, SlideshowFragment.this);
        taskRecyclerView.setAdapter(tasksAdapter);

        taskList = db.getAllTasksP2();
        int taskListSize = taskList.size();
        for (int i = 0; i <= taskListSize; i++){
            for (SlideshowViewModel task:taskList){
                if (task.getPriorrity() != 2 ){
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
        Toast.makeText(getContext(), "SlideshowFragment", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasksP2();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }


}