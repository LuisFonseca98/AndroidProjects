package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Items.Mushroom;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    private boolean isActive;
    public Coin(PlayScreen screen, MapObject object) {
        super(screen,object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(SuperLuigi.COIN_BIT);
        isActive = true;
    }

    @Override
    public void onHeadHit(Luigi luigi) {
        if(getCell().getTile().getId() == BLANK_COIN)
            SuperLuigi.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if(object.getProperties().containsKey("mushroom")){
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / SuperLuigi.PPM), Mushroom.class));
                SuperLuigi.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }else
                SuperLuigi.manager.get("audio/sounds/coin.wav", Sound.class).play();

        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        if(isActive){
            Hud.addScore(100);
            isActive = false;
        }
    }

}
