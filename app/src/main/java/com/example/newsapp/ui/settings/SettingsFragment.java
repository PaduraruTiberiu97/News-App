package com.example.newsapp.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;

import com.example.newsapp.R;
import com.example.newsapp.ui.loginANDregister.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends PreferenceFragment {
    private FirebaseAuth firebaseAuth;
    Preference preference;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        preference = findPreference("LoginOrRegister");


    }

    private void signOut() {
        firebaseAuth.signOut();
        LoginManager.getInstance().logOut();
        getActivity().onBackPressed();

    }

    private boolean checkIfUserIsLoggedIn(){
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current = firebaseAuth.getCurrentUser();
        return current==null;
    }

    @Override
    public void onResume() {
        if (checkIfUserIsLoggedIn()) {
            preference.setTitle("Login or Register");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent=new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
        } else {
            preference.setTitle("Logout");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new AlertDialog.Builder(getContext())
                            .setMessage("Are you sure you want to log out?")
                            .setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    signOut();

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();


                    return false;
                }
            });
        }
        super.onResume();
    }
}