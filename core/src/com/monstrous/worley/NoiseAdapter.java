package com.monstrous.worley;


// interface for consumer of noise
// used for callback in noise settings window
public interface NoiseAdapter {

    void regenerateNoise();

    void updateNoise();
}
