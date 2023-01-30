package com.monstrous.worley;

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
