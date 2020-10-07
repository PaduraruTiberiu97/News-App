package com.example.newsapp.ui.loginANDregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;

public class LoginWithEmailActivity extends AppCompatActivity {
    TextView txtCreateAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_with_email);
        bindUI();
        setTxtCreateAccountListener();
        super.onCreate(savedInstanceState);
    }

    private void bindUI(){
        txtCreateAccount=findViewById(R.id.txt_login_with_email_create_account);
    }

    public void setTxtCreateAccountListener() {
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginWithEmailActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}