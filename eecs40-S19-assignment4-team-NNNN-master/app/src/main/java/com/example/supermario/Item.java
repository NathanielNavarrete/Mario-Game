package com.example.supermario;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Item implements GameObject {

    public Rect rect;
    public boolean isAlive = true;
    public boolean isFalling = true;
    public boolean isMovingRight = false;
    public boolean isMovingLeft  = true;



    public Item(){

    }

    public void MoveRight(){
        this.rect.left += 5;
        this.rect.right += 5;
    }

    public void MoveLeft(){
        this.rect.left -= 10;
        this.rect.right -= 10;
    }

    public void Gravity(){
        this.rect.top += 30;
        this.rect.bottom += 30;
    }

    public void Shift(){
        this.rect.left -= 15;
        this.rect.right -= 15;
    }

    public void ObjectUnderneath(Rect rectangle){
        int y = rect.bottom + 15;
        int firstX = rect.left + 5;
        int secondX = (rect.right - rect.left)/2 + rect.left;
        int thirdX = rect.right - 5;
        if(rectangle.contains(firstX,y) || rectangle.contains(secondX,y) || rectangle.contains(thirdX,y)){
            isFalling = false;
        }
        if(rectangle.contains(this.rect.left - 10, (this.rect.bottom - this.rect.top)/2 + this.rect.top) && isMovingLeft){
            isMovingLeft = false;
            isMovingRight = true;
        }
        else if(rectangle.contains(this.rect.right + 10, (this.rect.bottom - this.rect.top)/2 + this.rect.top) && isMovingRight){
            isMovingLeft = true;
            isMovingRight = false;
        }
    }


    public void update(Mario player) {
        if(isAlive) {
            if (isFalling) {
                Gravity();
            }
            if (!isFalling) {
                this.isFalling = true;
            }
            if (isAlive && isMovingLeft)
                MoveLeft();
            if (isAlive && isMovingRight)
                MoveRight();
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update() {

    }
}
