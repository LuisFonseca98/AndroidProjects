package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.mygdx.game.MyGdxGame.log;

public class GameScreenFaces implements Screen, InputProcessor {
	MyGdxGame mainClass;
	SpriteBatch batch;
	AssetsLoader assetsLoader;
	float elapsedTime;
	boolean state = false,canUseAcel = true,stateTri = false;
	int triangleX,triangleY;

	public GameScreenFaces(MyGdxGame mainClass) {
		this.mainClass = mainClass;
		this.assetsLoader = mainClass.assetsLoader;
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(this);

	}
	private void processAccelerometer(){
		float y = Gdx.input.getAccelerometerY();
		float x = Gdx.input.getAccelerometerX();

		if(stateTri){
			triangleX = 0;
			triangleY = 0;
		}

		if(Math.abs(x) > 1){
			triangleX += x;
		}

		if(Math.abs(y) > 1){
			triangleY += y;
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetsLoader.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK){
			log("back pressed");
			mainClass.setInicialScreen();
			return true;
		}

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
		state = !state;
		stateTri = !stateTri;
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

	@Override
	public void show() { }

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(canUseAcel)
			processAccelerometer();
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.begin();
		if(state){
			batch.draw(assetsLoader.faceBoxAnimation.getKeyFrame(0),
					150,150,
					300,300);
		}else{
			batch.draw(assetsLoader.faceBoxAnimation.getKeyFrame(elapsedTime , true),
					150,150,
					300,300);
		}
		batch.draw(assetsLoader.faceCircleAnimation.getKeyFrame(elapsedTime , true),
				900,300,
				300,300);
		batch.draw(assetsLoader.faceHexAnimation.getKeyFrame(elapsedTime , true),
				1100,600,
				300,300);

		if(stateTri) {
			stateTri = false;
		}
		batch.draw(assetsLoader.faceTriAnimation.getKeyFrame(elapsedTime, true),
				triangleX, triangleY,
				300, 300);

		batch.end();

	}

	@Override
	public void resize(int width, int height) { }

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
