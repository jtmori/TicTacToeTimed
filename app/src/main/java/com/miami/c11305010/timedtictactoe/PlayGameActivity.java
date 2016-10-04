package com.miami.c11305010.timedtictactoe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class PlayGameActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private int barClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        progressBar = (ProgressBar)findViewById(R.id.progress);
        progressBar.setProgress(0);
        barClickTime = getResources().getInteger(R.integer.bar_click_time);
        myProgresser.run();
    }
    //--------------------------------------------------------------------
    private final Runnable myProgresser = new Runnable() {
        private Handler myHandler = new Handler();

        public void run() {
            Log.i("IN runnable", "Inside OK");
            progressBar.setProgress(progressBar.getProgress()+ barClickTime);
            if (progressBar.getProgress() >= getResources().getInteger(R.integer.bar_time)) {
                Log.i("IN runnable", "Progress bar full");
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                progressBar.setProgress(0);
                finish();
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
