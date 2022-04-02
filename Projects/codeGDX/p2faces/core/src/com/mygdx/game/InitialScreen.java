package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class InitialScreen implements Screen, InputProcessor {
    private final float FIGURESIZE = 300f;
    MyGdxGame mainClass;
    AssetsLoader assetsLoader;
    SpriteBatch batch;
    private float faceBoxX;
    private float faceBoxY;
    private float faceHexX;
    private float faceHexY;

    public InitialScreen(MyGdxGame mainClass) {
        batch = new SpriteBatch();
        this.mainClass = mainClass;
        this.assetsLoader = mainClass.assetsLoader;

        faceBoxX =  Gdx.graphics.getWidth() / 3f - FIGURESIZE / 2;
        faceHexX = 2 * Gdx.graphics.getWidth() / 3f - FIGURESIZE / 2;
        faceBoxY = Gdx.graphics.getHeight() / 2f - FIGURESIZE / 2;
        faceHexY = faceBoxY;
        Gdx.input.setInputProcessor(this);
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
    public boolean touchDown ( int screenX , int screenY , int pointer , int button ){
        MyGdxGame.log(" touchDown ...");
        Rectangle bounds = new Rectangle(faceBoxX, faceBoxY, FIGURESIZE, FIGURESIZE);
        if (bounds.contains(screenX, screenY)) {
            MyGdxGame.log(" touchedDown on FaceBox image ...");
            mainClass.setScreenFaces();
        } else {
            bounds = new Rectangle(faceHexX, faceHexY, FIGURESIZE, FIGURESIZE);
            if (bounds.contains(screenX, screenY)) {
                MyGdxGame.log(" touchedDown on FaceHex image ...");
                mainClass.setScreenBox2D();
            }
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
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.5f, 0, 1); // set colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(assetsLoader.faceBoxAnimation.getKeyFrame(0),
                faceBoxX,faceBoxY,
                300,300);
        batch.draw(assetsLoader.faceHexAnimation.getKeyFrame(0),
                faceHexX,faceHexY,
                300,300);
        batch.end();
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
        assetsLoader.dispose();
        batch.dispose();
    }

}
