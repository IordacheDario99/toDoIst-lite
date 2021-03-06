package com.example.todoistlite.ui.gallery;




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

import com.example.todoistlite.Adapter.PriorityOneAdapter;
import com.example.todoistlite.Adapter.TasksAdapter;
import com.example.todoistlite.DialogCloseListener;
import com.example.todoistlite.MainActivity;
import com.example.todoistlite.R;
import com.example.todoistlite.Utilities.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment implements DialogCloseListener {

    private GalleryViewModel galleryViewModel;
    private RecyclerView taskRecyclerView;
    private MainActivity activity;
    private PriorityOneAdapter tasksAdapter;
    private DatabaseHandler db;

    private List<GalleryViewModel> taskList = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        tasksAdapter = new PriorityOneAdapter(db, GalleryFragment.this);
        taskRecyclerView.setAdapter(tasksAdapter);

//        GalleryViewModel hTask = new GalleryViewModel();
//        GalleryViewModel pTask = new GalleryViewModel();
//
//        pTask.setTask("You should not see me");
//        pTask.setId(2);
//        pTask.setPriorrity(0);
//        pTask.setStatus(0);
//
//        hTask.setTask("This is a test task");
//        hTask.setStatus(0);
//        hTask.setId(1);
//        hTask.setPriorrity(1);
//
//
//
//
//
//        taskList.add(hTask);
//        taskList.add(pTask);
//        taskList.add(hTask);
//        taskList.add(pTask);
//        int taskListSize = taskList.size();
//
//
//        for ( int i = 0 ; i <= taskListSize; i++){
//            for (GalleryViewModel task:taskList){
//                if (task.getPriorrity() == 0){
//                    taskList.remove(hTask);
//                    taskListSize -= 1;
//                    break;
//                }
//                else {
//                    System.out.println("hTask");
//                }
//            }
//        }


        taskList = db.getAllTasksP1();
        int taskListSize = taskList.size();
        for (int i = 0; i <= taskListSize; i++){
            for (GalleryViewModel task:taskList){
                if (task.getStatus() == 0 ){
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
        Toast.makeText(getContext(), "Priority 1", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasksP1();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

    }


}