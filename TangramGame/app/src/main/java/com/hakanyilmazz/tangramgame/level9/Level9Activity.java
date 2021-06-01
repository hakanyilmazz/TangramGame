package com.hakanyilmazz.tangramgame.level9;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hakanyilmazz.tangramgame.MainActivity;
import com.hakanyilmazz.tangramgame.R;
import com.hakanyilmazz.tangramgame.level_controllers.TimeController;

import java.util.ArrayList;

public class Level9Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final long LEVEL9_TIME = 15500 + TimeController.increaseTime();
    private final ArrayList<ImageView> mustBeImageViews = new ArrayList<>();
    private final ArrayList<ImageView> originalImageViews = new ArrayList<>();
    private int lastScore = 0;
    private int scoreCounter = 0;
    private TextView scoreText;
    private TextView timeText;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level9);

        Intent intent = getIntent();
        lastScore = intent.getIntExtra("lastScore", 0);

        scoreText = findViewById(R.id.level9ScoreText);
        timeText = findViewById(R.id.level9TimeText);

        scoreText.setText("Score: " + lastScore);

        setMustBeImageViews();
        setMustBeImagesTags();

        setOriginalImageViews();
        setOriginalImagesTags();

        setMustBeImageViewsColorFilters(Color.LTGRAY);

        setOriginalImagesOnLongClickListeners();
        setMustBeImagesOnDragListeners();

        setCountDownTimer(LEVEL9_TIME);
    }

    private void setCountDownTimer(long level1Time) {
        countDownTimer = new CountDownTimer(level1Time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Left: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Level9Activity.this, "Time's Up!", Toast.LENGTH_SHORT).show();
                setTimesUpAlert();
            }
        };

        countDownTimer.start();
    }

    private void setTimesUpAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Time's Up!");
        alert.setMessage("Restart the level?");
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDownTimer.cancel();

                lastScore -= scoreCounter;

                Intent intent = getIntent();
                intent.putExtra("lastScore", lastScore);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDownTimer.cancel();

                Toast.makeText(Level9Activity.this, "See You Again!", Toast.LENGTH_SHORT).show();
                setHighScore();
                finish();
            }
        });

        alert.show();
    }

    private void setHighScore() {
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = this.getSharedPreferences(packageName, MODE_PRIVATE);

        int highScore = sharedPreferences.getInt("highScore", 0);

        if (lastScore > highScore) {
            sharedPreferences.edit().putInt("highScore", lastScore).apply();
        }
    }

    private void setMustBeImageViewsColorFilters(int colorId) {
        for (ImageView imageView : mustBeImageViews) {
            imageView.setColorFilter(colorId);
        }
    }

    private void setOriginalImageViews() {
        originalImageViews.add(findViewById(R.id.level9_griUcgen2));
        originalImageViews.add(findViewById(R.id.level9_kare2));
        originalImageViews.add(findViewById(R.id.level9_turuncuUcgen2));
        originalImageViews.add(findViewById(R.id.level9_yamuk2));
        originalImageViews.add(findViewById(R.id.level9_sariUcgen2));
        originalImageViews.add(findViewById(R.id.level9_maviUcgen2));
    }

    private void setMustBeImageViews() {
        mustBeImageViews.add(findViewById(R.id.level9_griUcgen));
        mustBeImageViews.add(findViewById(R.id.level9_kare));
        mustBeImageViews.add(findViewById(R.id.level9_turuncuUcgen));
        mustBeImageViews.add(findViewById(R.id.level9_yamuk));
        mustBeImageViews.add(findViewById(R.id.level9_sariUcgen));
        mustBeImageViews.add(findViewById(R.id.level9_maviUcgen));
    }

    private void setOriginalImagesOnLongClickListeners() {
        for (ImageView imageView : originalImageViews) {
            imageView.setOnLongClickListener(this);
        }
    }

    private void setMustBeImagesOnDragListeners() {
        for (ImageView imageView : mustBeImageViews) {
            imageView.setOnDragListener(this);
        }
    }

    private void setMustBeImagesTags() {
        mustBeImageViews.get(ShapeIndex.GRI_UCGEN).setTag("GRI_UCGEN");
        mustBeImageViews.get(ShapeIndex.KARE).setTag("KARE");
        mustBeImageViews.get(ShapeIndex.TURUNCU_UCGEN).setTag("TURUNCU_UCGEN");
        mustBeImageViews.get(ShapeIndex.YAMUK).setTag("YAMUK");
        mustBeImageViews.get(ShapeIndex.SARI_UCGEN).setTag("SARI_UCGEN");
        mustBeImageViews.get(ShapeIndex.MAVI_UCGEN).setTag("MAVI_UCGEN");
    }

    private void setOriginalImagesTags() {
        originalImageViews.get(ShapeIndex.GRI_UCGEN).setTag("GRI_UCGEN2");
        originalImageViews.get(ShapeIndex.KARE).setTag("KARE2");
        originalImageViews.get(ShapeIndex.TURUNCU_UCGEN).setTag("TURUNCU_UCGEN2");
        originalImageViews.get(ShapeIndex.YAMUK).setTag("YAMUK2");
        originalImageViews.get(ShapeIndex.SARI_UCGEN).setTag("SARI_UCGEN2");
        originalImageViews.get(ShapeIndex.MAVI_UCGEN).setTag("MAVI_UCGEN2");
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
            case DragEvent.ACTION_DRAG_LOCATION:
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                v.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                View imageObject = (View) event.getLocalState();

                String tag = imageObject.getTag().toString();
                int index = getShapeIndex(tag);

                float x = event.getX();
                float y = event.getY();

                float mustBeImageX = mustBeImageViews.get(index).getPivotX();
                float mustBeImageY = mustBeImageViews.get(index).getPivotY();

                float xPositionDifferent = (float) Math.pow(mustBeImageX - x, 2);
                float yPositionDifferent = (float) Math.pow(mustBeImageY - y, 2);
                float positionDifferent = (float) Math.sqrt(xPositionDifferent + yPositionDifferent);

                if (positionDifferent < 30) {
                    v.invalidate();

                    ViewGroup oldDesignArea = (ViewGroup) imageObject.getParent();
                    oldDesignArea.removeView(imageObject);

                    mustBeImageViews.get(index).setColorFilter(null);
                    imageObject.setVisibility(View.INVISIBLE);

                    increaseScore();

                    if (scoreCounter == mustBeImageViews.size()) {
                        goToNewLevel();
                    }

                    return true;
                }

            default:
                break;
        }

        ImageView imageView = (ImageView) event.getLocalState();
        imageView.setColorFilter(Color.BLUE);

        return false;
    }

    private void increaseScore() {
        lastScore++;
        scoreCounter++;
        scoreText.setText("Score: " + lastScore);
    }

    private void goToNewLevel() {
        countDownTimer.cancel();
        Toast.makeText(this, "Game Finished!", Toast.LENGTH_SHORT).show();
        setHighScore();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData clipData = new ClipData(v.getTag().toString(), mimeTypes, item);
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(clipData, shadowBuilder, v, 0);

        ImageView imageView = (ImageView) v;
        imageView.setColorFilter(Color.GREEN);

        String tag = imageView.getTag().toString();
        setSelectedMustBeEnabledTrue(tag);

        return true;
    }

    private int getShapeIndex(String tag) {
        switch (tag) {
            case "GRI_UCGEN2":
                return ShapeIndex.GRI_UCGEN;
            case "KARE2":
                return ShapeIndex.KARE;
            case "TURUNCU_UCGEN2":
                return ShapeIndex.TURUNCU_UCGEN;
            case "YAMUK2":
                return ShapeIndex.YAMUK;
            case "SARI_UCGEN2":
                return ShapeIndex.SARI_UCGEN;
            case "MAVI_UCGEN2":
                return ShapeIndex.MAVI_UCGEN;
            default:
                return 0;
        }
    }

    private void setSelectedMustBeEnabledTrue(String tag) {
        String tempTag = tag.substring(0, tag.length() - 1);

        for (ImageView image : mustBeImageViews) {
            String imageTag = image.getTag().toString();

            if (tempTag.equals(imageTag)) {
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
        }
    }

}