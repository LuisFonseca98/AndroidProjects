package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Luigi;
import com.mygdx.game.SuperLuigi;

public class Turtle extends Enemy {

    public static final int KICK_LEFT_SPEED = -2;
    public static final int KICK_RIGHT_SPEED = 2;


    public enum State {WALKING, MOVING_SHELL, STANDING_SHELL, DEAD};
    public State currentState;
    public State previousState;

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private float deadRotationDegrees;
    private boolean destroyed;
    private Hud hud;

    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 0, 0, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 16, 0, 16, 24));
        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"), 64, 0, 16, 24);
        walkAnimation = new Animation(0.2f, frames);

        currentState = previousState = State.WALKING;
        deadRotationDegrees = 0;

        setBounds(getX(),getY(),16/ SuperLuigi.PPM, 24 / SuperLuigi.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
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
                SuperLuigi.LUIGI_BIT;

        //shape.setRadius(5);

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
        fdef.restitution = 1.8f; //permite que o mario, de um salto quando esmaga um inimigo
        fdef.filter.categoryBits = SuperLuigi.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitOnHead(Luigi luigi) {
        if(currentState == State.STANDING_SHELL) {
            if(luigi.b2body.getPosition().x > b2body.getPosition().x)
                velocity.x = -2;
            else
                velocity.x = 2;
            currentState = State.MOVING_SHELL;

        }
        else {
            currentState = State.STANDING_SHELL;
            SuperLuigi.manager.get("audio/sounds/stomp.wav", Sound.class).play();
            hud.addScore(200);
            velocity.x = 0;
        }
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == State.STANDING_SHELL && stateTime > 5){
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - 8 /SuperLuigi.PPM);
        if(currentState == State.DEAD){
            deadRotationDegrees += 3;
            rotate(deadRotationDegrees);
            if(stateTime > 5 && !destroyed){
                world.destroyBody(b2body);
                destroyed = true;
            }
        }
        else
            b2body.setLinearVelocity(velocity);
    }

    @Override
    public void onEnemyHit(Enemy enemy) {
        reverseVelocity(true, false);
    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;

        switch (currentState){
            case MOVING_SHELL:

            case STANDING_SHELL:
                region = shell;
                break;

            case WALKING:
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }

        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true,false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }


    public void kick(int direction){
        velocity.x = direction;
        currentState = State.MOVING_SHELL;
    }

    public State getCurrentState(){
        return currentState;
    }

    public void draw(Batch batch){
        if(!destroyed)
            super.draw(batch);
    }

    public void killed(){
        currentState = State.DEAD;
        Filter filter = new Filter();
        filter.maskBits = SuperLuigi.NOTHING_BIT;
        for (Fixture fixture: b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(0,5f), b2body.getWorldCenter(),true);
    }
}
