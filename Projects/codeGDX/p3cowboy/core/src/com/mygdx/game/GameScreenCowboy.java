package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreenCowboy implements Screen, InputProcessor {
	AssetsLoader assetsLoader;
	MyGdxGame mainClass;
	SpriteBatch batch;
	float elapseTime;
	Scrollable scenarioBack,midScenario,frontScenario;


	public GameScreenCowboy(MyGdxGame mainClass){
		this.mainClass = mainClass;
		assetsLoader = new AssetsLoader();
		batch = new SpriteBatch();
		scenarioBack = new Scrollable(assetsLoader.backTexture , 0, 0,
				Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),-5f);
		midScenario = new Scrollable(assetsLoader.midTexture , 0, 0,
				Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10,-20f);
		frontScenario = new Scrollable(assetsLoader.frontTexture , 0, 0,
				Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/8,-75f);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		elapseTime += delta;

		scenarioBack.update(delta);
		midScenario.update(delta);
		frontScenario.update(delta);

		batch.begin();

		scenarioBack.draw(batch);
		midScenario.draw(batch);
		frontScenario.draw(batch);

		batch.draw(assetsLoader.cowboyAnimation.getKeyFrame(elapseTime,true),
				Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/10);

		batch.end();

	}

	@Override
	public void dispose() {
		assetsLoader.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
