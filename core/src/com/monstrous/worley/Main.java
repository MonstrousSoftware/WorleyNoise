package com.monstrous.worley;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Game;

public class Main extends Game {

	@Override
	public void create() {

		setScreen(new WorleyNoiseScreen());
	}

	@Override
	public void dispose() {
	}

}
