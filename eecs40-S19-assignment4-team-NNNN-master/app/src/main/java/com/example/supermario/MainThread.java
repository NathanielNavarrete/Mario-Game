package com.example.supermario;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gameScreen;
    private boolean isRunning;
    public static Canvas canvas;

    public void setRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gameScreen){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameScreen = gameScreen;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(isRunning) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameScreen.update();
                    this.gameScreen.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e) {e.printStackTrace();}
                }
            }
            timeMillis = (System.nanoTime() - startTime)/ 1000000;
            waitTime = targetTime - timeMillis;
            try{
                if(waitTime > 0)
                    this.sleep(waitTime);
            }catch(Exception e){e.printStackTrace();}
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}
