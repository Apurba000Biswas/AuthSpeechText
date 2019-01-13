package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{
    private static final int REQ_CODE_GOOGLE_SIGNIN = 32767 / 2;


    private GoogleApiClient google;
    private TextToSpeech tts;
    private boolean isTTsInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTTsInitialized = false;

        // Text to speech initialization
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                isTTsInitialized = true;
            }
        });

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
        // Say some word here
        if (isTTsInitialized){
            tts.speak("Hey idiot, you need to log in now. Booyah!",
                    TextToSpeech.QUEUE_FLUSH, null);
        }

        // connect to Google server to log in
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(google);
        startActivityForResult(intent, REQ_CODE_GOOGLE_SIGNIN);
    }

    /*
     * This method is called when Google Sign-in comes back to my activity.
     * We grab the sign-in results and display the user's name and email address.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQ_CODE_GOOGLE_SIGNIN) {
            TextView outPutTextView = findViewById(R.id.tv_Output);
            // google sign-in has returned
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess()) {
                // yay; user logged in successfully
                GoogleSignInAccount acct = result.getSignInAccount();

                outPutTextView.setText("You signed in as: " + acct.getDisplayName() + " "
                        + acct.getEmail());
            } else {
                outPutTextView.setText("Login fail. :(");
            }
        }
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
