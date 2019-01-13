package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    private static final int REQ_CODE_GOOGLE_SIGNIN = 32767 / 2;


    private GoogleApiClient google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInClicked();
            }
        });


        // request the user's ID, email address, and basic profile
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // build API client with access to Sign-In API and options above
        google = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addConnectionCallbacks(this)
                .build();
    }


    private void signInClicked(){
        Toast.makeText(this, "Sign in clicked", Toast.LENGTH_SHORT).show();

        // connect to Google server to log in
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
        startActivityForResult(intent, REQ_CODE_GOOGLE_SIGNIN);
    }






    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v("MainActivity", "*********onConnected*********");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("MainActivity", "*********onConnectionSuspended*********");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v("MainActivity", "*********onConnectionFailed*********");
    }
}
