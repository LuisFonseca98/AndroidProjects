package com.mygdx.game.Utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Projetile.Fireball;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.game.SuperLuigi;

/*
* Classe que permite detetar dois objetos que detetam colisão
*/
public class WorldContactListener implements ContactListener {
    @Override
    /*Quando duas colisoes choma uma com a outra*/
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch (cDef){
            case SuperLuigi.LUIGI_HEAD_BIT | SuperLuigi.BRICK_BIT:
            case SuperLuigi.LUIGI_HEAD_BIT | SuperLuigi.COIN_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.LUIGI_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Luigi) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Luigi) fixA.getUserData());
                break;
            case SuperLuigi.ENEMY_HEAD_BIT | SuperLuigi.LUIGI_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Luigi) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Luigi) fixA.getUserData());
                break;
            case SuperLuigi.ENEMY_BIT | SuperLuigi.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

            case SuperLuigi.LUIGI_BIT | SuperLuigi.ENEMY_BIT :
                if(fixA.getFilterData().categoryBits == SuperLuigi.LUIGI_BIT)
                    ((Luigi) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Luigi) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;

            case SuperLuigi.ENEMY_BIT | SuperLuigi.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).onEnemyHit((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).onEnemyHit((Enemy)fixA.getUserData());
                break;

            case SuperLuigi.ITEM_BIT | SuperLuigi.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true,false);
                break;

            case SuperLuigi.ITEM_BIT | SuperLuigi.LUIGI_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Luigi)fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Luigi)fixA.getUserData());
                break;

            case SuperLuigi.FIREBALL_BIT | SuperLuigi.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.FIREBALL_BIT)
                    ((Fireball)fixA.getUserData()).setToDestroy();
                else
                    ((Fireball)fixB.getUserData()).setToDestroy();
                break;

            case SuperLuigi.LUIGI_BIT | SuperLuigi.PIT_BIT:
                if(fixA.getFilterData().categoryBits == SuperLuigi.LUIGI_BIT)
                    ((Luigi)fixA.getUserData()).pitTouched();
                else
                    ((Luigi)fixB.getUserData()).pitTouched();
                break;

            case SuperLuigi.LUIGI_BIT|SuperLuigi.VICTORY_BIT:
                if (fixA.getFilterData().categoryBits == SuperLuigi.LUIGI_BIT)
                    ((Luigi) fixA.getUserData()).luigiWon();
                else
                    ((Luigi) fixB.getUserData()).luigiWon();
                break;



        }
    }

    @Override /*Quando duas colisoes deixam se de conectar*/
    public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
