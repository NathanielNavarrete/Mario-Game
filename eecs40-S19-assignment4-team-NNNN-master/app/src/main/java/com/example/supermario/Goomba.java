package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Goomba {

    public Rect rectangle;

    private boolean isFalling = true;
    private boolean isAlive = true;
    private boolean isMovingLeft = true;
    private boolean isMovingRight = false;

    private Animation walkLeft;
    private Animation walkRight;
    private AnimationManager animManger;

    public Goomba(int locY, int locX){
        this.rectangle = new Rect();
        this.rectangle.set(locX*100,locY*Constants.SCREEN_HEIGHT/12 - 45, locX*100 + 100, locY*Constants.SCREEN_HEIGHT/12 + 55);

        BitmapFactory bf = new BitmapFactory();
        Bitmap walkImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.tinygoombaleftfoot);
        Bitmap walkBigImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.othergoombabigstep);

        walkLeft = new Animation(new Bitmap[]{walkImg,walkBigImg}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walkImg = Bitmap.createBitmap(walkImg, 0, 0, walkImg.getWidth(), walkImg.getHeight(), m, false);
        walkBigImg = Bitmap.createBitmap(walkBigImg, 0, 0, walkBigImg.getWidth(), walkBigImg.getHeight(), m, false);

        walkRight = new Animation(new Bitmap[]{walkImg,walkBigImg}, 0.5f);

        animManger = new AnimationManager(new Animation[]{walkLeft, walkRight});
    }


    private void setAlive(boolean statement){
        this.isAlive = statement;
    }

    private void GoombaDead(Mario player){
        int y = this.rectangle.top - 5;
        int middleX = (this.rectangle.right - this.rectangle.left)/2 + this.rectangle.left;
        if(player.getRectangle().contains(this.rectangle.left + 5, y) || player.getRectangle().contains(this.rectangle.right - 5, y) || player.getRectangle().contains(middleX, y)) {
            setAlive(false);
        }

    }

    private void HurtMario(Mario player){
        int y = (this.rectangle.bottom - this.rectangle.top)/2 + this.rectangle.top;
        int leftX = this.rectangle.left - 20;
        int rightX = this.rectangle.right + 20;
        if((player.getRectangle().contains(leftX,y) || player.getRectangle().contains(rightX,y) ) && !player.invincible) {
            player.injured();
            setAlive(false);
        }
        else if((player.getRectangle().contains(leftX,y) || player.getRectangle().contains(rightX,y))){
            setAlive(false);
        }
    }

    public void walkGumba(Rect rectangle){
        int y = this.rectangle.bottom + 15;
        int firstX = this.rectangle.left + 5;
        int secondX = (this.rectangle.right - this.rectangle.left)/2 + this.rectangle.left;
        int thirdX = this.rectangle.right - 5;
        if(rectangle.contains(firstX,y) || rectangle.contains(secondX,y) || rectangle.contains(thirdX,y)){
            isFalling = false;
        }
        if(rectangle.contains(this.rectangle.left - 10, (this.rectangle.bottom - this.rectangle.top)/2 + this.rectangle.top) && isMovingLeft ){
            isMovingLeft = false;
            isMovingRight = true;
        }
        else if(rectangle.contains(this.rectangle.right + 10, (this.rectangle.bottom - this.rectangle.top)/2 + this.rectangle.top) && isMovingRight){
            isMovingLeft = true;
            isMovingRight = false;
        }
    }

    public void MoveRight(){
        this.rectangle.right += 15;
        this.rectangle.left += 15;
    }

    public void MoveLeft(){
        this.rectangle.right -= 15;
        this.rectangle.left -= 15;
    }

    public void Gravity(){
        this.rectangle.top += 30;
        this.rectangle.bottom += 30;
    }

    public void Shift(){
        this.rectangle.left -= 15;
        this.rectangle.right -= 15;
    }

    public void update(Mario player) {
       GoombaDead(player);
       if(isAlive) {
           int state = 0;
           HurtMario(player);
           if (isAlive){
               if (isMovingLeft) {
                   MoveLeft();
                   state = 0;

               }
               if (isMovingRight) {
               MoveRight();
               state = 1;
               }
               if (isFalling) {
                   Gravity();
               }
               if (!isFalling) {
                   this.isFalling = true;
               }
           animManger.playAnim(state);
           animManger.update();
           }
       }
    }


    public void draw(Canvas canvas) {
        if(isAlive) {
           /* Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawRect(rectangle,paint);*/
            animManger.draw(canvas,rectangle);
        }
    }

}
