package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Star extends Item {

    private Animation idle;
    private AnimationManager animationManager;

    public Star(int locY, int locX){
        this.rect = new Rect();
        this.rect.set(locX*100,locY*Constants.SCREEN_HEIGHT/12 - 45, locX*100 + 100, locY*Constants.SCREEN_HEIGHT/12 + 55);
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.starfinal);

        idle = new Animation(new Bitmap[]{idleImg},2);

        animationManager = new AnimationManager(new Animation[]{idle});

    }

    @Override
    public void draw(Canvas canvas) {
        if(isAlive)
        animationManager.draw(canvas,rect);
    }

    @Override
    public void update(Mario player) {

        if(isAlive){


            int y = (rect.bottom - rect.top)/2 + rect.top;
            int leftx = rect.left - 10;
            int rightx = rect.right + 10;
            if (isFalling) {
                Gravity();
            }
            if (!isFalling) {
                isFalling = true;
            }
            if (isAlive && isMovingLeft)
                MoveLeft();
            if (isAlive && isMovingRight)
                MoveRight();
            if(player.getRectangle().contains(leftx,y) ){
                isAlive = false;
                player.InvicibleMethod();
            }
            if(rect.top > Constants.SCREEN_HEIGHT)
                isAlive = false;
            animationManager.playAnim(0);
            animationManager.update();
        }
    }

}
