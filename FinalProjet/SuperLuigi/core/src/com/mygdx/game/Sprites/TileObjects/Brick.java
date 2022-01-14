package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(SuperLuigi.BRICK_BIT);

    }

    @Override
    public void onHeadHit(Luigi luigi) {
        if(luigi.isBig()){
            setCategoryFilter(SuperLuigi.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            SuperLuigi.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        SuperLuigi.manager.get("audio/sounds/bump.wav", Sound.class).play();

    }
}
