package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.SuperLuigi;
import com.mygdx.game.Utils.LanguagesValues;

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private boolean timeUp; // true when the world timer reaches 0
    private float timeCount;
    public static int score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabelWorld1;
    Label levelLabelWorld2;
    Label worldLabel;
    Label marioLabel;

    private Json jsonFiles;
    private LanguagesValues languagesValues;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        jsonFiles = new Json();
        languagesValues = jsonFiles.fromJson(LanguagesValues.class, Gdx.files.internal(SuperLuigi.language));

        viewport = new FitViewport(SuperLuigi.V_WIDTH, SuperLuigi.V_HEIGHT,new OrthographicCamera());

        stage = new Stage(viewport,sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(languagesValues.getValue(LanguagesValues.Keys.TIME), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabelWorld1 = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabelWorld2 = new Label("2-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label(languagesValues.getValue(LanguagesValues.Keys.LVL), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("LUIGI", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        if(PlayScreen.getCurrentLevel().equals("level1_1.tmx")) {
            table.add(marioLabel).expandX().padTop(10);
            table.add(worldLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
            table.add(scoreLabel).expandX();
            table.add(levelLabelWorld1).expandX();
            table.add(countdownLabel).expandX();
        }else if(PlayScreen.getCurrentLevel().equals("level2_1.tmx")) {
            table.add(marioLabel).expandX().padTop(10);
            table.add(worldLabel).expandX().padTop(10);
            table.add(timeLabel).expandX().padTop(10);
            table.row();
            table.add(scoreLabel).expandX();
            table.add(levelLabelWorld2).expandX();
            table.add(countdownLabel).expandX();
        }
        stage.addActor(table);

    }



    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp() { return timeUp; }

    public static int getScore() {
        return score;
    }
}
