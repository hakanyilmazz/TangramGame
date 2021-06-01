package com.hakanyilmazz.tangramgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hakanyilmazz.tangramgame.level1.Level1Activity;

public class MainActivity extends AppCompatActivity {

    private TextView highScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highScoreText = findViewById(R.id.mainActivity_highScoreText);
        setHighScoreToTextView();
    }

    private void setHighScoreToTextView() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("highScore", 0);

        highScoreText.setText("High Score: " + highScore);
    }

    public void play(View view) {
        Intent intent = new Intent(this, Level1Activity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHighScoreToTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.developers:
                intent = new Intent(MainActivity.this, DevelopersActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void help(View view) {
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    public void developers(View view) {
        Intent intent = new Intent(MainActivity.this, DevelopersActivity.class);
        startActivity(intent);
    }

}