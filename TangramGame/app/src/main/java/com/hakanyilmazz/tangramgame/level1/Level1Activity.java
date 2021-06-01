package com.hakanyilmazz.tangramgame.level1;

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
import com.hakanyilmazz.tangramgame.level2.Level2Activity;
import com.hakanyilmazz.tangramgame.level_controllers.TimeController;

import java.util.ArrayList;

public class Level1Activity extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener {

    private static final long LEVEL1_TIME = 35500 + TimeController.increaseTime();

    private final ArrayList<ImageView> mustBeImageViews = new ArrayList<>();
    private final ArrayList<ImageView> originalImageViews = new ArrayList<>();

    private int scoreCounter = 0;

    private TextView scoreText;
    private TextView timeText;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        scoreText = findViewById(R.id.level1ScoreText);
        timeText = findViewById(R.id.level1TimeText);

        setMustBeImageViews();
        setMustBeImagesTags();

        setOriginalImageViews();
        setOriginalImagesTags();

        setMustBeImageViewsColorFilters(Color.LTGRAY);

        setOriginalImagesOnLongClickListeners();
        setMustBeImagesOnDragListeners();

        setCountDownTimer(LEVEL1_TIME);
    }

    private void setCountDownTimer(long level1Time) {
        countDownTimer = new CountDownTimer(level1Time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Left: " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(Level1Activity.this, "Time's Up!", Toast.LENGTH_SHORT).show();
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

                Intent intent = getIntent();
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDownTimer.cancel();

                Toast.makeText(Level1Activity.this, "See You Again!", Toast.LENGTH_SHORT).show();
                setHighScore(scoreCounter);
                finish();
            }
        });

        alert.show();
    }

    private void setHighScore(int scoreCounter) {
        String packageName = getPackageName();
        SharedPreferences sharedPreferences = this.getSharedPreferences(packageName, MODE_PRIVATE);

        int highScore = sharedPreferences.getInt("highScore", 0);

        if (scoreCounter > highScore) {
            sharedPreferences.edit().putInt("highScore", scoreCounter).apply();
        }
    }

    private void setMustBeImageViewsColorFilters(int colorId) {
        for (ImageView imageView : mustBeImageViews) {
            imageView.setColorFilter(colorId);
        }
    }

    private void setOriginalImageViews() {
        originalImageViews.add(findViewById(R.id.yamukImage2));
        originalImageViews.add(findViewById(R.id.solAltUcgenImage2));
        originalImageViews.add(findViewById(R.id.sagUstUcgenImage2));
        originalImageViews.add(findViewById(R.id.yukariUcgenImage2));
        originalImageViews.add(findViewById(R.id.kareImage2));
        originalImageViews.add(findViewById(R.id.sagUcgenImage2));
        originalImageViews.add(findViewById(R.id.sagAltUcgenImage2));
    }

    private void setMustBeImageViews() {
        mustBeImageViews.add(findViewById(R.id.yamukImage));
        mustBeImageViews.add(findViewById(R.id.solAltUcgenImage));
        mustBeImageViews.add(findViewById(R.id.sagUstUcgenImage));
        mustBeImageViews.add(findViewById(R.id.yukariUcgenImage));
        mustBeImageViews.add(findViewById(R.id.kareImage));
        mustBeImageViews.add(findViewById(R.id.sagUcgenImage));
        mustBeImageViews.add(findViewById(R.id.sagAltUcgenImage));
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
        mustBeImageViews.get(ShapeIndex.YAMUK_IMAGE).setTag("YAMUK_IMAGE");
        mustBeImageViews.get(ShapeIndex.SOL_ALT_UCGEN_IMAGE).setTag("SOL_ALT_UCGEN_IMAGE");
        mustBeImageViews.get(ShapeIndex.SAG_UST_UCGEN_IMAGE).setTag("SAG_UST_UCGEN_IMAGE");
        mustBeImageViews.get(ShapeIndex.YUKARI_UCGEN_IMAGE).setTag("YUKARI_UCGEN_IMAGE");
        mustBeImageViews.get(ShapeIndex.KARE_IMAGE).setTag("KARE_IMAGE");
        mustBeImageViews.get(ShapeIndex.SAG_UCGEN_IMAGE).setTag("SAG_UCGEN_IMAGE");
        mustBeImageViews.get(ShapeIndex.SAG_ALT_UCGEN_IMAGE).setTag("SAG_ALT_UCGEN_IMAGE");
    }

    private void setOriginalImagesTags() {
        originalImageViews.get(ShapeIndex.YAMUK_IMAGE).setTag("YAMUK_IMAGE2");
        originalImageViews.get(ShapeIndex.SOL_ALT_UCGEN_IMAGE).setTag("SOL_ALT_UCGEN_IMAGE2");
        originalImageViews.get(ShapeIndex.SAG_UST_UCGEN_IMAGE).setTag("SAG_UST_UCGEN_IMAGE2");
        originalImageViews.get(ShapeIndex.YUKARI_UCGEN_IMAGE).setTag("YUKARI_UCGEN_IMAGE2");
        originalImageViews.get(ShapeIndex.KARE_IMAGE).setTag("KARE_IMAGE2");
        originalImageViews.get(ShapeIndex.SAG_UCGEN_IMAGE).setTag("SAG_UCGEN_IMAGE2");
        originalImageViews.get(ShapeIndex.SAG_ALT_UCGEN_IMAGE).setTag("SAG_ALT_UCGEN_IMAGE2");
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
        scoreCounter++;
        scoreText.setText("Score: " + scoreCounter);
    }

    private void goToNewLevel() {
        countDownTimer.cancel();

        setHighScore(scoreCounter);

        Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Level1Activity.this, Level2Activity.class);
        intent.putExtra("lastScore", scoreCounter);
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
            case "YAMUK_IMAGE2":
                return ShapeIndex.YAMUK_IMAGE;
            case "SOL_ALT_UCGEN_IMAGE2":
                return ShapeIndex.SOL_ALT_UCGEN_IMAGE;
            case "SAG_UST_UCGEN_IMAGE2":
                return ShapeIndex.SAG_UST_UCGEN_IMAGE;
            case "YUKARI_UCGEN_IMAGE2":
                return ShapeIndex.YUKARI_UCGEN_IMAGE;
            case "KARE_IMAGE2":
                return ShapeIndex.KARE_IMAGE;
            case "SAG_UCGEN_IMAGE2":
                return ShapeIndex.SAG_UCGEN_IMAGE;
            case "SAG_ALT_UCGEN_IMAGE2":
                return ShapeIndex.SAG_ALT_UCGEN_IMAGE;
            default:
                return 0;
        }
    }

    private void setSelectedMustBeEnabledTrue(String tag) {
        String tempTag = tag.substring(0, tag.length() - 1);

        for (ImageView image : mustBeImageViews) {
            String imageTag = image.getTag().toString();

            image.setEnabled(tempTag.equals(imageTag));
        }
    }

}