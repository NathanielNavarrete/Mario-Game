package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject{

    private Rect rectangle;
    //private int color;
    //private int locationX;
    //private int locationY;

    private int type;
    private boolean breakable = false;
    public boolean alive = true;
    public boolean nextlevel = false;

    public Obstacle(int locY, int locX, int type){
        this.type = type;

        if(this.type == 4 || this.type == 2)
            breakable = true;

        rectangle = new Rect();
        this.rectangle.set(locX*100,locY*Constants.SCREEN_HEIGHT/12 - 45, locX*100 + 100, locY*Constants.SCREEN_HEIGHT/12 + 55);
    }

    public void walking(int x){
        rectangle.left += x;
        rectangle.right += x;
    }

    @Override
    public void draw(Canvas canvas){
        sprites(canvas);
    }
    @Override
    public void update(){

    }
    public Rect getRectangle(){
        return rectangle;
    }

    public boolean playerCollide(Rect rectangle, Mario Player)
    {
        if((rectangle.contains(this.rectangle.left, this.rectangle.bottom + 10)||
                rectangle.contains(this.rectangle.right, this.rectangle.bottom + 10)) &&
                    breakable == true) {
            this.alive = false;
            Player.score += 10;
        }

        int y = 50 + this.rectangle.top;
        if(rectangle.contains(this.rectangle.left - 10 ,y + 200) && this.type == 3)
            this.nextlevel = true;


        return rectangle.contains(this.rectangle.left, this.rectangle.top - 10) || rectangle.contains(this.rectangle.right,this.rectangle.top - 10);
    }

    public boolean playerBlockFront(Rect rectangle){
        int y = 50 + this.rectangle.top;
        return rectangle.contains(this.rectangle.left - 10 ,y);
    }

    public boolean playerBlockBehind(Rect rectangle){
        int y = 50 + this.rectangle.top;
        return rectangle.contains(this.rectangle.right + 10 ,y);
    }

    public void sprites(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        BitmapFactory bf = new BitmapFactory();
        Bitmap floor = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.groundtile);
        Bitmap item_box = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.itembox);
        Bitmap endgate = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.endgate);
        Bitmap breakabletile = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.breakabletile);


        if (type == 1) {
            resize(floor);
            canvas.drawBitmap(floor, null, rectangle, paint);
        }
        if (type == 2) {
            resize(item_box);
            canvas.drawBitmap(item_box, null, rectangle, paint);
        }
        if (type == 3) {
            resize(endgate);
            canvas.drawBitmap(endgate,null, rectangle, paint);
        }
        if (type == 4) {
            resize(breakabletile);
            canvas.drawBitmap(breakabletile, null, rectangle, paint);
        }
    }

    public void resize(Bitmap sprite)
    {
        float whRatio = (float)sprite.getWidth()/sprite.getHeight();
        if(rectangle.width() > rectangle.height()){
            rectangle.left = rectangle.right - (int)(rectangle.height() * whRatio);
        }
        else
            rectangle.top = rectangle.bottom  - (int)(rectangle.width() *(1/whRatio));

    }

}
