package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected PlayScreen screen;
    protected MapObject object;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bDef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set((bounds.getX() + bounds.getWidth() / 2) / SuperLuigi.PPM, (bounds.getY() + bounds.getHeight() / 2) / SuperLuigi.PPM);
        //bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight());

        body = world.createBody(bDef);

        shape.setAsBox((bounds.getWidth() / 2)/ SuperLuigi.PPM, (bounds.getHeight() / 2) / SuperLuigi.PPM);
        //shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);

        fdef.shape = shape;
        fixture = body.createFixture(fdef);


    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);

    }

    /*
    * Retorna a celula que o player vai contra, conseguindo destruir tudo que e bricks
    * */
    public TiledMapTileLayer.Cell  getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * SuperLuigi.PPM/16),(int)(body.getPosition().y * SuperLuigi.PPM/16));
    }

    public abstract void onHeadHit(Luigi luigi);




}
