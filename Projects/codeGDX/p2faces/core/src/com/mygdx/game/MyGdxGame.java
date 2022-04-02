package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

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

	void setScreenFaces() { setScreen((Screen) new GameScreenFaces(this));}

	void setScreenBox2D() { setScreen(new GameScreenBox2D(this));}

	void setInicialScreen(){setScreen(new InitialScreen(this));}


}
