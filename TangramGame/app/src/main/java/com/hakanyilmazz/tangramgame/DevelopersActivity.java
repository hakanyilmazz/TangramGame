package com.hakanyilmazz.tangramgame;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DevelopersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        TextView developersNameText = findViewById(R.id.developersNameText);
        String names = "1- Hakan Yılmaz\n2- Ayşe Madran\n3- Mehmet Kulubecioğlu\n4- Vural Azar";
        developersNameText.setText(names);
    }

}