package com.monstrous.worley;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.GridPoint3;

import java.util.ArrayList;


// generates Worley noise in 3 dimensions.
// generateMap and updatePixmap return a slice for a given z value (see settings).

public class WorleyNoise {

    ArrayList<GridPoint3> points = new ArrayList<>();

    public void placeRandomPoints(int width, int height, WorleyNoiseSettings settings) {
        // add some points in random location
        int n = settings.numPoints;
        for(int i = 0; i < n; i++){
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            int z = (int)(Math.random() * settings.depth);
            points.add( new GridPoint3(x, y, z) );
        }
    }

    public float [][] generateMap(int width, int height, WorleyNoiseSettings settings ) {
        float[][] map = new float[width][height];

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int z = settings.z;

                float closest = Float.MAX_VALUE;
                for(GridPoint3 point : points ){
                    float dist = point.dst(x,y,z);
                    if(dist < closest)
                        closest = dist;
                }
                float noise =  Math.min(closest/ (settings.distanceScale*(float)width), 1f);
                if(settings.invert)
                    noise = 1f - noise;
                map[y][x] = noise;
            }
        }
        return map;
    }


    // use only the closest distance
    // convert directly to rgb values instead of a float matrix
    public void updatePixmap(Pixmap pixmap, int size, WorleyNoiseSettings settings ) {

        int idx = 0;
        for(int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int z = settings.z;

                float closest = Float.MAX_VALUE;
                for(GridPoint3 point : points ){
                    float dist = point.dst(x,y,z);
                    if(dist < closest)
                        closest = dist;
                }
                float noise =  Math.min(closest/ (settings.distanceScale*(float)size), 1f);
                if(settings.invert)
                    noise = 1f - noise;
                byte val = (byte)(noise *255f);
                pixmap.getPixels().put(idx++, val); // r
                pixmap.getPixels().put(idx++, val); // g
                pixmap.getPixels().put(idx++, val); // b
                pixmap.getPixels().put(idx++, (byte)255); // alpha
            }
        }
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
