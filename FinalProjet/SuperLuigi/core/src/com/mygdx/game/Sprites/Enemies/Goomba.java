package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;

public class Goomba extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private Hud hud;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16/ SuperLuigi.PPM,16/ SuperLuigi.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"),32,0,16,16));
            hud.addScore(300);
            stateTime = 0;
        }
        else if(!destroyed){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            setRegion(walkAnimation.getKeyFrame(stateTime,true));
        }

    }

    @Override
    public void onEnemyHit(Enemy enemy) {
        if(enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.MOVING_SHELL)
            setToDestroy = true;
        else
            reverseVelocity(true, false);
        }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        //bdef.position.set(32,32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperLuigi.PPM);

        fdef.filter.categoryBits = SuperLuigi.ENEMY_BIT;
        fdef.filter.maskBits = SuperLuigi.GROUND_BIT |
                SuperLuigi.COIN_BIT |
                SuperLuigi.BRICK_BIT |
                SuperLuigi.ENEMY_BIT |
                SuperLuigi.OBJECT_BIT |
                SuperLuigi.LUIGI_BIT |
                SuperLuigi.FIREBALL_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create head
        PolygonShape head = new PolygonShape();
        Vector2[] vertex = new Vector2[4];
        vertex[0] = new Vector2(-5,8).scl(1/ SuperLuigi.PPM);
        vertex[1] = new Vector2(5,8).scl(1/ SuperLuigi.PPM);
        vertex[2] = new Vector2(-3,3).scl(1/ SuperLuigi.PPM);
        vertex[3] = new Vector2(3,3).scl(1/ SuperLuigi.PPM);
        head.set(vertex);

        fdef.shape = head;
        fdef.restitution = 0.5f; //permite que o mario, de um salto quando esmaga um inimigo
        fdef.filter.categoryBits = SuperLuigi.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1.5f){
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead(Luigi luigi) {
        setToDestroy = true;
        SuperLuigi.manager.get("audio/sounds/stomp.wav", Sound.class).play();

    }
}
