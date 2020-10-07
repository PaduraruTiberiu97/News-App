package com.example.newsapp.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import com.example.newsapp.R;

public class SettingsActivity extends AppCompatActivity {
    ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().add(R.id.fragment_container_settings, new SettingsFragment()).commit();
        bindUI();

        setImageButton();
        super.onCreate(savedInstanceState);
    }

    private void bindUI() {
        imageButton = findViewById(R.id.btn_back_settings);
    }

    private void setImageButton() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
