package com.example.newsapp.ui.foryou;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.newsapp.R;
import com.example.newsapp.database.FavoriteForYouDatabase;
import com.example.newsapp.ui.loginANDregister.LoginActivity;
import com.example.newsapp.ui.settings.SettingsActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ForYouFragment extends Fragment implements OnFollowBtnClick {
    public static FavoriteForYouDatabase database;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    NavController navController = null;
    CustomSnackbar snackbar;
    TextView textView;
    RecyclerView recyclerView;
    private ArrayList<ForYouItems> forYouItems = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataForRv();


    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_foryou, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        database = Room.databaseBuilder(getContext(), FavoriteForYouDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        bindUI(root);
        setTextView();
        setupToolbar();
        buildRV();
        checkFollowed(root);
        return root;

    }

    private void buildRV() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(new ForYouRecyclerViewAdapter(forYouItems, this));

    }

    private void bindUI(View view) {
        toolbar = view.findViewById(R.id.toolbar_foryou);
        textView = view.findViewById(R.id.textView_interested_in);
        recyclerView = view.findViewById(R.id.rv_foryou);
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

    private void setTextView() {
        String sourceString = "<b>" + "What are you interested in?" + "</b> " + " We'll update this section regularly with stories for you.";
        textView.setText(Html.fromHtml(sourceString));
    }

    private void setDataForRv() {
        TypedArray imagesArray = getResources().obtainTypedArray(R.array.for_you_section_images);
        String[] sectionNames = getResources().getStringArray(R.array.for_you_section_titles);
        for (int i = 0; i < getResources().getIntArray(R.array.for_you_section_images).length; i++) {
            forYouItems.add(new ForYouItems(i + 1, imagesArray.getResourceId(i, 0), sectionNames[i]));
        }
    }


    @Override
    public void onFlwClick() {
        if (database.forYouDao().getCount() != 0) {
            makeSnackbar(getView());
        } else {
            snackbar.dismiss();
        }
    }

    void checkFollowed(View view) {
        if (database.forYouDao().getCount() != 0) {
            makeSnackbar(view);
        }
    }

    private void makeSnackbar(View view) {
        snackbar = CustomSnackbar.make((ViewGroup) view, CustomSnackbar.LENGTH_INDEFINITE);
        if (database.forYouDao().getCount() == 1)
            snackbar.setText("Following " + (database.forYouDao().getCount()) + " topic");
        else
            snackbar.setText("Following " + (database.forYouDao().getCount()) + " topics");
        snackbar.setAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog();
            }
        });
        snackbar.show();

    }

    private void createAlertDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("To follow, please login or register for a New News Times account.")
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startForYouLoggedIn();

                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onResume() {
        transtitionToFragmentIfUserIsLoggedIn();
        buildRV();
        if (database.forYouDao().getCount() != 0) {
            makeSnackbar(getView());
        } else try {
            snackbar.dismiss();
        } catch (Exception e) {
            Log.d("TAG", "Snackbar not yet instantiated");
        }
        super.onResume();
    }

    private void startForYouLoggedIn() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private boolean checkIfGoogleUserIsLoggedIn() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null;
    }

    private void transtitionToFragmentIfUserIsLoggedIn() {
        if (checkIfGoogleUserIsLoggedIn()) {
            navController = Navigation.findNavController(getView());
            navController.navigate(R.id.action_navigation_foryou_to_navigation_foryou_loggedin2);
        }
    }


}