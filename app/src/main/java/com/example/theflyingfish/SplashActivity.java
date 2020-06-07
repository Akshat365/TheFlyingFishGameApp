package com.example.theflyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4500);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        };
        thread.start();*/
    }

    public  void playGame(View view){
        ToggleButton soundTB = findViewById(R.id.soundTB);
        boolean soundON = false;
        if(soundTB.isChecked()){
            soundON = true;
        }
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        mainIntent.putExtra("sound", soundON);

        startActivity(mainIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
