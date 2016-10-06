package com.miami.c11305010.timedtictactoe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class PlayGameActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private Boolean currentPlayer;

    private int barClickTime;
    private final Boolean PLAYER_ONE = true;
    private final Boolean PLAYER_TWO = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        setFirstPlayer();
        setProgressBar();
        myProgresser.run();
    }

//-----------------------------------------------------------------
    //sets
    private void setFirstPlayer() {
        currentPlayer = PLAYER_ONE;
    }

//------------------------------------------------------------------
    //sets progress bar max value from main activity
    //initializes how smooth the bar increments
    private void setProgressBar() {
        int pMax;

        progressBar = (ProgressBar)findViewById(R.id.progress);
        progressBar.setProgress(0);

        //receives gameTime value from onOptionsItemSelected switch statement in Main Activity
        pMax = getIntent().getIntExtra(getResources().getString(R.string.gameTimeKey), 0);
        if (pMax != 0){                 // should be 1, 2, 5, or 10
            progressBar.setMax(pMax * 1000);    //set max to pMax * 1000 milliseconds
        }

        barClickTime = getResources().getInteger(R.integer.bar_click_time);
    }

//--------------------------------------------------------------------
    // Runs/Increments Progress Bar
    // When full, switches players and resets bar progress
    private final Runnable myProgresser = new Runnable() {
        private Handler myHandler = new Handler();

        public void run() {
            progressBar.setProgress(progressBar.getProgress()+ barClickTime);
            if (progressBar.getProgress() >= getResources().getInteger(R.integer.bar_time)) {
                progressBar.setProgress(0);
                //switch players
                if (currentPlayer == PLAYER_ONE)
                    currentPlayer = PLAYER_TWO;
                else currentPlayer = PLAYER_ONE;
                
                //Intent returnIntent = new Intent();
                //setResult(RESULT_OK, returnIntent);
                //progressBar.setProgress(0);
                //finish();
            }
            if (!myHandler.postDelayed(myProgresser,barClickTime)) {
                Log.e("ERROR","Cannot postDelayed");
            }
        }

    };

    //CODE FOR THE TICTACTOE PART

    //--------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        Log.i("IN backpressed", "back button pressed");
        Intent gobackIntent = new Intent();
        setResult(RESULT_CANCELED, gobackIntent);
        finish();
    }
}
