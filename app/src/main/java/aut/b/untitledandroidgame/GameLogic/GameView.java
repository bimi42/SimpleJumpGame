package aut.b.untitledandroidgame.GameLogic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.solver.widgets.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class GameView extends View {
    private Paint paint;
    private Paint platformP;
    private int platformIndex;
    Player player;
    //int left, int top, int right, int bottom
    List<Rect> platforms = new ArrayList<Rect>();
    private Timer timer;
    private TimerTask tt;
    int iteration = 0;
    int timecount = 0;
    private int timerCount;
    public boolean playerJump;
    public GameView(Context context) {
        super(context);
        platformP = new Paint(Color.BLACK);
        player = new Player();
        player.setSpeed(10);
        platforms.add(new Rect(700,1400,1000,1500));
        platforms.add(new Rect(800,1200,1000,1300));
        platforms.add(new Rect(10,1100,300,1200));
        platforms.add(new Rect(10,1300,500,1400));
        platforms.add(new Rect(700,1000,1000,1100));
        platforms.add(new Rect(10,900,300,1000));
        platforms.add(new Rect(700,800,1000,900));
        platforms.add(new Rect(10,700,300,800));
        platforms.add(new Rect(700,600,1000,700));
        platforms.add(new Rect(10,500,300,600));
        platforms.add(new Rect(700,400,1000,500));
        platforms.add(new Rect(10,300,300,400));
        //platforms.add(new Rect())
        //player.setPlatform(platforms.get(0).right,platforms.get(0).left,platforms.get(0).top);
        player.setPlatform(platforms.get(0),0);
        player.setX(platforms.get(0).right);
        playerJump = false;
        platformIndex = 0;
        iteration =0;
        timer = new Timer();
        timerCount = 0;
        tt = new TimerTask() {
            @Override
            public void run() {
                if(player.actualplatformindex == platforms.size()-1)
                {
                    Rect saveR = platforms.get(platforms.size()-1);
                    platforms.clear();
                    saveR.bottom = 1450;
                    saveR.top = 1400;
                    platforms.add(saveR);
                    platformIndex = 0;
                    player.setPlatform(platforms.get(0),0);
                    player.setX(platforms.get(0).left +60);
                    playerJump = false;
                    generateLevel();
                }
                postInvalidate();
                iteration++;
                if(playerJump)
                {
                        timerCount++;
                        jumpPlayer(timerCount);
                        if(iteration >= 5)
                        {
                         //moveRects(platforms);
                         iteration = 0;
                        }
                    Log.d("Platformindex", player.actualplatformindex+"");
                }
                //TODO dynamic values
                else if(player.getY() >  1920 || player.getX() > 1080 || player.getX() <= 0)
                {
                    Log.d("Player x", player.getX()+" ");
                    Log.d("Player y", player.getY()+" ");
                    reset();

                }
                else
                {
                    timerCount = 0;
                    player.movePlayer();
                    if(iteration >= 5)
                    {
                        //moveRects(platforms);
                        iteration = 0;
                    }

                }

            }
        };
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                playerJump = true;
            }
        });
        timer.scheduleAtFixedRate(tt,10,20);
        paint = new Paint();
        paint.setColor(Color.RED);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                playerJump = true;
                postInvalidate();
            }
        });

    }
    Random rnd = new Random();
    private void generateLevel()
    {

        for(int i = 1; i < 7; i++)
        {

            if(i%2==0)
            {
                Rect r = new Rect((rnd.nextInt(200)+500),platforms.get(i - 1).top - (rnd.nextInt(50)+170),rnd.nextInt(200)+800,platforms.get(i - 1).top - (rnd.nextInt(50)+100));
                platforms.add(r);
            }
            else
            {
                Rect r = new Rect(rnd.nextInt(200),platforms.get(i - 1).top - (rnd.nextInt(50)+170),rnd.nextInt(100)+400,platforms.get(i - 1).top - (rnd.nextInt(50)+100));
                platforms.add(r);
            }
        }
        for(int i = 0; i < platforms.size();i++)
        {
            Log.d("Platform top and bottom: "," top: " + platforms.get(i).top + " bottom: " + platforms.get(i).bottom);
        }
    }
    private void moveRects(List<Rect> platforms)
    {
        for(Rect r : platforms)
        {
            r.top+=1;
            r.bottom+=1;

        }
    }
    private void reset()
    {
        // timer.purge();
         //player.setPlatform(platforms.get(0).right,platforms.get(0).left,platforms.get(0).top);
        player.setPlatform(platforms.get(0),0);
         player.setX(platforms.get(0).left +60);
         //player.setY(400);
         platformIndex =  0;
         playerJump = false;
    }
    private void jumpPlayer(int time)
    {
        time = time/3;
        if(player.getDirection() == 1)
        {
            int xthrow = (int)(player.getSpeed()+20*time*cos(45));
            int ythrow = (int)(player.getSpeed()+20*time*sin(45)-((10/2)*time*time));
           /*Log.d("Throw X", xthrow +" ");
            Log.d("Throw Y",ythrow + " ");
            Log.d("Before X throw", player.getX()+" ");
            Log.d("Before Y throw",player.getY()+ " ");
            Log.d("Time: ", time+ " ");*/
            player.addX(xthrow);
            player.addY(-ythrow);
            /*Log.d("X", player.getX()+" ");
            Log.d("Y",player.getY()+ " ");*/
            for(int i = 0; i < platforms.size(); i++)
            {
                if(player.getX() < platforms.get(i).right && player.getX() > platforms.get(i).left && player.getY() > platforms.get(i).top - 20 && player.getY() < platforms.get(i).top + 20)
                {
                    if(platformIndex!= i) {
                        player.setPlatform(platforms.get(i),i);
                        platformIndex = i;
                        playerJump = false;
                        break;
                    }
                }
            }
            if(player.getY() >  1920 || player.getX() > 1080 || player.getX() <= 0) {
                playerJump = false;
                player.setPlatform(platforms.get(0),0);
            }
        }
        else if(player.getDirection() == 0)
        {

            int xthrow = (int)(player.getSpeed()+20*time*cos(45));
            int ythrow = (int)(player.getSpeed()+20*time*sin(45)-((10/2)*time*time));
            /*Log.d("Throw X", xthrow +" ");
            Log.d("Throw Y",ythrow + " ");
            Log.d("Before X throw", player.getX()+" ");
            Log.d("Before Y throw",player.getY()+ " ");
            Log.d("Time: ", time+ " ");*/
            player.addX(-xthrow);
            player.addY(-ythrow);
            //Log.d("X", player.getX()+" ");
            //Log.d("Y",player.getY()+ " ");
            for(int i = 0; i < platforms.size(); i++)
            {
                if(player.getX() < platforms.get(i).right && player.getX() > platforms.get(i).left && player.getY() > platforms.get(i).top - 20  && player.getY() < platforms.get(i).top + 20)
                {
                    if(platformIndex!= i) {
                        player.setPlatform(platforms.get(i),i);
                        platformIndex = i;
                        playerJump = false;
                        break;
                    }
                }
            }
            if(player.getY() >  1920 || player.getX() > 1080 || player.getX() <= 0) {
                playerJump = false;
                player.setPlatform(platforms.get(0),0);
            }
        }
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(paint.getColor());
        player.draw(canvas);
        for (Rect r: platforms) {
            canvas.drawRect(r, platformP);
        }
    }
}
