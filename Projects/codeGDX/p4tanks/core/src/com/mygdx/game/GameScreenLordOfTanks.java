package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public class GameScreenLordOfTanks implements Screen, InputProcessor {

	private MyGdxGame mainclass;
	private AssetsLoader assetsLoader;
	SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private final float factor = 4f;
	private Rectangle tankButton,notesButton;
	private Color tankColor,notesColor;

	public GameScreenLordOfTanks(MyGdxGame mainClass) {
		this.mainclass = mainClass;
		this.assetsLoader = mainclass.assetsLoader;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		defineButtons();
		tankColor = new Color(255,0,0,255);
		notesColor = new Color(0,255,0,255);

		int logLevel = Application.LOG_DEBUG;
		Gdx.app.setLogLevel(logLevel);
		Gdx.input.setInputProcessor(this);

	}

	private void defineButtons() {
		// 2 rectangles to used around the textures in order to built the buttons
		float tankWidth = assetsLoader.tankTexture.getWidth() * factor;
		float tankHeight = assetsLoader.tankTexture.getHeight() * factor;
		tankButton = new Rectangle(Gdx.graphics.getWidth()/4f - tankWidth / 2f,3 * Gdx.graphics.getHeight()/5f - tankHeight/2f, tankWidth, tankHeight);
		float notesWidth = assetsLoader.notesTexture.getWidth () * factor;
		float notesHeight = assetsLoader.notesTexture.getHeight() * factor;
		notesButton = new Rectangle(3 * Gdx.graphics.getWidth()/4f - notesWidth/2f, 3 * Gdx.graphics.getHeight()/5f - notesHeight/2f, notesWidth, notesHeight);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		// draw background
		batch.draw(assetsLoader.backTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// Draw shadow and text
		assetsLoader.shadow.draw(batch, "Touch for Sound !",Gdx.graphics.getWidth()/2f - 150 + 5,200 + assetsLoader.font.getCapHeight() + 5, 400, Align.center, false);
		assetsLoader.font.draw(batch, " Touch for Sound !",Gdx.graphics.getWidth()/2f - 150,200 + assetsLoader.font.getCapHeight(),400,Align.center,false);
		batch.end();

		// draw delimiter lines
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(tankColor);
		shapeRenderer.rect(tankButton.x-25, tankButton.y-25, tankButton.width+50, tankButton.height+50);
		shapeRenderer.setColor(notesColor);
		shapeRenderer.rect(notesButton.x-25, notesButton.y-25, notesButton.width+50 , notesButton.height+50);
		shapeRenderer.end();

		batch.begin();
		// draw tank and notes image
		batch.draw(assetsLoader.tankTexture, tankButton.x, tankButton.y, tankButton.getWidth(), tankButton.getHeight());
		batch.draw(assetsLoader.notesTexture, notesButton.x, notesButton.y, notesButton.getWidth(), notesButton.getHeight());
		batch.end();

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
		// Mouse Y is inverted
		screenY = Gdx.graphics.getHeight() - screenY;
		Gdx.app.debug("TouchUp", " TouchUp , x:" + screenX + " y:" + screenY );// from previous projects
		if(notesButton.contains(screenX, screenY)) {
			Gdx.app.debug("Notes", "Touch on Notes button");
			assetsLoader.music.stop();
			assetsLoader.music.play();
		} else if(tankButton.contains(screenX, screenY)) {
			Gdx.app.debug("Tank", "Touch on Tanks button");
			assetsLoader.explosion.play();
		} else {
			assetsLoader.music.stop();
			assetsLoader.explosion.stop();
		}
		return false ;
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
	public void show() {

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
	public void dispose() {
		batch.dispose();
		assetsLoader.dispose();
	}
}