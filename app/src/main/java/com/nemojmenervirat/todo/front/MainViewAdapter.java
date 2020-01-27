package com.nemojmenervirat.todo.front;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.nemojmenervirat.todo.R;
import com.nemojmenervirat.todo.back.MainItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MainViewAdapter extends BaseAdapter {

    private final Object mLock = new Object();
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final List<MainItem> items;
    

    public MainViewAdapter(@NonNull Context context, @NonNull List<MainItem> items) {
        this.mContext = context;
        this.items = items;
        Comparator<MainItem> comparator = new Comparator<MainItem>() {
            @Override
            public int compare(MainItem item1, MainItem item2) {
                return item1.compareTo(item2);
            }
        };
        Collections.sort(this.items, comparator);
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MainItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CheckedTextView view = (CheckedTextView) mInflater.inflate(R.layout.main_item_checked, parent, false);
        MainItem item = getItem(position);
        if(item.isChecked()) {
            view.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            view.setTextColor(Color.BLACK);
        }
        view.setText(item.getText());
        view.setChecked(item.isChecked());
        return view;
    }
}
