package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Firebase.DataHolderClass;
import com.mygdx.game.Firebase.FirebaseInterface;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.MainMenu;


public class SuperLuigi extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100; //pixels per meter

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short LUIGI_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32; //pipes, etc
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short LUIGI_HEAD_BIT = 512;
	public static final short FIREBALL_BIT = 1024;
	public static final short PIT_BIT = 2048;
	public static final short VICTORY_BIT = 4096;

	public static String language;

	public static AssetManager manager;

	public SpriteBatch batch; //cada um dos screens tem acesso ao batch, aplicando o mesmo batch em diferentes screens

	private FirebaseInterface _FBIC;
	private DataHolderClass dataHolder;

	public SuperLuigi(FirebaseInterface FBIC){
		this._FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/powerup.wav", Sound.class);
		manager.load("audio/sounds/powerdown.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);

		language=java.util.Locale.getDefault().toString()+".json";

		manager.finishLoading();

		_FBIC.SomeFunction();
		_FBIC.FirstFireBaseTest();
		_FBIC.SetOnValueChangedListener(dataHolder);
		_FBIC.SetValueInDb("Level", Hud.getScore()); //0

		//setScreen(new PlayScreen(this, "level1_1.tmx"));
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render(); //permite detetar o ecra que esta ligado no momento
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}
