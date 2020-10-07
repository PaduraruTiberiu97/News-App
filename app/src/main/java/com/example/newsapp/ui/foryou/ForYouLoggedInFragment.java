package com.example.newsapp.ui.foryou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.newsapp.ApiInterfaces.ApiConstants;
import com.example.newsapp.ApiInterfaces.SpecificArticle;
import com.example.newsapp.R;
import com.example.newsapp.database.FavoriteForYou;
import com.example.newsapp.database.FavoriteForYouDatabase;
import com.example.newsapp.holders.specificarticle.Docs;
import com.example.newsapp.ui.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForYouLoggedInFragment extends Fragment {
    public static FavoriteForYouDatabase database;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    NavController navController = null;
    Toolbar toolbar;
    ArrayList<ForYouLoggedInItems> forYouLoggedInItemsArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.acrivity_foryou_logged_in, container, false);
        bindUI(root);
        firebaseAuth = FirebaseAuth.getInstance();
        setupToolbar();
        database = Room.databaseBuilder(getActivity(), FavoriteForYouDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        initAndGetDataFromDB();
        return root;
    }

    private void buildRV(ArrayList<ForYouLoggedInItems> arrayList) {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setHasFixedSize(true);
        Collections.shuffle(arrayList);
        ForYouLoggedInRecyclerViewAdapter adapter = new ForYouLoggedInRecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
    }

    public void retrofitCall(final String string) {


        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        SpecificArticle specificArticle = retrofit.create(SpecificArticle.class);


        Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call = specificArticle.getSpecificArticle(string);
        call.enqueue(new Callback<com.example.newsapp.holders.specificarticle.SpecificArticle>() {
            @Override
            public void onResponse(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Response<com.example.newsapp.holders.specificarticle.SpecificArticle> response) {
                try { //ERROR ERROR ERROR -crashes sometimes because it cant get response FIX FIX FIX
                    List<Docs> docs = response.body().getResponse().getDocs();
                    for (int i = 0; i < docs.size(); i++) {
                        try {
                            forYouLoggedInItemsArrayList.add(new ForYouLoggedInItems(docs.get(i).getHeadline().getArticleTitle(),
                                    docs.get(i).getArticleContent(),
                                    docs.get(i).getMultimedia().get(0).getArticleImageUrl(),
                                    docs.get(i).getByline().getAuthorName(),
                                    docs.get(i).getPublishedDate(),
                                    docs.get(i).getArticleWebUrl(),
                                    string));
                        } catch (IndexOutOfBoundsException e) {
                            forYouLoggedInItemsArrayList.add(new ForYouLoggedInItems(docs.get(i).getHeadline().getArticleTitle(),
                                    docs.get(i).getArticleContent(),
                                    "",
                                    docs.get(i).getByline().getAuthorName(),
                                    docs.get(i).getPublishedDate(),
                                    docs.get(i).getArticleWebUrl(),
                                    string));
                            Log.d("TAG", "Exception " + e);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                buildRV(addAll(forYouLoggedInItemsArrayList));


            }

            @Override
            public void onFailure(Call<com.example.newsapp.holders.specificarticle.SpecificArticle> call, Throwable t) {
                Toast.makeText(getActivity(), "Retrofit failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindUI(View view) {
        recyclerView = view.findViewById(R.id.rv_foryou_logged_in);
        toolbar = view.findViewById(R.id.toolbar_foryou_logged_in);
    }

    private ArrayList<ForYouLoggedInItems> addAll(ArrayList<ForYouLoggedInItems> arrayList) {
        Set<ForYouLoggedInItems> set = new LinkedHashSet<>(arrayList);
        set.addAll(arrayList);
        return new ArrayList<>(set);
    }

    private void initAndGetDataFromDB() {
        List<FavoriteForYou> arrayList = database.forYouDao().getFavoriteForYou();

        for (int i = 0; i < arrayList.size(); i++) {
            stringArrayList.add(arrayList.get(i).getFavoriteSectionName());
            retrofitCall(stringArrayList.get(i));
        }
    }

    public void setupToolbar() {
        toolbar.inflateMenu(R.menu.for_you_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }


    private boolean checkIfGoogleUserIsLoggedIn() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null;
    }

    @Override
    public void onResume() {
        transtitionToFragmentIfUserIsNotLoggedIn();
        super.onResume();
    }

    private void transtitionToFragmentIfUserIsNotLoggedIn() {
        if (!checkIfGoogleUserIsLoggedIn()) {
            navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_navigation_foryou_loggedin_to_navigation_foryou);
        }
    }


}
