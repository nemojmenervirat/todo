package com.nemojmenervirat.todo.back;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "item")
public class MainItem implements Comparable<MainItem> {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "checked")
    private boolean checked;

    @ColumnInfo(name = "checked_timestamp")
    private long checkedTimestamp;

    public MainItem(long id, String text, boolean checked,long checkedTimestamp) {
        this.id = id;
        this.text = text;
        this.checked = checked;
        this.checkedTimestamp = checkedTimestamp;
    }

    @Ignore
    public MainItem(String text, boolean checked) {
        this(System.currentTimeMillis(), text, checked, 0);
    }

    @Ignore
    public MainItem(String text) {
        this(text, false);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        this.checkedTimestamp = System.currentTimeMillis();
    }

    public long getCheckedTimestamp() {
        return checkedTimestamp;
    }

    public void setCheckedTimestamp(long checkedTimestamp) {
        this.checkedTimestamp = checkedTimestamp;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

    @Override
    public int compareTo(MainItem item) {
        int result = Boolean.compare(checked, item.checked);
        if(result != 0){
            return result;
        }
        if(!checked) {
            return Long.compare(id, item.id);
        } else {
            return Long.compare(item.checkedTimestamp, checkedTimestamp);
        }
    }
}
