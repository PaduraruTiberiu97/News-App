package com.example.newsapp.ui.foryou;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.newsapp.ApiInterfaces.ApiConstants;
import com.example.newsapp.ApiInterfaces.SpecificArticle;
import com.example.newsapp.R;
import com.example.newsapp.database.FavoriteForYou;
import com.example.newsapp.database.FavoriteForYouDatabase;
import com.example.newsapp.holders.specificarticle.Docs;
import com.example.newsapp.ui.selectedcategory.SelectedCategory;
import com.example.newsapp.ui.selectedcategory.SelectedCategoryRvAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForYouSelectedActivity extends AppCompatActivity {
    int id;
    FavoriteForYou favoriteForYou = new FavoriteForYou();
    private String selectedForYouCategory;
    FavoriteForYouDatabase database;
    RecyclerView recyclerView;
    ToggleButton toggleButton;
    ProgressBar progressBar;
    //SwipeRefreshLayout swipeRefreshLayout;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_for_you);
        database = Room.databaseBuilder(ForYouSelectedActivity.this, FavoriteForYouDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        bindUI();
        retrofitCall();
        checkFavorite();
        setToggleButton();
        //setSwipeRefreshLayout();

    }


    private void bindUI() {
        //swipeRefreshLayout = findViewById(R.id.swipe_to_refresh_layout_selected_for_you);
        toggleButton = findViewById(R.id.toggle_selected_for_you);
        recyclerView = findViewById(R.id.rv_selected_for_you);
        textView = findViewById(R.id.txt_toolbar_selected_for_you);
        progressBar = findViewById(R.id.prgs);
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedForYouCategory = (String) bundle.get("forYouSelectedCategory");
            id = Integer.parseInt(String.valueOf(bundle.get("forYouSelectedCategoryId")));
            textView.setText(selectedForYouCategory);
        }
    }

/*    private void setSwipeRefreshLayout() {
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
    }*/

    private void buildRV(ArrayList<SelectedCategory> selectedCategoryArrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(ForYouSelectedActivity.this, 1));
        recyclerView.setHasFixedSize(true);
        SelectedCategoryRvAdapter adapter = new SelectedCategoryRvAdapter(selectedCategoryArrayList);
        recyclerView.setAdapter(adapter);

    }

    public void retrofitCall() {
        progressBar.setVisibility(View.VISIBLE);
        //swipeRefreshLayout.setRefreshing(true);
        final ArrayList<SelectedCategory> selectedCategoryArrayList = new ArrayList<>();
        getBundle();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        SpecificArticle specificArticle = retrofit.create(SpecificArticle.class);
        Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call = specificArticle.getSpecificArticle(selectedForYouCategory);
        call.enqueue(new Callback<com.example.newsapp.holders.specificarticle.SpecificArticle>() {
            @Override
            public void onResponse(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Response<com.example.newsapp.holders.specificarticle.SpecificArticle> response) {
                try { //ERROR ERROR ERROR -crashes sometimes because it cant get response FIX FIX FIX
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //swipeRefreshLayout.setRefreshing(false);
                buildRV(selectedCategoryArrayList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Throwable t) {
                Toast.makeText(ForYouSelectedActivity.this, "Retrofit failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setToggleButton() {
        favoriteForYou.setId(id);
        favoriteForYou.setFavoriteSectionName(selectedForYouCategory);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (database.forYouDao().isFavorite(id) != 1) {
                    setFollow();
                } else {
                    setUnfollow();
                    database.forYouDao().delete(favoriteForYou);
                }

            }
        });
    }

    private void checkFavorite() {
        if (database.forYouDao().isFavorite(id) == 1) {
            setFollow();

        } else
            setUnfollow();
    }

    private void setFollow() {
        toggleButton.setChecked(true);
        toggleButton.setTextColor(Color.parseColor("#578DB8"));
        toggleButton.setBackground(ContextCompat.getDrawable(toggleButton.getContext(), R.drawable.toggle_border));
        database.forYouDao().insert(favoriteForYou);
    }

    private void setUnfollow() {
        toggleButton.setChecked(false);
        database.forYouDao().delete(favoriteForYou);
        toggleButton.setTextColor(Color.parseColor("#ffffff"));
        toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(toggleButton.getContext(), R.drawable.toggle_fill));

    }
}
