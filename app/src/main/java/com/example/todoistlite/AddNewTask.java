package com.example.todoistlite;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.todoistlite.Utilities.DatabaseHandler;
import com.example.todoistlite.ui.home.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionButtonDialog";

    private EditText newTask;
    private Button saveButton, priorityOne, priorityTwo, priorityThree;
    private DatabaseHandler db;
    private int priority;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @NonNull
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTask = getView().findViewById(R.id.newTaskText);
        saveButton = getView().findViewById(R.id.newTaskButton);
        priorityOne = getView().findViewById(R.id.priority_1_btn);
        priorityTwo = getView().findViewById(R.id.priority_2_btn);
        priorityThree = getView().findViewById(R.id.priority_3_btn);


        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdated = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdated = true;
            String task = bundle.getString("taks");
            newTask.setText(task);
            if(task.length() > 0){
                newTask.setTextColor(Color.GREEN);
            }
        }

        newTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newTask.setEnabled(false);
                    newTask.setTextColor(Color.GRAY);
                }else {
                    newTask.setEnabled(true);
                    saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                }

            //update recyclerview or data base status or smt like this
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        boolean finalIsUpdated = isUpdated;

        priorityOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 1;
            }
        });

        priorityTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 2;
            }
        });

        priorityThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priority = 3;
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTask.getText().toString();
                if(finalIsUpdated){
                    db.updateTask(bundle.getInt("id"),text);
                }
                else {
                    Toast.makeText(getActivity(), "Refresh the page to view the new task !", Toast.LENGTH_SHORT).show();
                    HomeViewModel task = new HomeViewModel();
                    task.setTask(text);
                    task.setStatus(0);
                    //task.setPriorrity(priority);
                    db.insertTask(task, priority);
                }
                dismiss();
            }
        });


    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

}
