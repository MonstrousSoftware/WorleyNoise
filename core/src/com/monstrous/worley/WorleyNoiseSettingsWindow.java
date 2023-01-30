package com.monstrous.worley;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class WorleyNoiseSettingsWindow extends Window {

    private final Skin skin;
    private final WorleyNoiseSettings noiseSettings;
    private final NoiseAdapter client;


    public WorleyNoiseSettingsWindow(WorleyNoiseSettings noiseSettings, NoiseAdapter client, String title, Skin skin) {
        super(title, skin);
        this.noiseSettings = noiseSettings;
        this.skin = skin;
        this.client = client;
        setColor(.8f,.8f,.8f,.8f);      // make window semi transparent

        addActors();
    }



    private void addActors() {
        Table table = new Table();

        final Slider sliderCellSize = new Slider(1, 100f, 2f, false, skin);
        sliderCellSize.setValue(noiseSettings.cellSize);
        final Label labelCellSizeValue = new Label(String.valueOf(noiseSettings.cellSize), skin);
        sliderCellSize.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noiseSettings.cellSize = sliderCellSize.getValue();
                labelCellSizeValue.setText(String.valueOf(noiseSettings.cellSize));
                client.regenerateNoise();
            }
        });

        table.add(new Label("CellSize", skin));
        table.add(sliderCellSize);
        table.add(labelCellSizeValue);
        table.row();

        final CheckBox checkboxInvert = new CheckBox("invert", skin);
        checkboxInvert.setChecked(noiseSettings.invert);
        checkboxInvert.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noiseSettings.invert = checkboxInvert.isChecked();
                client.updateNoise();
            }
        });

        table.add(checkboxInvert);
        table.row();

        final Slider sliderDistanceScale = new Slider(0, 1f, 0.02f, false, skin);
        sliderDistanceScale.setValue(noiseSettings.distanceScale);
        final Label labelDistanceScaleValue = new Label(String.valueOf(noiseSettings.distanceScale), skin);
        sliderDistanceScale.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                noiseSettings.distanceScale = sliderDistanceScale.getValue();
                labelDistanceScaleValue.setText(String.format("%.2f", noiseSettings.distanceScale));
                client.updateNoise();
            }
        });

        table.add(new Label("Distance Scale", skin));
        table.add(sliderDistanceScale);
        table.add(labelDistanceScaleValue);
        table.row();

        table.pack();
        table.top();

        add(table);
        pack();
    }

}
