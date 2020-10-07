package com.example.newsapp.ui.loginANDregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private TextView txtCreateAccount;
    private TextView txtLoginWithEmail;
    private LinearLayout linearLayoutGoogle;
    private LinearLayout linearLayoutFacebook;
    private ProgressBar progressBar;
    private CallbackManager callbackManager;
    //This had to be created so that the facebook login would work (registerCallback() works only on the button that facebook provides)
    private LoginButton facebookLoginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        bindUI();
        setTxtLoginWithEmail();
        setLinearLayoutGoogle();
        setLinearLayoutFacebook();
        setTxtCreateAccountListener();
        super.onCreate(savedInstanceState);
    }

    private void bindUI() {
        txtCreateAccount = findViewById(R.id.txt_create_account);
        linearLayoutGoogle = findViewById(R.id.btn_google_login);
        progressBar = findViewById(R.id.progress_bar_login);
        linearLayoutFacebook = findViewById(R.id.btn_facebook_login);
        facebookLoginButton = findViewById(R.id.btn_fb_login);
        txtLoginWithEmail=findViewById(R.id.txt_email_login);
    }

    private void setLinearLayoutGoogle() {
        createGoogleRequest();
        linearLayoutGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signInWithGoogle();
            }
        });
    }

    private void createGoogleRequest() {
        final GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                Toast.makeText(LoginActivity.this, "Google sign in succesful", Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(LoginActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            onBackPressed();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(android.R.id.content), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void setTxtCreateAccountListener() {
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setFacebookLoginButton() {
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
            }
        });
    }

    private void setLinearLayoutFacebook() {
        linearLayoutFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   setFacebookLoginButton();
                facebookLoginButton.performClick();
            }
        });
    }

    private boolean checkIfFacebookUserIsLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && !accessToken.isExpired();
    }

    private boolean checkIfGoogleUserIsLoggedIn() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        return firebaseUser != null;
    }

    @Override
    protected void onStart() {
        Log.d("TAG","Google logged in: "+checkIfGoogleUserIsLoggedIn());
        Log.d("TAG","Facebook logged in: "+checkIfFacebookUserIsLoggedIn());
        if (checkIfFacebookUserIsLoggedIn() || checkIfGoogleUserIsLoggedIn()) {
            Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }



    private void setTxtLoginWithEmail(){
        txtLoginWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,LoginWithEmailActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
