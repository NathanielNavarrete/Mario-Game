package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Character implements GameObject {

    public Rect rectangle;
    public int color;
    public boolean MoveRight = false;
    public boolean MoveLeft = false;
    public boolean MoveUp = false;
    public boolean MoveDown = false;
    public boolean MoveRightAllowed;
    public boolean MoveLeftAllowed;

    public Character(){
        this.rectangle = new Rect(100,100,200,200);
        this.color = Color.rgb(255,0,0);
    }

    public Character(Rect rectangle, int color){
        this.color = color;
        this.rectangle = rectangle;
    }

    public boolean getMoveRight(){return MoveRight;};

    public void setMoveRight(boolean expression){
        MoveRight = expression;
    }

    public void setMoveLeft(boolean expression){
        MoveLeft = expression;
    }

    public void setMoveUp(boolean expression){
        MoveUp = expression;
    }

    public void setMoveDown(boolean expression){
        MoveDown = expression;
    }

    public Rect getRectangle(){return rectangle;}


    public void setRectangle(Rect rectangle){
        this.rectangle = rectangle;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void MakeTaller(int size){
        this.rectangle.top -= size;
    }

    public void MakeShorter(int size){
        this.rectangle.top += size;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
    }

    @Override
    public void update() {

        if(MoveRight && this.rectangle.right < Constants.SCREEN_WIDTH/2 + 20 && MoveRightAllowed)
            MoveRight();

        if(MoveLeft && MoveLeftAllowed )
            MoveLeft();

        if(MoveDown)
            Gravity();

        if(MoveUp)
            Jump();
    }

    public void Gravity(){
        this.rectangle.top += 20;
        this.rectangle.bottom += 20;
    }

    public void Jump(){
        this.rectangle.top -= 30;
        this.rectangle.bottom -= 30;
    }

    public void MoveRight(){
        this.rectangle.right += 20;
        this.rectangle.left += 20;
    }

    public void MoveLeft(){
        this.rectangle.right -= 20;
        this.rectangle.left -= 20;
    }


}
