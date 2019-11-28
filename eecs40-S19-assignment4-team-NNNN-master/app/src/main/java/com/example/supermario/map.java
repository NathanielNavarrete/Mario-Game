package com.example.supermario;

public class map {
    private int[][] map = new int[11][37];
    private int chosenMap;

    public map(int levelChoice){
        chosenMap = levelChoice;
        if (levelChoice == 1)
        {
            for (int i = 0; i < 36; i++)
                map[10][i] = 1;

            map[9][4] = 5;

            map[4][7] = 2;
            map[4][8] = 2;
            map[4][9] = 4;
            map[3][7] = 8;

            map[9][10] = 6;
            map[9][13] = 9;

            map[9][35] = 3;
        }
        if(levelChoice == 2)
        {
            for (int i = 0; i < 36; i++)
                map[10][i] = 1;

            map[9][8] = 5;

            map[9][18] = 6;

            map[4][7] = 2;
            map[4][8] = 2;
            map[4][9] = 4;
            map[9][13] = 7;

            map[5][20] = 4;
            map[5][21] = 4;
            map[5][22] = 4;
            //end gate
            map[9][35] = 3;
        }
        if(levelChoice == 3)
        {
            for (int i = 0; i < 36; i++)
                map[10][i] = 1;

            //goomba set up
            map[9][10] = 5;
            //map[9][3] = 1;
            //map[9][6] = 1;

            map[10][20] = 0;
            map[10][21] = 0;
            map[10][22] = 0;
            map[10][23] = 0;

            map[8][20] = 1;
            map[8][21] = 1;
            map[8][22] = 1;
            //pirahna
            map[9][10] = 6;

            //end gate
            map[9][35] = 3;
        }

    }
    public int[][] getMap(){
        return map;
    }
}
