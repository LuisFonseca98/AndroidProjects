package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperLuigi;
import com.mygdx.game.Utils.LanguagesValues;

public class OptionsMenu implements Screen, InputProcessor {

    private Viewport viewport;
    private Stage stage;
    private static Music music;
    public static boolean playMusic;

    private TextButton btnSettings;

    private Texture background;

    private SpriteBatch spriteBatch;

    private int row_height = Gdx.graphics.getWidth() / 12;
    private int col_width = Gdx.graphics.getWidth() / 12;

    private Skin skin;

    private Json jsonFiles;
    private LanguagesValues languagesValues;


    public OptionsMenu(Game game){
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        background = new Texture(Gdx.files.internal("bg.png"));
        music = SuperLuigi.manager.get("audio/music/mario_music.ogg", Music.class);

        jsonFiles = new Json();
        languagesValues = jsonFiles.fromJson(LanguagesValues.class,Gdx.files.internal(SuperLuigi.language));

        final Game  jogo = game;

        //For the btn to turn on and off the music
        TextButton turnOnOffMusic = new TextButton(languagesValues.getValue(LanguagesValues.Keys.MUSIC),skin,"small");
        turnOnOffMusic.setSize(col_width*4,row_height);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            turnOnOffMusic.getLabel().setFontScale(3,3);
        turnOnOffMusic.setPosition(col_width,Gdx.graphics.getHeight()-row_height*3);
        turnOnOffMusic.addListener(new InputListener(){

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(!playMusic) {
                    playMusic = true;
                    music.play();
                    music.setLooping(true);
                }else {
                    playMusic = false;
                    music.stop();
                    music.setLooping(false);
                }
                return true;
            }
        });
        stage.addActor(turnOnOffMusic);

        //For the btn to start the second level
        TextButton backbtn = new TextButton(languagesValues.getValue(LanguagesValues.Keys.BACK),skin,"small");
        backbtn.setSize(col_width*4,row_height);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            backbtn.getLabel().setFontScale(3,3);
        backbtn.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
        backbtn.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                (jogo).setScreen(new MainMenu((SuperLuigi) jogo));
                return true;
            }
        });
        stage.addActor(backbtn);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act();
        stage.draw();
    }

    public static boolean getMusicValue(){
        return playMusic = true;
    }

    public static void setMusicValue(boolean playMusic){
        playMusic = playMusic;
    }


    public static Music getMusic(){
        return music;
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {}
    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }

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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
