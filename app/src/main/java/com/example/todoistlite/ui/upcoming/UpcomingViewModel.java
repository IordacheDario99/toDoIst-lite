package com.example.todoistlite.ui.upcoming;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpcomingViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public UpcomingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TODAY fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
