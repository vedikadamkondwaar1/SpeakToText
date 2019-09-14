package com.example.speaktotext;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView,speakup;
    
    //private final int REQ_SPEECH_INPUT=100;
    //private int STORAGE_PERMISSION_Code=23;
    Calendar calendar;
    String timesysa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.voiceInput);
        speakup = findViewById(R.id.btnSpeak);
        speakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked" + speakup.getText(), Toast.LENGTH_SHORT).show();
                boolnalkehalke();
            }
        });

    }

    private void boolnalkehalke() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak something");
        try {
            startActivityForResult(intent,20);

        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 20:
            {
                if(resultCode==RESULT_OK && null != data){
                    ArrayList<String>result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                    writedatafile(textView.getText().toString());
                }
                break;
            }
        }
    }

    private void writedatafile(String toString) {
        calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-yyyy HH:MM:SS");
        timesysa=simpleDateFormat.format(calendar.getTime());
        timesysa="externaldate"+timesysa+".txt";
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},200);
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File myFile=new File(folder,timesysa);//file name and path name
        writeData(myFile,toString);
    }

    private void writeData(File myFile, String toString) {
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(myFile);
            try {
                fileOutputStream.write(toString.getBytes());
                Toast.makeText(this, "Done"+myFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
