package com.example.newsapp.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsapp.R;

public class NewestFragment extends Fragment {
    View view;
    TextView textView;

    private PageViewModel pageViewModel;

    public NewestFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_search_newest, container, false);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        textView=view.findViewById(R.id.tttttttttttttttttttt);
        try {
            String asd="qwert";
            if (getArguments() != null) {
                asd =getArguments().getString("searchViewText");
            }
            pageViewModel.setIndex(getArguments().getString("searchViewText"));
            pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);

                }
            });

           // textView.setText(searchViewText);
        }
        catch (Exception e){
            //something
        }
        return view;
    }
}
