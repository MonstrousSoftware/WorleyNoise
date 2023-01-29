package com.monstrous.worley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WorleyNoiseScreen  extends ScreenAdapter implements NoiseAdapter {


    private static int noiseResolution = 600;

    private SpriteBatch batch;
    private Texture texture;
    private Skin skin;
    private Stage stage;
    private int screenWidth, screenHeight;
    private WorleyNoiseSettings settings;


    @Override
    public void show() {
        settings = new WorleyNoiseSettings();
        settings.numPoints = 20;
        settings.distanceScale = 0.3f;

        // GUI elements via Stage class
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        WorleyNoiseSettingsWindow noiseSettingsWindow = new WorleyNoiseSettingsWindow(settings, this, "Noise", skin);
        stage.addActor(noiseSettingsWindow);

        batch = new SpriteBatch();

        //refresh();
        Gdx.input.setInputProcessor(stage);

        texture = makeNoiseTexture(noiseResolution, settings);
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        stage.getViewport().update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void render(float deltaTime) {
        // clear screen
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        // show texture at right of screen
        int w = texture.getWidth();
        int h = texture.getHeight();
        batch.draw(texture, (screenWidth - w), (screenHeight - h) / 2, w, h);

        batch.end();

        stage.act(deltaTime);
        stage.draw();
    }


    private Texture makeNoiseTexture(int size, WorleyNoiseSettings settings) {
        WorleyNoise worley = new WorleyNoise();

        // generate a noise map
        float[][] map = worley.generateMap(size, size, settings);

        // copy to a texture (for debug)
        Pixmap pixmap = generatePixmap(map, size);
        return new Texture(pixmap);
    }

    // from tests/g3d/voxel/PerlinNoiseGenerator.java
    public Pixmap generatePixmap (float [][] map, int size) {

        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        int idx = 0;
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                byte val = (byte) (map[x][y] * 255f);

                pixmap.getPixels().put(idx++, val);
                pixmap.getPixels().put(idx++, (byte)0);
                pixmap.getPixels().put(idx++, val);
                pixmap.getPixels().put(idx++, (byte) 255);
            }
        }
        return pixmap;
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        super.dispose();
    }

    @Override
    public void regenerateNoise() {
        texture = makeNoiseTexture(noiseResolution, settings);
    }
}
