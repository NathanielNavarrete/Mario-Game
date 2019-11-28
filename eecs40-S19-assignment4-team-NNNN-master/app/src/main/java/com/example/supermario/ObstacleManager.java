package com.example.supermario;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class ObstacleManager {

    private ArrayList<Obstacle> obstacles;

    private Goomba enemy; //= new Goomba();
    private Piranha piranha; //= new Piranha(new Rect(600,400,700,500), Color.rgb(255,255,255));

    private Coin coin = null;
    private Star star = null;
    private Mushroom mush = null;

    public boolean nextLevel = false;
    private int[][] map;
    private int score = 0;



    public  ObstacleManager(int[][] inputMap){
        obstacles = new ArrayList<>();
        map = inputMap;

        populateObstacles();
    }

    public void update(Mario player){

        for(Obstacle clean : obstacles){
            if(clean.alive == false){
                obstacles.remove(clean);
            }
        }

        for(Obstacle nexlevel : obstacles){
            if(nexlevel.nextlevel){
                this.nextLevel = true;
            }
        }


        for(Obstacle ob : obstacles) {
            if(enemy != null)
            enemy.walkGumba(ob.getRectangle());
            if(star != null)
                star.ObjectUnderneath(ob.getRectangle());
            if(mush != null)
                mush.ObjectUnderneath(ob.getRectangle());
        }
        if(enemy != null)
            enemy.update(player);
        if(enemy != null)
            piranha.update(player);

        if (star != null)
            star.update(player);

        if (coin != null)
            coin.update(player);

        if (mush != null)
            mush.update(player);
        //star.isFalling = true;

        if(player.getRectangle().right > Constants.SCREEN_WIDTH/2 && player.getMovement) {
            for (Obstacle ob : obstacles) {
                ob.walking(-15);
            }
            if(piranha != null)
            piranha.Shift();
            if(star != null)
                star.Shift();
            if(coin != null)
                coin.Shift();
            if(mush != null)
                mush.Shift();
            if(enemy != null)
                enemy.Shift();
        }
    }

    public void draw(Canvas canvas){
        for (Obstacle ob: obstacles)
        {
            if(ob.alive)
                ob.draw(canvas);
        }
        if(enemy != null)
        enemy.draw(canvas);
        if(piranha != null)
        piranha.draw(canvas);

        if(mush != null)
            mush.draw(canvas);

        if(star != null)
            star.draw(canvas);

        if(coin != null)
            coin.draw(canvas);
    }

    public void populateObstacles(){
        for (int i = 0; i < 11; i++)
        {
            for (int j = 0; j < 36; j++)
            {
                //height, color,
                if(map[i][j] == 1)
                    obstacles.add(new Obstacle(i, j, 1));
                if(map[i][j] == 2)
                    obstacles.add(new Obstacle(i,j, 2));
                if(map[i][j] == 3)
                    obstacles.add(new Obstacle(i,j,3));
                if(map[i][j] == 4)
                    obstacles.add(new Obstacle(i,j, 4));
                if(map[i][j] == 5)
                    enemy = new Goomba(i,j);
                if(map[i][j] == 6)
                    piranha = new Piranha(i,j);
                if(map[i][j] == 7)
                    star = new Star(i,j);
                if(map[i][j] == 8)
                    coin = new Coin(i,j);
                if(map[i][j] == 9)
                    mush = new Mushroom(i,j);
            }
        }
    }

    public boolean playerCollide(Rect rectangle, Mario player){
        for (Obstacle ob : obstacles){
            if(ob.playerCollide(rectangle, player)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerFront(Rect rectangle){
        for (Obstacle ob : obstacles){
            if(ob.playerBlockFront(rectangle))
                return true;
        }
        return false;
    }

    public boolean playerBehind(Rect rectangle){
        for (Obstacle ob : obstacles){
            if(ob.playerBlockBehind(rectangle))
                return true;
        }
        return false;
    }
}
