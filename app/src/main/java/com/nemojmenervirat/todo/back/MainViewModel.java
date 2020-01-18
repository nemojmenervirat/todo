package com.nemojmenervirat.todo.back;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class MainViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;
    private ArrayList<MainItem> list;
    private MutableLiveData<List<MainItem>> liveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appDatabase = Room.databaseBuilder(application.getApplicationContext(),
                AppDatabase.class, "database-name").build();
    }

    public MutableLiveData<List<MainItem>> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<List<MainItem>>();
            loadItems();
        }
        return liveData;
    }

    public MainItem[] getItemsArray() {
        if (liveData == null) {
            return new MainItem[]{};
        }
        return liveData.getValue().toArray(new MainItem[liveData.getValue().size()]);
    }

    public void addItem(final String text) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MainItem item = new MainItem(text);
                list.add(item);
                appDatabase.mainItemDao().insertAll(item);
                liveData.postValue(list);
            }
        });
    }

    public void changeSelection(final MainItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                item.setChecked(!item.isChecked());
                appDatabase.mainItemDao().update(item);
                liveData.postValue(list);
            }
        });
    }

    public void removeItem(final MainItem item) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                list.remove(item);
                appDatabase.mainItemDao().delete(item);
                liveData.postValue(list);
            }
        });
    }

    private void loadItems() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                list.addAll(appDatabase.mainItemDao().getAll());
                liveData.postValue(list);
            }
        });
    }
}
