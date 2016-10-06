package com.miami.c11305010.timedtictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected final int PLAY_GAME_RETURN_OKAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu/add any items to the action bar.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //on main activity menu option selection:
        switch (id) {
            //change game time to 1, 2, 5, or 10 seconds
            case R.id.one_sec:
                break;
            case R.id.two_sec:
                break;
            case R.id.five_sec:
                break;
            case R.id.ten_sec:
                break;
            //reset tournament
            case R.id.menuReset:
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickHandler(View view){
        int id = view.getId();
        switch (id){
            case R.id.start_button:
                Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
                startActivityForResult(intent, PLAY_GAME_RETURN_OKAY);
                break;
            default:
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        int increment = 1;
        RatingBar p1_ratingBar = (RatingBar)findViewById(R.id.player1_rating);
        RatingBar p2_ratingBar = (RatingBar)findViewById(R.id.player2_rating);

        switch (requestCode){
            case PLAY_GAME_RETURN_OKAY:
                if (resultCode == RESULT_OK){
                    int winner = data.getExtras().getInt(getResources().getString(R.string.winnerKey));
                    if (winner == 1){  //data = 1 -> player 1 won
                        p1_ratingBar.setRating(p1_ratingBar.getRating()+increment);
                        final int rating = (int)p1_ratingBar.getRating();
                        if(rating == (getResources().getInteger(R.integer.max_score))){
                            Toast.makeText(MainActivity.this, "P1 WINS!", Toast.LENGTH_LONG).show();
                        }
                    } else if (winner == 2){ //data = 2 -> player 2 won
                        p2_ratingBar.setRating(p2_ratingBar.getRating()+increment);
                        final int rating = (int)p2_ratingBar.getRating();
                        if(rating == (getResources().getInteger(R.integer.max_score))){
                            Toast.makeText(MainActivity.this, "P2 WINS!", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(MainActivity.this, "NO WINNER", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
