package com.hakanyilmazz.tangramgame.level4;

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

import com.hakanyilmazz.tangramgame.R;
import com.hakanyilmazz.tangramgame.level5.Level5Activity;
import com.hakanyilmazz.tangramgame.level_controllers.TimeController;

import java.util.ArrayList;

public class Level4Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final long LEVEL4_TIME = 27500 + TimeController.increaseTime();
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
        setContentView(R.layout.activity_level4);

        Intent intent = getIntent();
        lastScore = intent.getIntExtra("lastScore", 0);

        scoreText = findViewById(R.id.level4ScoreText);
        timeText = findViewById(R.id.level4TimeText);

        scoreText.setText("Score: " + lastScore);

        setMustBeImageViews();
        setMustBeImagesTags();

        setOriginalImageViews();
        setOriginalImagesTags();

        setMustBeImageViewsColorFilters(Color.LTGRAY);

        setOriginalImagesOnLongClickListeners();
        setMustBeImagesOnDragListeners();

        setCountDownTimer(LEVEL4_TIME);
    }

    private void setCountDownTimer(long level1Time) {
        countDownTimer = new CountDownTimer(level1Time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Left: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Level4Activity.this, "Time's Up!", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(Level4Activity.this, "See You Again!", Toast.LENGTH_SHORT).show();
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
        originalImageViews.add(findViewById(R.id.level4_ust_ucgen_sari2));
        originalImageViews.add(findViewById(R.id.level4_sag_alt_ucgen2));
        originalImageViews.add(findViewById(R.id.level4_kare2));
        originalImageViews.add(findViewById(R.id.level4_sol_ust_ucgen2));
        originalImageViews.add(findViewById(R.id.level4_sag_ust_ucgen2));
        originalImageViews.add(findViewById(R.id.level4_yamuk2));
        originalImageViews.add(findViewById(R.id.level4_ust_ucgen_mavi2));
    }

    private void setMustBeImageViews() {
        mustBeImageViews.add(findViewById(R.id.level4_ust_ucgen_sari));
        mustBeImageViews.add(findViewById(R.id.level4_sag_alt_ucgen));
        mustBeImageViews.add(findViewById(R.id.level4_kare));
        mustBeImageViews.add(findViewById(R.id.level4_sol_ust_ucgen));
        mustBeImageViews.add(findViewById(R.id.level4_sag_ust_ucgen));
        mustBeImageViews.add(findViewById(R.id.level4_yamuk));
        mustBeImageViews.add(findViewById(R.id.level4_ust_ucgen_mavi));
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
        mustBeImageViews.get(ShapeIndex.UST_UCGEN_SARI).setTag("UST_UCGEN_SARI");
        mustBeImageViews.get(ShapeIndex.SAG_ALT_UCGEN).setTag("SAG_ALT_UCGEN");
        mustBeImageViews.get(ShapeIndex.KARE).setTag("KARE");
        mustBeImageViews.get(ShapeIndex.SOL_UST_UCGEN).setTag("SOL_UST_UCGEN");
        mustBeImageViews.get(ShapeIndex.SAG_UST_UCGEN).setTag("SAG_UST_UCGEN");
        mustBeImageViews.get(ShapeIndex.YAMUK).setTag("YAMUK");
        mustBeImageViews.get(ShapeIndex.UST_UCGEN_MAVI).setTag("UST_UCGEN_MAVI");
    }

    private void setOriginalImagesTags() {
        originalImageViews.get(ShapeIndex.UST_UCGEN_SARI).setTag("UST_UCGEN_SARI2");
        originalImageViews.get(ShapeIndex.SAG_ALT_UCGEN).setTag("SAG_ALT_UCGEN2");
        originalImageViews.get(ShapeIndex.KARE).setTag("KARE2");
        originalImageViews.get(ShapeIndex.SOL_UST_UCGEN).setTag("SOL_UST_UCGEN2");
        originalImageViews.get(ShapeIndex.SAG_UST_UCGEN).setTag("SAG_UST_UCGEN2");
        originalImageViews.get(ShapeIndex.YAMUK).setTag("YAMUK2");
        originalImageViews.get(ShapeIndex.UST_UCGEN_MAVI).setTag("UST_UCGEN_MAVI2");
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

        Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();

        setHighScore();

        Intent intent = new Intent(Level4Activity.this, Level5Activity.class);
        intent.putExtra("lastScore", lastScore);
        startActivity(intent);
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
            case "UST_UCGEN_SARI2":
                return ShapeIndex.UST_UCGEN_SARI;
            case "SAG_ALT_UCGEN2":
                return ShapeIndex.SAG_ALT_UCGEN;
            case "KARE2":
                return ShapeIndex.KARE;
            case "SOL_UST_UCGEN2":
                return ShapeIndex.SOL_UST_UCGEN;
            case "SAG_UST_UCGEN2":
                return ShapeIndex.SAG_UST_UCGEN;
            case "YAMUK2":
                return ShapeIndex.YAMUK;
            case "UST_UCGEN_MAVI2":
                return ShapeIndex.UST_UCGEN_MAVI;
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