package com.avigail.android.quizexpert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aviagil on 06/10/2018.
 */


public class ResultActivity extends AppCompatActivity {
    //start Game again:

    private static final int SCORE_TO_WIN = 20 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final Button button = findViewById(R.id.resetButton);
        final TextView score = findViewById(R.id.scoreTextView);
        final ImageView imageLostOrWin = findViewById(R.id.imageViewWinLost);
        final TextView textOfResult = findViewById(R.id.textOfResult);

        String textOnScreen = "";

        int scoreNum = getIntent().getExtras().getInt("score");
        final String url = getIntent().getExtras().getString("url");

        String printScoreOnScreen = "Your Score is: " + scoreNum ;

        score.setText(printScoreOnScreen);

        if( scoreNum > SCORE_TO_WIN ){
            textOnScreen = "You Are The Best!!!\n" ;
            imageLostOrWin.setImageResource(R.drawable.win_face);
        }
        else{
            textOnScreen = "Try Again!\n Maybe Next Time";
        }

        textOfResult.setText(textOnScreen);


        // if the user want's to Reset the Game :
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to get the Game Screen:
                Intent i = new Intent(ResultActivity.this,GameActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            }
        });
    }
}
