package aut.b.untitledandroidgame.GameLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import aut.b.untitledandroidgame.R;

public class GameActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gw = new GameView(this);
        setContentView(gw);

    }
}
