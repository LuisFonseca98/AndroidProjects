package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SuperLuigi;
import com.mygdx.game.Utils.LanguagesValues;

public class MainMenu implements Screen, InputProcessor {

    private Stage stage;
    private Texture background;
    private SpriteBatch spriteBatch;
    private Skin skin;
    private Label titleMainGame;
    private TextButton playLevel1btn,playLevel2btn,btnOptions;


    private int row_height = Gdx.graphics.getWidth() / 12;
    private int col_width = Gdx.graphics.getWidth() / 12;

    private Json jsonFiles;
    private LanguagesValues languagesValues;


    public MainMenu(final Game game){
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        background = new Texture(Gdx.files.internal("bg.png"));

        jsonFiles = new Json();
        languagesValues = jsonFiles.fromJson(LanguagesValues.class,Gdx.files.internal(SuperLuigi.language));

        final Game jogo = game;

        titleMainGame = new Label(languagesValues.getValue(LanguagesValues.Keys.WLCM),skin);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            titleMainGame.setFontScale(4,4);
        titleMainGame.setSize(Gdx.graphics.getWidth(),row_height*2);
        titleMainGame.setPosition(0,Gdx.graphics.getHeight()-row_height*2);
        titleMainGame.setAlignment(Align.center);
        stage.addActor(titleMainGame);

        //For the btn to start the first level
        playLevel1btn = new TextButton(languagesValues.getValue(LanguagesValues.Keys.LVL1),skin,"small");
        playLevel1btn.setSize(col_width*4,row_height);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            playLevel1btn.getLabel().setFontScale(3,3);
        playLevel1btn.setPosition(col_width,Gdx.graphics.getHeight()-row_height*3);
        playLevel1btn.addListener(new InputListener(){

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                (jogo).setScreen(new PlayScreen((SuperLuigi) jogo,"level1_1.tmx"));
                return true;
            }
        });
        stage.addActor(playLevel1btn);

        //For the btn to start the second level
        playLevel2btn = new TextButton(languagesValues.getValue(LanguagesValues.Keys.LVL2),skin,"small");
        playLevel2btn.setSize(col_width*4,row_height);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            playLevel2btn.getLabel().setFontScale(3,3);
        playLevel2btn.setPosition(col_width*7,Gdx.graphics.getHeight()-row_height*3);
        playLevel2btn.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                (jogo).setScreen(new PlayScreen((SuperLuigi) jogo,"level2_1.tmx"));
                return true;
            }
        });
        stage.addActor(playLevel2btn);

        //For the btn to options menu
        btnOptions = new TextButton(languagesValues.getValue(LanguagesValues.Keys.OPTIONS),skin,"small");
        btnOptions.setSize(col_width*4,row_height);
        if(Gdx.app.getType() == Application.ApplicationType.Android)
            btnOptions.getLabel().setFontScale(3,3);
        btnOptions.setPosition(col_width,Gdx.graphics.getHeight()-row_height*6);
        btnOptions.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                (jogo).setScreen(new OptionsMenu(jogo));
                return true;
            }
        });
        stage.addActor(btnOptions);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(30/255f,144/255f,255/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.end();
        stage.act();
        stage.draw();
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
