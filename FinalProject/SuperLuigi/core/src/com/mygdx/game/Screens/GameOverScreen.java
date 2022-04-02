package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SuperLuigi;
import com.mygdx.game.Utils.LanguagesValues;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    private Json jsonFiles;
    private LanguagesValues languagesValues;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(SuperLuigi.V_WIDTH, SuperLuigi.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((SuperLuigi) game).batch);

        jsonFiles = new Json();
        languagesValues = jsonFiles.fromJson(LanguagesValues.class,Gdx.files.internal(SuperLuigi.language));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label(languagesValues.getValue(LanguagesValues.Keys.DEFEAT), font);
        Label playAgainLabel = new Label(languagesValues.getValue(LanguagesValues.Keys.PLAYAGAIN), font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            if(PlayScreen.getCurrentLevel().equals("level1_1.tmx")) {
                game.setScreen(new PlayScreen((SuperLuigi) game, "level1_1.tmx"));
                dispose();
            }else if(PlayScreen.getCurrentLevel().equals("level2_1.tmx")) {
                game.setScreen(new PlayScreen((SuperLuigi) game, "level2_1.tmx"));
                dispose();
            }
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
        stage.dispose();
    }
}
