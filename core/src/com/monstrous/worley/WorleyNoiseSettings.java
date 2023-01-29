package com.monstrous.worley;

public class WorleyNoiseSettings {
    int numPoints;
    float distanceScale;
    int distanceN;  // 0 for closest point, 1 for second closest, etc.
    boolean invert;

    public WorleyNoiseSettings() {
        numPoints = 20;
        distanceScale = .5f;    // as a fraction of width
        distanceN = 0;
        invert = true;
    }
}
