package com.nemojmenervirat.todo.front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nemojmenervirat.todo.R;
import com.nemojmenervirat.todo.back.MainItem;

import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainViewAdapter extends ArrayAdapter<MainItem> {

    private LayoutInflater mInflater;

    public MainViewAdapter(@NonNull Context context,  @NonNull MainItem[] objects) {
        super(context, R.layout.main_item, objects);
        sort(new Comparator<MainItem>() {
            @Override
            public int compare(MainItem item1, MainItem item2) {
                return item1.compareTo(item2);
            }
        });
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MainItem item = getItem(position);
        if(item.isChecked()){
            return super.getView(position, mInflater.inflate(R.layout.main_item_checked, parent, false), parent);
        } else {
            return super.getView(position, convertView, parent);
        }
    }
}
