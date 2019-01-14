package com.example.apurba.authspeechtext;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class TextSpeechActivity extends AppCompatActivity {
    private static final int SPEECH_TO_TEXT_REQ_CODE = 123;


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
                String input = etUserInputField.getText().toString();
                spellText(input);
            }
        });

        Button STT = findViewById(R.id.bt_stt);
        STT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSpeechToText();
            }
        });
    }

    private void spellText(String inputedText){
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

    private void performSpeechToText(){
        // perform speech-to-text using an intent
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // prompt tells user what to say
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speck something");
        startActivityForResult(intent, SPEECH_TO_TEXT_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_TO_TEXT_REQ_CODE){
            TextView resultTextView = findViewById(R.id.tv_login_message);
            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = list.get(0);
            resultTextView.setText("You spock " + spokenText);
        }
    }
}
