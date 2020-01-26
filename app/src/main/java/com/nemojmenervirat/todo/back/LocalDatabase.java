package com.nemojmenervirat.todo.back;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MainItem.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract MainItemDao mainItemDao();
}