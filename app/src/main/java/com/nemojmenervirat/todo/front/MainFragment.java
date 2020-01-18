package com.nemojmenervirat.todo.front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.nemojmenervirat.todo.R;
import com.nemojmenervirat.todo.back.MainItem;
import com.nemojmenervirat.todo.back.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ListView mListView;
    private MainViewAdapter mAdapter;
    private ImageButton mImageButton;
    private EditText mEditText;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mListView = getView().findViewById(R.id.grid_view);
        mEditText = getView().findViewById(R.id.edit_text);
        mImageButton = getView().findViewById(R.id.image_button);

        // observe data changes
        mViewModel.getLiveData().observe(this, new Observer<List<MainItem>>() {
            @Override
            public void onChanged(List<MainItem> mainItems) {
                // create adapter
                mAdapter = new MainViewAdapter(getContext(), mViewModel.getItemsArray());
                mListView.setAdapter(mAdapter);
            }
        });

        // item clicked -> item selected/deselected
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.changeSelection(mAdapter.getItem(position));
            }
        });

        // item long clicked -> item edit
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.removeItem(mAdapter.getItem(position));
                return true;
            }
        });

        // button clicked -> new item added
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                if (text.isEmpty()) {
                    return;
                }
                mViewModel.addItem(text);
                mEditText.getText().clear();
            }
        });
    }

}
