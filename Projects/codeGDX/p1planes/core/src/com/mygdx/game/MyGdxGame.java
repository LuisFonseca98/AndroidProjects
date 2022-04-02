package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	Pixmap pixmap;
	Sprite sprite;
	TextureAtlas textureAtlas;
	Animation<TextureAtlas.AtlasRegion> animation;
	int planesTextureWidth,planesTextureHeight;
	float elapsedTime = 0;
	TextureRegion[] rotateUpFrames;
	Animation<TextureRegion> rotateUpAnimation;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("plain.png");

		font = new BitmapFont();
		font.setColor(Color.RED);
		font.getData().setScale(4.0f, 4.0f);

		// create 256 wide, 128 height using 8 bits for Red, Green, Blue and Alpha
		pixmap = new Pixmap(256, 128, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.RED);
		pixmap.fill();

		// Draw two lines forming an X
		pixmap.setColor(Color.BLACK);
		pixmap.drawLine(0, 0, pixmap.getWidth() - 1, pixmap.getHeight() - 1);
		pixmap.drawLine(0, pixmap.getHeight() - 1, pixmap.getWidth() - 1, 0);

		// Draw a circle in the middle
		pixmap.setColor(Color.YELLOW);
		pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2,
				pixmap.getHeight() / 2 - 1);

		// create a sprite, that is a texture
		sprite = new Sprite(new Texture(pixmap));
		pixmap.dispose();

		textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		animation = new Animation<>(0.1f, textureAtlas.getRegions());
		TextureRegion textureRegion = textureAtlas.getRegions().get(0);
		planesTextureWidth = textureRegion.getRegionWidth();
		planesTextureHeight = textureRegion.getRegionHeight();

		rotateUpFrames = new TextureRegion[10];
		for (int i = 0; i < rotateUpFrames.length; i++) {
			rotateUpFrames[i] = textureAtlas.findRegion(String.format("00%02d", i + 1));
		}
		rotateUpAnimation = new Animation<>(0.1f, rotateUpFrames);


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(img, Gdx.graphics.getWidth() / 2f - img.getWidth() / 2f,
				Gdx.graphics.getHeight() / 2f - img.getHeight() / 2f);
		font.draw(batch, "Hello libGDX - Tutorial 4", 50, Gdx.graphics.getHeight() - 50);

		sprite.setPosition(0, Gdx.graphics.getHeight() / 2f - sprite.getHeight()/2);
		sprite.draw(batch);
		sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth(),
				Gdx.graphics.getHeight() / 2f - sprite.getHeight() / 2);
		sprite.draw(batch);

		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedTime , true),
				Gdx.graphics.getWidth() / 4f - planesTextureWidth / 2f,
				Gdx.graphics.getHeight() / 2f - planesTextureHeight / 2f);

		batch.draw(animation.getKeyFrame(elapsedTime , true),
				Gdx.graphics.getWidth() / 1.3f - planesTextureWidth / 2f,
				Gdx.graphics.getHeight() / 2f - planesTextureHeight / 2f);


		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		textureAtlas.dispose();
	}
}
