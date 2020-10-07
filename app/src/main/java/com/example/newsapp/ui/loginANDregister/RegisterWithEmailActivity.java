package com.example.newsapp.ui.loginANDregister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterWithEmailActivity extends AppCompatActivity {
    TextView textView;
    Button btnRegister;
    FirebaseAuth firebaseAuth;
    TextInputEditText edtEmailId, edtPassword, edtPasswordVer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_with_email);
        bindUI();
        setTxtHaveAccount();
        setBtnRegister();
        super.onCreate(savedInstanceState);
    }


    private void bindUI() {
        textView = findViewById(R.id.qqqqqqqqqqqqqqqqqq);
        btnRegister = findViewById(R.id.btn_register_register_with_email);
        edtEmailId = findViewById(R.id.edt_txt_email_register_with_email);
        edtPassword = findViewById(R.id.edt_txt_password_register_with_email);
        edtPasswordVer = findViewById(R.id.edt_txt_confirm_password_register_with_email);
    }

    private void setTxtHaveAccount() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterWithEmailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmailId.getText().toString();
                String password = edtPassword.getText().toString();
                String passwordVer = edtPasswordVer.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edtEmailId.setError("You need to provide an email adress");
                    edtEmailId.requestFocus();

                } else if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Please include a password");
                    edtPassword.requestFocus();
                } else if (TextUtils.isEmpty(passwordVer)) {
                    edtPasswordVer.setError("Please include a password");
                    edtPasswordVer.requestFocus();
                } else if (!(password.equals(passwordVer))) {
                    edtPassword.setError("Pasword entries must match");
                    edtPassword.requestFocus();
                } else if ((!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)))  {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterWithEmailActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                String errorMessage = Objects.requireNonNull(task.getException()).toString();
                                Toast.makeText(RegisterWithEmailActivity.this, "Eroare: " + errorMessage, Toast.LENGTH_SHORT).show();

                            } else {

                                finish();
                                /*Intent intent = new Intent(RegisterWithEmailActivity.this, );
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);*/
                            }
                            //////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                    });
                } else {
                    Toast.makeText(RegisterWithEmailActivity.this,"Eroare",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
