package com.nemojmenervirat.todo.back;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MainItemDao {

    @Query("SELECT * FROM item")
    List<MainItem> getAll();

    @Query("SELECT * FROM item WHERE id IN (:itemIds)")
    List<MainItem> loadAllByIds(int[] itemIds);

    @Insert
    void insertAll(MainItem... items);

    @Update
    void update(MainItem item);

    @Delete
    void delete(MainItem item);
}
