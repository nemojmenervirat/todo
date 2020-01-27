package com.nemojmenervirat.todo.back;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class MainViewModel extends AndroidViewModel {

    private LocalDatabase localDatabase;
    private ArrayList<MainItem> list;
    private MutableLiveData<List<MainItem>> liveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        localDatabase = Room.databaseBuilder(application.getApplicationContext(),
                LocalDatabase.class, "database-name").build();
    }

    public MutableLiveData<List<MainItem>> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<List<MainItem>>();
            loadItems();
        }
        return liveData;
    }

    public List<MainItem> getItems() {
        if (liveData == null) {
            return Collections.emptyList();
        }
        return liveData.getValue();
    }

    public void addItem(final String text) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MainItem item = new MainItem(text);
                list.add(item);
                localDatabase.mainItemDao().insertAll(item);
                liveData.postValue(list);
            }
        });
    }

    public void changeSelection(final MainItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                item.setChecked(!item.isChecked());
                localDatabase.mainItemDao().update(item);
                liveData.postValue(list);
            }
        });
    }

    public void removeItem(final MainItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                list.remove(item);
                localDatabase.mainItemDao().delete(item);
                liveData.postValue(list);
            }
        });
    }

    private void loadItems() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                list.addAll(localDatabase.mainItemDao().getAll());
                liveData.postValue(list);
            }
        });
    }
}
