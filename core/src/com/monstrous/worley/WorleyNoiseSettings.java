package com.monstrous.worley;

public class WorleyNoiseSettings {
    int numPoints;
    int depth;
    float distanceScale;
    boolean invert;
    int z;              // z value

    public WorleyNoiseSettings() {
        numPoints = 20;
        depth = 400;
        distanceScale = .5f;    // as a fraction of width
        invert = true;
        z = 0;
    }
}
