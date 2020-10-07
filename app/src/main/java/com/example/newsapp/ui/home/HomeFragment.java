package com.example.newsapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.ApiInterfaces.ApiConstants;
import com.example.newsapp.ApiInterfaces.MostPopularArticles;
import com.example.newsapp.R;
import com.example.newsapp.holders.homearticles.HomeArticleHolder;
import com.example.newsapp.holders.homearticles.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        bindUI(root);
        retrofitCall();
        setSwipeRefreshLayout();
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });


        return root;
    }

    private void retrofitCall() {
        swipeRefreshLayout.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final ArrayList<HomeArticles> homeArticlesArrayList = new ArrayList<>();

        MostPopularArticles mostPopularArticles = retrofit.create(MostPopularArticles.class);
        Call<HomeArticleHolder> call = mostPopularArticles.getMostPopularArticles();
        call.enqueue(new Callback<HomeArticleHolder>() {
            @Override
            public void onResponse(Call<HomeArticleHolder> call, Response<HomeArticleHolder> response) {
                try {
                    List<Results> results = response.body().getResults();

                    for (int i = 0; i < results.size(); i++) {
                        try {
                            homeArticlesArrayList.add(new HomeArticles(results.get(i).getArticleTitle(),
                                    results.get(i).getArticleContent(),
                                    results.get(i).getArticleUrl(),
                                    results.get(i).getMedia().get(0).getMediaMetaDataList().get(2).getUrl(),
                                    results.get(i).getArticlePublisherName(),
                                    results.get(i).getArticlePublishedDate()));

                        } catch (IndexOutOfBoundsException e) {
                            homeArticlesArrayList.add(new HomeArticles(results.get(i).getArticleTitle(),
                                    results.get(i).getArticleContent(),
                                    results.get(i).getArticleUrl(),
                                    "No Image",
                                    results.get(i).getArticlePublisherName(),
                                    results.get(i).getArticlePublishedDate()));
                            Log.d("TAG", "Exception: " + e);
                        }
                    }

                } catch (Exception e) {
                    //Toast.makeText(getContext(),"Too many requests.Wait 1 minute,then try again.",Toast.LENGTH_LONG).show();
                }

                swipeRefreshLayout.setRefreshing(false);
                buildRecyclerView(homeArticlesArrayList);

            }

            @Override
            public void onFailure(Call<HomeArticleHolder> call, Throwable t) {
                Toast.makeText(getContext(), "Retrofit failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void buildRecyclerView(ArrayList<HomeArticles> homeArticles) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setHasFixedSize(true);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(homeArticles);
        recyclerView.setAdapter(adapter);
    }

    private void bindUI(View view) {
        swipeRefreshLayout = view.findViewById(R.id.home_swipe_to_refresh_layout);
        recyclerView = view.findViewById(R.id.rv_home);
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d("LOG", "Refreshing layout");
                        swipeRefreshLayout.setRefreshing(true);
                        retrofitCall();
                    }
                }
        );
    }

    @Override
    public void onResume() {
        swipeRefreshLayout.setRefreshing(true);
        super.onResume();
    }
}