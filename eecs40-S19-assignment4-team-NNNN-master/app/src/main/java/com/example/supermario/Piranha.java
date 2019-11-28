package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Piranha {

    public boolean isAlive = true;
    public boolean isUp = true;
    public long timer;

    private Rect rectangle;
    private int color;

    private Animation idle;
    private AnimationManager animManager;


    public Piranha(int locY, int locX){

        this.rectangle = new Rect();
        this.rectangle.set(locX*100,locY*Constants.SCREEN_HEIGHT/12 - 45, locX*100 + 100, locY*Constants.SCREEN_HEIGHT/12 + 55);

        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.plantfinal);

        idle = new Animation(new Bitmap[]{idleImg}, 2);

        timer = System.currentTimeMillis();

        animManager = new AnimationManager(new Animation[]{idle});

    }

    public void timerOnPlant(){
        if(isUp && System.currentTimeMillis() - timer >= 3000) {
            isUp = false;
            rectangle.top -= 100;
            timer = System.currentTimeMillis();
        }
        else if(!isUp && System.currentTimeMillis() - timer >= 5000 ){
            isUp = true;
            rectangle.top += 100;
            timer = System.currentTimeMillis();
        }
    }

    public void marioInteraction(Mario player){
        if(isAlive){
        int y = (this.rectangle.bottom - this.rectangle.top)/2 + this.rectangle.top;
        int leftX = this.rectangle.left - 15;
        int rightX = this.rectangle.right + 15;
        int middleX = 50 + this.rectangle.left;
        if(isUp && !player.invincible && (player.rectangle.contains(leftX,y) || player.rectangle.contains(rightX,y) || player.rectangle.contains(middleX,this.rectangle.top - 10))){
            player.injured();
            isAlive = false;
        }
        else if((player.rectangle.contains(leftX,y) || player.rectangle.contains(rightX,y) || player.rectangle.contains(middleX,this.rectangle.top - 10)) && isUp)
            isAlive = false;
        }
    }

    public void draw(Canvas canvas){
        if(isAlive && isUp)
            animManager.draw(canvas,rectangle);
    }

    public void Shift(){
        this.rectangle.left -= 15;
        this.rectangle.right -= 15;

    }
    public void update(Mario player){
        if(isAlive){
            timerOnPlant();
            marioInteraction(player);
            animManager.playAnim(0);
            animManager.update();
        }
    }
}
