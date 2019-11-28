package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GamePlayScene implements Scene {

    private boolean gameOver = false;
    private Mario test;
    private map currentMap = new map(Constants.CURRENT_LEVEL);
    private ObstacleManager obstacleManager;

    public GamePlayScene() {
        this.obstacleManager = new ObstacleManager(currentMap.getMap());
        this.test = new Mario(new Rect(100,100,200,200),Color.rgb(255,0,0));
        //this.enemy = new Goomba();
    }

    @Override
    public void update() {
        if (!gameOver && !obstacleManager.nextLevel) {
            obstacleManager.update(test);
            test.MoveRightAllowed = !obstacleManager.playerFront(test.rectangle);
            test.MoveLeftAllowed = !obstacleManager.playerBehind(test.rectangle);
            test.update();
            if(!obstacleManager.playerCollide(test.getRectangle(), test) && !test.MoveUp)
                test.Gravity();
            if(test.getRectangle().top > Constants.SCREEN_HEIGHT || test.getLives() < 1)
               gameOver = true;
        }
        else if(gameOver && !obstacleManager.nextLevel){
            gameOver = false;
            reset();
        }
        else if(!gameOver && obstacleManager.nextLevel && Constants.CURRENT_LEVEL < 3)
        {
            Constants.CURRENT_LEVEL++;
            currentMap = new map(Constants.CURRENT_LEVEL);
            //test.reset();
            if(test.getSmallMario())
            test.getRectangle().set(100,700,200,800);
            if(test.getBigMario())
                test.getRectangle().set(100,700,200,900);
            this.obstacleManager = new ObstacleManager(currentMap.getMap());
        }
        else
            System.exit(0);
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        background(canvas);
        obstacleManager.draw(canvas);
        test.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Score: " + test.score + "  Lives: " +  test.getLives(), 50, 50 + paint.descent() - paint.ascent(), paint);
        //enemy.draw(canvas);

    }

    public void background(Canvas canvas)
    {
        Paint paint = new Paint();
        Rect rect = new Rect();
        rect.set(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        paint.setColor(Color.WHITE);
        BitmapFactory bf = new BitmapFactory();
        Bitmap background = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.marioback);

        float whRatio = (float)background.getWidth()/background.getHeight();
        if(rect.width() > rect.height()){
            rect.left = rect.right - (int)(rect.height() * whRatio);
        }
        else
            rect.top = rect.bottom  - (int)(rect.width() *(1/whRatio));

        canvas.drawBitmap(background, null, rect, paint);
    }

    @Override
    public void terminate() {
        ScreenManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if ((int)event.getX() > (2*Constants.SCREEN_WIDTH)/3 && (int)event.getY() > Constants.SCREEN_HEIGHT/2) {
                 if(!obstacleManager.playerFront(test.rectangle))
                    test.setMoveRight(true);
                }

                if((int)event.getX() > Constants.SCREEN_WIDTH/3 && (int)event.getX() < (2*Constants.SCREEN_WIDTH)/3 && (int)event.getY() > Constants.SCREEN_HEIGHT/2) {
                 if(obstacleManager.playerCollide(test.rectangle,test))
                    test.setMoveUp(true);
                }

                //if((int)event.getX() > Constants.SCREEN_WIDTH/4 && (int)event.getX() < Constants.SCREEN_WIDTH/2 && (int)event.getY() > Constants.SCREEN_HEIGHT/2)
                  //  test.setMoveDown(true);

                if ((int)event.getX() < Constants.SCREEN_WIDTH/3 && (int)event.getY() > Constants.SCREEN_HEIGHT/2)
                    if(!obstacleManager.playerBehind(test.rectangle))
                    test.setMoveLeft(true);

                break;
            case MotionEvent.ACTION_UP:
                test.setMoveRight(false);
                test.setMoveDown(false);
                test.setMoveLeft(false);
                test.setMoveUp(false);
                break;
        }
    }

    public void reset() {
        test.reset();
        test.getRectangle().set(100,700,200,800);
        this.obstacleManager = new ObstacleManager(currentMap.getMap());

    }
}