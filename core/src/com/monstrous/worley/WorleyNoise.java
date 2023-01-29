package com.monstrous.worley;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;

public class WorleyNoise {

    ArrayList<GridPoint2> points = new ArrayList<>();
    ArrayList<Float> distances = new ArrayList<>();

    public float [][] generateMap(int width, int height, WorleyNoiseSettings settings ) {
        float[][] map = new float[width][height];

        // clear
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++) {
                map[y][x] = 0;
            }
        }

        // add some points in random location
        int n = settings.numPoints;
        for(int i = 0; i < n; i++){
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            points.add( new GridPoint2(x, y) );
            map[y][x] = 1f;
        }

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                distances.clear();
                for(GridPoint2 point : points ){
                    float dist = point.dst(x,y);
                    distances.add(dist);
                }
                Collections.sort(distances);
                float noise =  Math.min(distances.get(settings.distanceN)/ (settings.distanceScale*(float)width), 1f);
                if(settings.invert)
                    noise = 1f - noise;
                map[y][x] = noise;

            }
        }

        return map;
    }

    public float [][] generateWhiteNoiseMap(int width, int height) {
        float[][] map = new float[width][height];

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++) {
                map[y][x] = (float) Math.random();
            }
        }
        return map;
    }
}
