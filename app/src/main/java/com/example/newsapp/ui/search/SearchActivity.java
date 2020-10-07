package com.example.newsapp.ui.search;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.newsapp.R;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SearchView searchView;
    Toolbar toolbar;
    ImageButton imageButtonBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bindUI();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new NewestFragment(), "Newest");
        viewPagerAdapter.AddFragment(new OldestFragment(), "Oldest");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setSearchView();
        setImageButtonBack();
    }

    private void setSearchView() {
        searchView.requestFocus();
        View v = searchView.findViewById(R.id.search_plate);
        v.setBackgroundColor(Color.parseColor("#ffffff"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("searchViewText", searchView.getQuery().toString());
                NewestFragment newestFragment = new NewestFragment();
                OldestFragment oldestFragment = new OldestFragment();
                newestFragment.setArguments(bundle);
                oldestFragment.setArguments(bundle);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle bundle = new Bundle();
                bundle.putString("searchViewText", searchView.getQuery().toString());
                NewestFragment newestFragment = new NewestFragment();
                OldestFragment oldestFragment = new OldestFragment();
                newestFragment.setArguments(bundle);
                oldestFragment.setArguments(bundle);
                return false;
            }
        });

    }

    private void bindUI() {
        imageButtonBack = findViewById(R.id.btn_back_settings);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        searchView = findViewById(R.id.search_view_searchactivity);
        toolbar = findViewById(R.id.toolbar_selected_category);
    }

    private void setImageButtonBack() {
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
