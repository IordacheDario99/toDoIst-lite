package com.example.todoistlite.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private int id, status, priorrity;
    private String task;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public int getPriorrity() {
        return priorrity;
    }

    public void setPriorrity(int priorrity) {
        this.priorrity = priorrity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Tasks");
    }

    public LiveData<String> getText() {
        return mText;
    }
}