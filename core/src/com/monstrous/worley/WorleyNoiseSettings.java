package com.monstrous.worley;

public class WorleyNoiseSettings {


    float cellSize;
    int depth;
    float distanceScale;
    boolean invert;
    int z;              // z value

    public WorleyNoiseSettings() {

        cellSize = 50;
        depth = 400;
        distanceScale = .5f;    // as a fraction of width
        invert = true;
        z = 0;
    }
}
