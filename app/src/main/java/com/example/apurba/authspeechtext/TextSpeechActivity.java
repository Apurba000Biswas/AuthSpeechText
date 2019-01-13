package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextSpeechActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_speech);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        TextView tvMessage = findViewById(R.id.tv_login_message);
        tvMessage.setText("You Signed in: " + email);
    }
}
