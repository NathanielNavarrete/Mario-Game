package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Coin {

    public Rect rectangle;
    public int color;
    public boolean isAlive = true;
    private Animation idle;
    private AnimationManager animManager;

    public Coin(int locY, int locX){
        this.rectangle = new Rect();
        this.rectangle.set(locX*100,locY*Constants.SCREEN_HEIGHT/12 - 45, locX*100 + 100, locY*Constants.SCREEN_HEIGHT/12 + 55);

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.coinfinal);

        idle = new Animation(new Bitmap[]{idleImg},2);

        animManager = new AnimationManager(new Animation[]{idle});

    }

    public Rect getRectangle(){return rectangle;}

    public void draw(Canvas canvas){
        if(isAlive)
            animManager.draw(canvas,rectangle);
    }

    public void Shift(){
        this.rectangle.left -= 15;
        this.rectangle.right -= 15;
    }
    public void update(Mario player){
        if(isAlive) {
            int y = (rectangle.bottom - rectangle.top)/2 + rectangle.top;
            if (player.rectangle.contains(this.rectangle.left - 10,y )|| player.rectangle.contains(this.rectangle.right+10,y)){
                player.coin();
                isAlive = false;
            }
            animManager.playAnim(0);
            animManager.update();
        }
    }
}
