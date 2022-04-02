package com.mygdx.game.Utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Goomba;
import com.mygdx.game.Sprites.Enemies.Turtle;
import com.mygdx.game.Sprites.TileObjects.Brick;
import com.mygdx.game.Sprites.TileObjects.Coin;
import com.mygdx.game.SuperLuigi;

public class B2WorldCreator {

    private Array<Goomba> goombas;
    private Array<Turtle> turtles;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //For the ground layer
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / SuperLuigi.PPM, (rect.getY() + rect.getHeight() / 2) / SuperLuigi.PPM);
            //bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight());

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2)/ SuperLuigi.PPM, (rect.getHeight() / 2) / SuperLuigi.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);

        }
        //for the pipes layer
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / SuperLuigi.PPM, (rect.getY() + rect.getHeight() / 2) / SuperLuigi.PPM);
            //bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight());

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2)/ SuperLuigi.PPM, (rect.getHeight() / 2) / SuperLuigi.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SuperLuigi.OBJECT_BIT;

            body.createFixture(fdef);

        }

        //For the bricks layer
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();

           new Brick(screen,object);
        }

        //for the coins layer
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, object);

        }

        //for the goomba layer
        goombas = new Array<Goomba>();

        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen,rect.getX()/ SuperLuigi.PPM, rect.getY()/ SuperLuigi.PPM));

        }

        //for the turtle layer
        turtles = new Array<Turtle>();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen,rect.getX()/ SuperLuigi.PPM, rect.getY()/ SuperLuigi.PPM));

        }

        //for the blocks
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / SuperLuigi.PPM, (rect.getY() + rect.getHeight() / 2) / SuperLuigi.PPM);
            //bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight());

            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / SuperLuigi.PPM, (rect.getHeight() / 2) / SuperLuigi.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SuperLuigi.OBJECT_BIT;

            body.createFixture(fdef);
        }

        //for the pit
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type= BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX()+rect.getWidth()/2)/ SuperLuigi.PPM, (rect.getY()+rect.getHeight()/2)/SuperLuigi.PPM);
            body=world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2/SuperLuigi.PPM,rect.getHeight()/2/SuperLuigi.PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=SuperLuigi.PIT_BIT;
            body.createFixture(fdef);

        }

        for(MapObject object:map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type= BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX()+rect.getWidth()/2)/ SuperLuigi.PPM, (rect.getY()+rect.getHeight()/2)/SuperLuigi.PPM);
            body=world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2/SuperLuigi.PPM,rect.getHeight()/2/SuperLuigi.PPM);
            fdef.shape=shape;
            fdef.filter.categoryBits=SuperLuigi.VICTORY_BIT;
            body.createFixture(fdef);


        }


    }


    public Array<Enemy> getEnemies(){
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goombas);
        enemies.addAll(turtles);
        return enemies;
    }

}
