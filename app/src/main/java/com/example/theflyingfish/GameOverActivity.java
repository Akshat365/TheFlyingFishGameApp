package com.example.theflyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameOverActivity extends AppCompatActivity {

    private TextView scoreDisplay;
    private Button startGameAgain;
    private String score;
    private String highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scoreDisplay = findViewById(R.id.displayScore);
        score = getIntent().getExtras().get("score").toString();



        startGameAgain = findViewById(R.id.playAgainButton);
        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToggleButton soundTB = findViewById(R.id.soundTBGO);
                boolean soundON = false;
                if(soundTB.isChecked()){
                    soundON = true;
                }
                final boolean soundGO = soundON;
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);
                mainIntent.putExtra("sound", soundGO);
                startActivity(mainIntent);
            }
        });

        scoreDisplay.setText("SCORE : " + score);

        SharedPreferences sharedPreferences = getSharedPreferences("HIGH_SCORE_SP", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getString("highScore", "0");

        if(Integer.parseInt(score) > Integer.parseInt(highScore)){
            highScore = score;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("highScore", highScore);
            editor.apply();
        }

        TextView highScoreDisplay = findViewById(R.id.highScore);
        highScoreDisplay.setText("HIGH SCORE : " + highScore);

    }
}
