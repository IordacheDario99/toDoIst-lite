package com.example.todoistlite.ui.today;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodayViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public TodayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TODAY fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
