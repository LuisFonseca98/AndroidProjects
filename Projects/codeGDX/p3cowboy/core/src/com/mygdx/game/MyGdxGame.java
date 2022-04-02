package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MyGdxGame extends Game {
	AssetsLoader assetsLoader;

	@Override
	public void create() {
		assetsLoader = new AssetsLoader();
		setInicialScreen();

	}

	@Override
	public void dispose() {
		assetsLoader.dispose();

	}

	static void log(String message) { Gdx.app.log("MyGdxGame", message); }

	void setInicialScreen(){setScreen(new GameScreenCowboy(this));}


}
