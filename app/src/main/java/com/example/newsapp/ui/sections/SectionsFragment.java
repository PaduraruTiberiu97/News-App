package com.example.newsapp.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.ui.search.SearchActivity;

import java.util.ArrayList;

public class SectionsFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<Sections> sectionsArrayList = new ArrayList<>();
    Toolbar toolbar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sections, container, false);
        bindUI(root);
        setupToolbar();
        buildRevyclerView();

        return root;
    }

    private void bindUI(View view) {
        recyclerView = view.findViewById(R.id.rv_section);
        toolbar = view.findViewById(R.id.toolbar_sections);

    }

    private void buildRevyclerView() {
        createSectionsList();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setHasFixedSize(true);
        SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(sectionsArrayList);
        recyclerView.setAdapter(adapter);
    }

    private void createSectionsList() {
        final String[] sectionNames = {"World", "Politics", "Business", "U.S.", "Sports", "Arts", "Magazine", "Video", "Books", "Weather", "Workplace", "Vacation", "Travel", "Technology", "Television", "Society", "Science", "Movies", "Metropolitan", "Cars", "Environment", "Opinion"};
        for (String sectionName : sectionNames) {
            sectionsArrayList.add(new Sections(sectionName, R.drawable.ic_home_black_24dp));
        }
    }

    public void setupToolbar() {
        toolbar.inflateMenu(R.menu.section_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
