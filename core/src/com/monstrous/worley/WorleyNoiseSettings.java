package com.monstrous.worley;

public class WorleyNoiseSettings {


    float cellSize;                 // size of cells containing one point (in pixels)
    int depth;                      // depth in Z direction
    float distanceScale;            // fall-off from white to black as fraction of texture size
    boolean invert;                 // black on white or white on black
    int z;                          // X value to take an XY slice from, [0 - depth]

    public WorleyNoiseSettings() {

        cellSize = 50;
        depth = 400;
        distanceScale = .5f;    // as a fraction of width
        invert = true;
        z = 0;
    }
}
