package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Projetile.Fireball;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Mushroom;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;
import com.mygdx.game.Utils.B2WorldCreator;
import com.mygdx.game.Utils.WorldContactListener;

import java.util.concurrent.LinkedBlockingDeque;

public class PlayScreen implements Screen {

    private SuperLuigi superLuigi;
    private Luigi player;
    private Hud hud;
    private ControllerScreen controller;

    private OrthographicCamera gameCam;
    private Viewport gamePort;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private TextureAtlas atlas;


    private Array<Item> items;
    private Array<Fireball> fireballs;
    private LinkedBlockingDeque<ItemDef> itemsToSpawn;

    public static SpriteBatch batch;
    private static String level;

    //private OptionsMenu optionsMenu;


    public PlayScreen(SuperLuigi superLuigi, String level) {

        batch = new SpriteBatch();
        controller = new ControllerScreen();

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.superLuigi = superLuigi;

        this.level = level;

        gameCam = new OrthographicCamera();

        gamePort = new FitViewport(SuperLuigi.V_WIDTH / SuperLuigi.PPM, SuperLuigi.V_HEIGHT / SuperLuigi.PPM, gameCam);

        hud = new Hud(superLuigi.batch);
        //optionsMenu = new OptionsMenu();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(level);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ SuperLuigi.PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Luigi(this);

        world.setContactListener(new WorldContactListener());

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingDeque<ItemDef>();

        fireballs = new Array<Fireball>();


    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems(){
        if(!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();
            if(idef.type == Mushroom.class){
                items.add(new Mushroom(this,idef.position.x, idef.position.y));
            }
        }
    }


    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt) {
        if(player.currentState != Luigi.State.DEAD) {
            if (controller.isRightPressed() && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            else if (controller.isLeftPressed() & player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0)
                player.jump();
        }

    }

    public void update(float dt) {
        handleInput(dt);
        handleSpawningItems();
        world.step(1 / 60f, 6, 2);
        hud.update(dt);
        player.update(dt);

        for(Enemy enemy: creator.getEnemies()) {
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224 / SuperLuigi.PPM) //permite acordar os goombas, quando o mario fica perto deles
                enemy.b2body.setActive(true);
        }

        if(player.currentState != Luigi.State.DEAD){
            gameCam.position.x = player.b2body.getPosition().x;
        }

        for (Item item: items)
            item.update(dt);

        for(Fireball balls: fireballs)
            balls.update(dt);

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gameCam.combined);

        /*ONDE PERMITE DESENHAR O MARIO, APLICADO A BOUNDING BOX, APLICANDO AS TEXTURAS*/
        superLuigi.batch.setProjectionMatrix(gameCam.combined);
        superLuigi.batch.begin();
        player.draw(superLuigi.batch);

        for(Enemy enemy: creator.getEnemies())
            enemy.draw(superLuigi.batch);

        for (Item item: items)
            item.draw(superLuigi.batch);


        superLuigi.batch.end();

        superLuigi.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if(pitTouched())
            player.die();

        if(gameOver()){
            superLuigi.setScreen(new GameOverScreen(superLuigi));
            dispose();
        }

        if(Victory()){
            superLuigi.setScreen(new VictoryScreen(superLuigi));
        }


        if(Gdx.app.getType() == Application.ApplicationType.Android)
            controller.draw();

    }

    public boolean gameOver(){
        if(player.currentState == Luigi.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width,height);
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

    public static String getCurrentLevel(){
        return level;
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){ return hud; }

    public boolean Victory(){
        if(player.victory()){
            return true;
        }
        return false;
    }

    public boolean pitTouched(){
        if(player.luigiTouchedPit()){
            return true;
        }
        return false;
    }

}
