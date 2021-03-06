package com.example.todoistlite.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoistlite.ui.gallery.GalleryFragment;
import com.example.todoistlite.ui.gallery.GalleryViewModel;
import com.example.todoistlite.ui.home.HomeFragment;
import com.example.todoistlite.ui.home.HomeViewModel;
import com.example.todoistlite.ui.inbox.InboxViewModel;
import com.example.todoistlite.ui.priority_3.Priority_3_ViewModel;
import com.example.todoistlite.ui.slideshow.SlideshowViewModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoIstDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String PRIORITY_LV = "priority_lv";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + STATUS + " INTEGER, " + PRIORITY_LV + " INTEGER)";

    private SQLiteDatabase db;
    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION );
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        //Drop all existing tables
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //Create tables again
        onCreate(db);
    }

    // This is the method that we will use in the activities (MainActivity, NFragment etc)
    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(HomeViewModel task, int priority){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        if (priority != 0){
            cv.put(PRIORITY_LV, priority);
        }
        else { cv.put(PRIORITY_LV, 0); }

        db.insert(TODO_TABLE, null, cv);
    }

    // Get all tasks for Priority 0
    public List<HomeViewModel> getAllTasks(){
        List<HomeViewModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE,null, null, null, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        HomeViewModel task = new HomeViewModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setPriorrity(cursor.getInt(cursor.getColumnIndex(PRIORITY_LV)));
                        // the above line is questionable... change getInt to getString
                        // if problems appears
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }

        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    // Get all tasks for Priority 1

    public List<GalleryViewModel> getAllTasksP1(){
        List<GalleryViewModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE,null, null, null, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        GalleryViewModel task = new GalleryViewModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setPriorrity(cursor.getInt(cursor.getColumnIndex(PRIORITY_LV)));
                        // the above line is questionable... change getInt to getString
                        // if problems appears
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }

        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    // Get all tasks for Priority 2

    public List<SlideshowViewModel> getAllTasksP2(){
        List<SlideshowViewModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE,null, null, null, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        SlideshowViewModel task = new SlideshowViewModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setPriorrity(cursor.getInt(cursor.getColumnIndex(PRIORITY_LV)));
                        // the above line is questionable... change getInt to getString
                        // if problems appears
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }

        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    // Get all tasks for Priority 3

    public List<Priority_3_ViewModel> getAllTasksP3(){
        List<Priority_3_ViewModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE,null, null, null, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        Priority_3_ViewModel task = new Priority_3_ViewModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setPriorrity(cursor.getInt(cursor.getColumnIndex(PRIORITY_LV)));
                        // the above line is questionable... change getInt to getString
                        // if problems appears
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }

        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    // Get all tasks from Inbox
    public List<InboxViewModel> getAllTasksInbox(){
        List<InboxViewModel> taskList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(TODO_TABLE,null, null, null, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        InboxViewModel task = new InboxViewModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        task.setPriorrity(cursor.getInt(cursor.getColumnIndex(PRIORITY_LV)));
                        // the above line is questionable... change getInt to getString
                        // if problems appears
                        taskList.add(task);

                    }while (cursor.moveToNext());
                }
            }

        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return taskList;
    }

    
    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }
    public void updateTask(int id, String task){
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE,  ID + "=?", new String[] {String.valueOf(id)});
    }
    
}
