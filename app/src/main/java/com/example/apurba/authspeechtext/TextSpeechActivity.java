package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TextSpeechActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private boolean isTTsInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_speech);
        isTTsInitialized = false;

        // Text to speech initialization
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                isTTsInitialized = true;
            }
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        TextView tvMessage = findViewById(R.id.tv_login_message);
        tvMessage.setText("You Signed in: " + email);

        final EditText etUserInputField = findViewById(R.id.et_tts);

        Button spellButt = findViewById(R.id.bt_tts_play);
        spellButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputedText = etUserInputField.getText().toString();
                if (isTTsInitialized){
                    if (!TextUtils.isEmpty(inputedText)){
                        tts.speak(inputedText,
                                TextToSpeech.QUEUE_FLUSH, null);
                    }else {
                        tts.speak("idiot, you typed nothing",
                                TextToSpeech.QUEUE_FLUSH, null);
                    }

                }
            }
        });
    }
}
