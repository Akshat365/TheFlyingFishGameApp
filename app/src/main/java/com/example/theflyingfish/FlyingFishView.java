package com.example.theflyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View {

    private Bitmap fish[] = new Bitmap[2];
    private int fishX = 10;
    private int fishY;
    private int fishSpeed;

    private int canvasWidth, canvasHeight;

    private int score, lifeCounterOfFish;

    private int yellowX, ywllowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();

    private Bitmap life[] = new Bitmap[2];

    private Boolean touch = false;

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        canvas.drawBitmap(backgroundImage, 0, 0, null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight()*1;
        //int maxFishY = canvasHeight;
        fishY = fishY + fishSpeed;
        if(fishY < minFishY) {
            fishY = minFishY;
        }
        if(fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        if(touch)
        {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }
        else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }

        //yellow
        yellowX = yellowX - yellowSpeed;
        if(hitBallChecker(yellowX, ywllowY)){
            score = score + 10;
            yellowX = -100;
        }
        if(yellowX < 0){
            yellowX = canvasWidth + 21;
            ywllowY = (int) (Math.floor(Math.random()* (maxFishY - minFishY)) + minFishY);
        }
        canvas.drawCircle(yellowX, ywllowY, 30, yellowPaint);

        //green
        greenX = greenX - greenSpeed;
        if(hitBallChecker(greenX, greenY)){
            score = score + 20;
            greenX = -100;
        }
        if(greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) (Math.floor(Math.random()* (maxFishY - minFishY)) + minFishY);
        }
        canvas.drawCircle(greenX, greenY, 35, greenPaint);

        //red
        redX = redX - redSpeed;
        if(hitBallChecker(redX, redY)){
            redX = -100;
            lifeCounterOfFish--;

            Context context = getContext();
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
            }
            else {
                //deprecated in API 26
                v.vibrate(500);
            }

            if(lifeCounterOfFish == 0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);
            }
        }
        if(redX < 0){
            redX = canvasWidth + 21;
            redY = (int) (Math.floor(Math.random()* (maxFishY - minFishY)) + minFishY);
        }
        canvas.drawCircle(redX, redY, 45, redPaint);

        canvas.drawText("Score : " + score, 20, 60, scorePaint);
        for(int i=0; i<3; i++)
        {
            int x = (int) (580 + life[0].getWidth()*1.5*i);
            int y = 30;
            if(i<lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }


    }

    public boolean hitBallChecker(int x, int y){
        if(fishX<x && x<(fishX + fish[0].getWidth()) && fishY<y && y<(fishY + fish[0].getWidth()))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
