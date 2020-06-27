package aut.b.untitledandroidgame.GameLogic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import aut.b.untitledandroidgame.R;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Player {
    private int x;
    private int y;
    private int speed;
    private int actualPlatformRight;
    private int actualPlatformLeft;
    private Rect platform;
    int actualplatformindex = 0;
    private int direction; // 1 - right, 0 - left
    int top;
    private Bitmap pic;
    private boolean throwed;
    public int getDirection()
    {
        return direction;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void addX(int x)
    {
        this.x += x;
    }
    public void addY(int y)
    {
        this.y += y;
    }
    public void setSpeed(int speed)
    {
        this.speed = speed;
    }
    public int getSpeed()
    {
        return speed;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public Player()
    {
        throwed = false;
        x = 0;
        y = 0;
        direction = 1;
    }
    public void setPlatform(Rect platform, int index)
    {

        actualplatformindex = index;
        y = platform.top+8;
        Log.d("Platform index from set: ",actualplatformindex+ "");
        this.platform = platform;
        actualPlatformRight = platform.right;
        actualPlatformLeft = platform.left;
        this.top = platform.top;
    }
    public void setPlatformWithoutTop(int right, int left)
    {
        actualPlatformRight = right;
        actualPlatformLeft = left;
    }
    public void movePlayer()
    {
            y = platform.top;
            if (x < actualPlatformRight && direction == 1) {
                x += speed;
            }
            if (x > actualPlatformRight - 20) {
                x = actualPlatformRight - 20;
                direction = 0;
            }
            if (x > actualPlatformLeft && direction == 0) {
                x -= speed;
            }
            if (x < actualPlatformLeft + 20 ) {
                x = actualPlatformLeft+20;
                direction = 1;
            }
    }
    public void draw(Canvas c)
    {

        //c.drawBitmap(pic,null,new Paint(Color.BLUE));
        c.drawCircle(x,y,20,new Paint(Color.YELLOW));
    }
}
