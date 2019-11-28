package com.example.supermario;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class ScreenManager {

    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public ScreenManager(){
        ACTIVE_SCENE = 0;
        scenes.add(new GamePlayScene());
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    public void changeScene(int activeScene){
        this.ACTIVE_SCENE = activeScene;
    }
    public void update()
    {
        scenes.get(ACTIVE_SCENE).update();
    }
    public void draw(Canvas canvas)
    {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
