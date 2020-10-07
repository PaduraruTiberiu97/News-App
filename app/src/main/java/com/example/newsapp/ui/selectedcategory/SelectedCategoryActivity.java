package com.example.newsapp.ui.selectedcategory;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.ApiInterfaces.ApiConstants;
import com.example.newsapp.ApiInterfaces.SpecificArticle;
import com.example.newsapp.R;
import com.example.newsapp.holders.specificarticle.Docs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedCategoryActivity extends AppCompatActivity {

    private String searchedArticle;
    private TextView textViewCategoryName;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        bindUI();
        retrofitCall();
        setSwipeRefreshLayout();
    }


    public void retrofitCall() {
        swipeRefreshLayout.setRefreshing(true);
        final ArrayList<SelectedCategory> selectedCategoryArrayList = new ArrayList<>();
        getBundle();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        SpecificArticle specificArticle = retrofit.create(SpecificArticle.class);
        Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call = specificArticle.getSpecificArticle(searchedArticle);
        call.enqueue(new Callback<com.example.newsapp.holders.specificarticle.SpecificArticle>() {
            @Override
            public void onResponse(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Response<com.example.newsapp.holders.specificarticle.SpecificArticle> response) {
                List<Docs> docs = response.body().getResponse().getDocs();
                for (int i = 0; i < docs.size(); i++) {
                    try {
                        selectedCategoryArrayList.add(new SelectedCategory(docs.get(i).getHeadline().getArticleTitle(),
                                docs.get(i).getArticleContent(),
                                docs.get(i).getMultimedia().get(0).getArticleImageUrl(),
                                docs.get(i).getByline().getAuthorName(),
                                docs.get(i).getPublishedDate(),
                                docs.get(i).getArticleWebUrl()));
                    } catch (IndexOutOfBoundsException e) {
                        selectedCategoryArrayList.add(new SelectedCategory(docs.get(i).getHeadline().getArticleTitle(),
                                docs.get(i).getArticleContent(),
                                "",
                                docs.get(i).getByline().getAuthorName(),
                                docs.get(i).getPublishedDate(),
                                docs.get(i).getArticleWebUrl()));
                        Log.d("TAG", "Exception " + e);
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                buildRV(selectedCategoryArrayList);
            }

            @Override
            public void onFailure(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Throwable t) {
                Toast.makeText(SelectedCategoryActivity.this, "Retrofit failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buildRV(ArrayList<SelectedCategory> selectedCategoryArrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(SelectedCategoryActivity.this, 1));
        recyclerView.setHasFixedSize(true);
        SelectedCategoryRvAdapter adapter = new SelectedCategoryRvAdapter(selectedCategoryArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void getBundle() {
        /*Intent intent=getIntent();
        searchedArticle=intent.getStringExtra("searchedCategory");*/ //Another way of getting data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            searchedArticle = (String) bundle.get("searchedCategory");
            textViewCategoryName.setText(searchedArticle);
        }
    }

    private void bindUI() {
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh_layout_selected_section);
        recyclerView = findViewById(R.id.rv_selected_category);
        textViewCategoryName = findViewById(R.id.txt_toolbar_selected_category);
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

}
