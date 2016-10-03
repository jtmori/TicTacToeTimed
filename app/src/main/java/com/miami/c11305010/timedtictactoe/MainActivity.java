package com.miami.c11305010.timedtictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    protected final int PLAY_GAME_RETURN_OKAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
