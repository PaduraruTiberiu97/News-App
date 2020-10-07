package com.example.newsapp.ui.loginANDregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;

public class RegisterActivity extends AppCompatActivity {
    TextView txtHaveAccount;
    TextView txtRegisterWithEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        bindUI();
        setTxtHaveAccount();
        setTxtRegisterWithEmail();
        super.onCreate(savedInstanceState);
    }

    private void bindUI() {
        txtHaveAccount = findViewById(R.id.txt_have_account);
        txtRegisterWithEmail = findViewById(R.id.txt_email_register);
    }

    private void setTxtHaveAccount() {
        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setTxtRegisterWithEmail() {
        txtRegisterWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, RegisterWithEmailActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
