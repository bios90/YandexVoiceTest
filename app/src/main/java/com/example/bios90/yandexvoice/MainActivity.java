package com.example.bios90.yandexvoice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Language;
import ru.yandex.speechkit.OnlineModel;
import ru.yandex.speechkit.OnlineRecognizer;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Track;

public class MainActivity extends AppCompatActivity implements RecognizerListener
{
    private static final String TAG = "MainActivity";

    Button start, stop;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);
        textView = findViewById(R.id.tvTextView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 123);
        }

        try
        {
            SpeechKit.getInstance().init(getApplicationContext(), "95c4c09e-eda4-4cfc-99b7-7a21626390a6");
        } catch (Exception e)
        {
            Log.e(TAG, "onCreate: ________--|||||||||||---_________ " + e.getMessage());
        }

        final OnlineRecognizer onlineRecognizer = new OnlineRecognizer.Builder(Language.ENGLISH, OnlineModel.FREEFORM, this)
                .setDisableAntimat(false)
                .setEnablePunctuation(true)
                //.setFinishAfterFirstUtterance(false)
                //.setWaitForResultTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        onlineRecognizer.prepare();

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
                {
                    // TODO: Consider calling
                    Log.e(TAG, "onClick: Error Herer!!!!!" );
                    return;
                }
                onlineRecognizer.startRecording();
            }
        });

        stop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onlineRecognizer.stopRecording();
            }
        });
    }

    @Override
    public void onRecordingBegin(@NonNull Recognizer recognizer)
    {

    }

    @Override
    public void onSpeechDetected(@NonNull Recognizer recognizer)
    {

    }

    @Override
    public void onSpeechEnds(@NonNull Recognizer recognizer)
    {

    }

    @Override
    public void onRecordingDone(@NonNull Recognizer recognizer)
    {

    }

    @Override
    public void onPowerUpdated(@NonNull Recognizer recognizer, float v)
    {

    }

    @Override
    public void onPartialResults(@NonNull Recognizer recognizer, @NonNull Recognition recognition, boolean b)
    {
        String text = recognition.getBestResultText();
        Log.e(TAG, "onPartialResults: "+text );
        textView.setText(text);
    }

    @Override
    public void onRecognitionDone(@NonNull Recognizer recognizer)
    {
        Log.e(TAG, "onRecognitionDone: ");

    }

    @Override
    public void onRecognizerError(@NonNull Recognizer recognizer, @NonNull Error error)
    {

    }

    @Override
    public void onMusicResults(@NonNull Recognizer recognizer, @NonNull Track track)
    {

    }
}
