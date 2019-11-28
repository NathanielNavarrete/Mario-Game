package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.provider.ContactsContract;

public class Mario extends Character {

    private int Lives = 3;
    private long Invincble = 0;
    public int score = 0;
    private boolean BigMario = false;
    private boolean SmallMario = true;
    public  boolean invincible = false;
    public boolean getMovement;
    public boolean RightAllowed;


    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    private Animation jump;
    private Animation BigLeft;
    private Animation BigRight;
    private Animation Immune;
    private AnimationManager animaManger;


    public Mario(Rect rectangle, int color){
        this.color = color;
        this.rectangle = rectangle;

        BitmapFactory bf = new BitmapFactory();

        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.marioidlefinal);
        Bitmap walkIdleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.mariowalkingidlefinal);
        Bitmap walkImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.mariowalkingleftfinal);
        Bitmap jumpImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.mariojumpfinal);
        Bitmap bigLeftImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.bigmariofinal);
        Bitmap cantTouchImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.immune);

        walkLeft = new Animation(new Bitmap[]{walkImg,walkIdleImg},0.5f);
        BigLeft = new Animation(new Bitmap[]{bigLeftImg},0.5f);
        Immune = new Animation(new Bitmap[]{cantTouchImg},0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        jumpImg = Bitmap.createBitmap(jumpImg, 0, 0, jumpImg.getWidth(), jumpImg.getHeight(), m, false);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), m, false);
        walkImg = Bitmap.createBitmap(walkImg, 0, 0, walkImg.getWidth(), walkImg.getHeight(), m, false);
        walkIdleImg = Bitmap.createBitmap(walkIdleImg, 0, 0, walkIdleImg.getWidth(), walkIdleImg.getHeight(), m, false);
        bigLeftImg = Bitmap.createBitmap(bigLeftImg, 0, 0, bigLeftImg.getWidth(), bigLeftImg.getHeight(), m, false);




        BigRight = new Animation(new Bitmap[]{bigLeftImg},0.5f);
        idle = new Animation (new Bitmap[]{idleImg},2);
        walkRight = new Animation(new Bitmap[]{walkImg,walkIdleImg},0.5f);
        jump = new Animation(new Bitmap[]{jumpImg},2 );

        animaManger = new AnimationManager(new Animation[]{idle, walkRight,walkLeft,jump, BigRight,BigLeft,Immune});

    }

    public boolean getBigMario(){
        return BigMario;
    }

    boolean getSmallMario(){
        return SmallMario;
    }

    public void MakeTaller(int size){
        this.rectangle.top -= size;
    }

    public void MakeShorter(int size){
        this.rectangle.top += size;
    }

    public void mushroom(){
        if(getSmallMario()){
            BigMario = true;
            SmallMario = false;
            MakeTaller(100);
            score += 1000;
        }
    }

    public void InvicibleMethod(){
        Invincble = System.currentTimeMillis();
        invincible = true;
        score += 1000;
    }

    public void reset(){
        Lives = 3;
        Invincble = 0;
        score = 0;
        if(BigMario) {
            BigMario = false;
            SmallMario = true;
            MakeShorter(100);
        }
        invincible = false;
    }

    public void injured(){
        if(getBigMario()){
            BigMario = false;
            SmallMario = true;
            MakeShorter(100);
        }else{
            lostLife();
            //MakeTaller(100);
        }
    }

    public void lostLife(){
        Lives--;
    }

    public int getLives(){
        return Lives;
    }

    public void setLives(int Lives){
        this.Lives = Lives;
    }

    public void coin(){
        score += 200;
    }

    @Override
    public void draw(Canvas canvas) {
        animaManger.draw(canvas,rectangle);
    }

    public void update(){

        if(System.currentTimeMillis() - Invincble >= 5000)
            invincible = false;
        int state = 0;
        if(MoveLeft && getSmallMario())
            state = 2;
        if(MoveRight && getSmallMario())
            state = 1;
        if((MoveUp || MoveDown) && getSmallMario())
            state = 3;
        if(BigMario)
            state = 4;
        if(BigMario & MoveLeft)
            state = 5;
        if(invincible)
            state = 6;

        this.getMovement = this.getMoveRight();

        super.update();

        animaManger.playAnim(state);
        animaManger.update();
    }
}
