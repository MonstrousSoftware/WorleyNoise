package com.monstrous.worley;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.GridPoint3;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;


// generates Worley noise in 3 dimensions.
// generateMap and updatePixmap return a slice for a given z value (see settings).

public class WorleyNoise {

    Vector3[][][] pointMap; // point vector for each cell

    // call this before calling updatePixmap
    public void placeRandomPoints(int width, int height, WorleyNoiseSettings settings) {

        pointMap = new Vector3[1+(int) (width/settings.cellSize)][1+(int) (height/settings.cellSize)][1+(int) (settings.depth/settings.cellSize)];

        // imagine the 3d space is divided into cubic cells.
        // add one point in random location in each cell

        for(int ix = 0; ix < width/settings.cellSize; ix++) {
            for (int iy = 0; iy < height/settings.cellSize; iy++) {
                for (int iz = 0; iz < settings.depth/settings.cellSize; iz++) {
                    float fx = (float) (ix+Math.random()) * settings.cellSize;
                    float fy = (float) (iy+Math.random()) * settings.cellSize;
                    float fz = (float) (iz+Math.random()) * settings.cellSize;
                    pointMap[ix][iy][iz] = new Vector3(fx, fy, fz);
                }
            }
        }
    }


    // assumes a Pixmap is already created at the right size.
    // use only the closest distance
    // convert directly to rgb values in the Pixmap
    public void updatePixmap(Pixmap pixmap, int size, WorleyNoiseSettings settings ) {

        int idx = 0;
        for(int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int z = settings.z;

                int ix = (int) (x/settings.cellSize);
                int iy = (int) (y/settings.cellSize);
                int iz = (int) (z/settings.cellSize);


                float closest = Float.MAX_VALUE;
                for(int dx = -1; dx <= 1; dx++){
                    for(int dy = -1; dy <= 1; dy++){
                        for(int dz = -1; dz <= 1; dz++){
                            if(ix+dx < 0 || ix+dx >= size/settings.cellSize)
                                continue;
                            if(iy+dy < 0 || iy+dy >= size/settings.cellSize)
                                continue;
                            if(iz+dz < 0 || iz+dz >= settings.depth/settings.cellSize)
                                continue;

                            Vector3 point = pointMap[ix+dx][iy+dy][iz+dz];
                            float dist = point.dst(x,y,z);
                            if(dist < closest)
                                closest = dist;
                        }
                    }
                }

                float noise =  Math.min(closest/ (settings.distanceScale*(float)size), 1f);
                if(settings.invert)
                    noise = 1f - noise;
                // noise is a float in the range [0-1]

                byte val = (byte)(noise *255f);
                pixmap.getPixels().put(idx++, val); // r
                pixmap.getPixels().put(idx++, val); // g
                pixmap.getPixels().put(idx++, val); // b
                pixmap.getPixels().put(idx++, (byte)255); // alpha
            }
        }
    }

}
