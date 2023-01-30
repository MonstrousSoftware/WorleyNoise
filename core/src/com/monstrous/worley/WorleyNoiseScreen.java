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


    private SpriteBatch batch;
    private Texture texture;
    private Stage stage;
    private int screenWidth, screenHeight;
    private final WorleyNoise worley = new WorleyNoise();
    private WorleyNoiseSettings settings;


    @Override
    public void show() {
        settings = new WorleyNoiseSettings();
        settings.cellSize = 50;
        settings.depth = 1000;
        settings.distanceScale = 1.0f;

        // GUI elements via Stage class
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        WorleyNoiseSettingsWindow noiseSettingsWindow = new WorleyNoiseSettingsWindow(settings, this, "Noise", skin);
        stage.addActor(noiseSettingsWindow);

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);

        stage.getViewport().update(width, height, true);
        texture = makeNoiseTexture(screenHeight, settings);
        super.resize(width, height);
    }

    @Override
    public void render(float deltaTime) {

        texture = updateNoiseTexture(screenHeight, settings);

        // clear screen
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();

        // show texture at right of screen
        int w = texture.getWidth();
        int h = texture.getHeight();
        batch.draw(texture, (screenWidth - w), 0, w, h);


        batch.end();

        stage.act(deltaTime);
        stage.draw();
    }


    private Texture makeNoiseTexture(int size, WorleyNoiseSettings settings) {

        worley.placeRandomPoints(size, size, settings);
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        worley.updatePixmap(pixmap, size, settings);
        // copy to a texture
        return new Texture(pixmap);
    }

    private Texture updateNoiseTexture(int size, WorleyNoiseSettings settings) {

        settings.z = (settings.z+1) % settings.depth;


        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pm = texture.getTextureData().consumePixmap();

        worley.updatePixmap(pm, size, settings);    // update for new Z and other settings that may have changed

        texture.draw(pm, 0,0);
        return texture;
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        super.dispose();
    }

    @Override
    public void regenerateNoise() {
        texture = makeNoiseTexture(screenHeight, settings);
    }

    @Override
    public void updateNoise() {
        texture = updateNoiseTexture(screenHeight, settings);
    }
}
