package com.miami.c11305010.timedtictactoe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayGameActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private Boolean currentPlayer;

    private int barClickTime;
    private final Boolean PLAYER_ONE = true;
    private final Boolean PLAYER_TWO = false;

    private int [] tictacPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        tictacPlaces = new int[9];
        for (int i = 0; i < tictacPlaces.length; i++){
            tictacPlaces[i] = 0;
        }

        setFirstPlayer();
        setProgressBar();
        myProgresser.run();
    }

//-----------------------------------------------------------------
    //sets first player using goesFirstKey from Main Activity
    private void setFirstPlayer() {
        int first = getIntent().getIntExtra(getResources().getString(R.string.goesFirstKey), 0);
        if (first == 1) {
            currentPlayer = PLAYER_ONE;
        } else {    //otherwise p2
            currentPlayer = PLAYER_TWO;
        }

        setImageVisibility();
    }

//-----------------------------------------------------------------
    // Changes which image is visible above progress bar based off current player
    private void setImageVisibility(){
        ImageView player1 = (ImageView)findViewById(R.id.player1_playicon);
        ImageView player2 = (ImageView)findViewById(R.id.player2_playicon);

        //set the other player's icon invisible
        if (currentPlayer == PLAYER_ONE) {    //if player one is playing
            player2.setVisibility(View.INVISIBLE);
            player1.setVisibility(View.VISIBLE);
        } else {
            player1.setVisibility(View.INVISIBLE);
            player2.setVisibility(View.VISIBLE);
        }
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
                currentPlayer = !currentPlayer;
                setImageVisibility();
            }
            if (!myHandler.postDelayed(myProgresser,barClickTime)) {
                Log.e("ERROR","Cannot postDelayed");
            }
        }
    };

//------------------------------------------------------------------------
    public void onClickHandler(View view){
        int id = view.getId();
        int place;
        int rscId;

        //set drawable
        if (currentPlayer) {
            rscId = R.drawable.p1;
        } else {
            rscId = R.drawable.p2;
        }

        //switch statement for image buttons
        switch (id){
            case R.id.place00:
                place = 0;
                checkWinner(place, id, rscId);
                break;
            case R.id.place01:
                place = 1;
                checkWinner(place, id, rscId);
                break;
            case R.id.place02:
                place = 2;
                checkWinner(place, id, rscId);
                break;
            case R.id.place10:
                place = 3;
                checkWinner(place, id, rscId);
                break;
            case R.id.place11:
                place = 4;
                checkWinner(place, id, rscId);
                break;
            case R.id.place12:
                place = 5;
                checkWinner(place, id, rscId);
                break;
            case R.id.place20:
                place = 6;
                checkWinner(place, id, rscId);
                break;
            case R.id.place21:
                place = 7;
                checkWinner(place, id, rscId);
                break;
            case R.id.place22:
                place = 8;
                checkWinner(place, id, rscId);
                break;
            default:
                break;
        }

    }

//------------------------------------------------------------------------
    private void checkWinner(int place, int id, int rscId) {
        //if already clicked by a player in tictac array, exit
        if (tictacPlaces[place] != 0) {
            return;
        }

        int player;
        //set image to new image
        ImageView imageview = (ImageView)findViewById(id);
        imageview.setImageResource(rscId);

        //update tictac array
        if(currentPlayer){
            player = 1;
        } else {
            player = 2;
        }
        tictacPlaces[place] = player;

        //check for tie
        Boolean tie = true;
        for (int i = 0; i < tictacPlaces.length; i++){
            if (tictacPlaces[i] == 0) {
                tie = false;
            }
        }

        //if tie, return winner
        if (tie) {
            returnWinner(0);
        }

        //check Diagonal win
        if (place == 0 || place == 2 || place == 6 || place == 8 || place == 4){
            if(checkDiagonal(place, player)){
                returnWinner(player);
            }
        }
        //check horizontal win
        if(checkHorizontal(place, player)){
            returnWinner(player);
        }
        //check vertical win
        if(checkVertical(place, player)){
            returnWinner(player);
        }

        //set next player
        progressBar.setProgress(0);
        currentPlayer = !currentPlayer;
        setImageVisibility();
    }

//-----------------------------------------------------------------------
    //Each function below returns true if given player won given the place most recently clicked

    private Boolean checkDiagonal(int place, int player){
        if (place == 0 || place == 8 || place == 4){
            if (tictacPlaces[0] == player && tictacPlaces[4] == player && tictacPlaces[8] == player){
                return true;
            }
        } else if (place == 2 || place == 6 || place == 4) {
            if (tictacPlaces[2] == player && tictacPlaces[4] == player && tictacPlaces[6] == player) {
                return true;
            }
        }
        return false;
    }
    //-------------------------------------------------------------
    private Boolean checkHorizontal(int place, int player){
        if (place == 0 || place == 1 || place == 2){
            if (tictacPlaces[0] == player && tictacPlaces[1] == player && tictacPlaces[2] == player){
                return true;
            }
        } else if (place == 3 || place == 4 || place == 5){
            if (tictacPlaces[3] == player && tictacPlaces[4] == player && tictacPlaces[5] == player){
                return true;
            }
        }else {
            if (tictacPlaces[6] == player && tictacPlaces[7] == player && tictacPlaces[8] == player) {
                return true;
            }
        }
        return false;
    }
    //--------------------------------------------------------------
    private Boolean checkVertical(int place, int player){
        if (place == 0 || place == 3 || place == 6){
            if (tictacPlaces[0] == player && tictacPlaces[3] == player && tictacPlaces[6] == player){
                return true;
            }
        } else if (place == 1 || place == 4 || place == 7){
            if (tictacPlaces[1] == player && tictacPlaces[4] == player && tictacPlaces[7] == player){
                return true;
            }
        }else {
            if (tictacPlaces[2] == player && tictacPlaces[5] == player && tictacPlaces[8] == player) {
                return true;
            }
        }
        return false;
    }

//------------------------------------------------------------------------
    //Return to main activity the winner
    private void returnWinner(int winner){
        if(winner != 1 && winner != 2){
            winner = 0;     //set winner to only 3 values
        }

        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        returnIntent.putExtra(getResources().getString(R.string.winnerKey), winner);
        progressBar.setProgress(0);
        finish();
    }

//--------------------------------------------------------------------------
    //If back button presssed, should do nothing
    @Override
    public void onBackPressed() {
        Intent gobackIntent = new Intent();
        setResult(RESULT_CANCELED, gobackIntent);
        finish();
    }
}
