package com.mygdx.game.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Projetile.Fireball;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Turtle;
import com.mygdx.game.SuperLuigi;


public class Luigi extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD}

    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion marioStand;
    private Animation<TextureRegion> luigiRun;
    private TextureRegion luigiJump;
    private TextureRegion bigLuigiStand;
    private TextureRegion bigLuigiJump;
    private TextureRegion luigiDead;
    private Animation<TextureRegion> bigLuigiRun;
    private Animation<TextureRegion> growLuigi;

    private boolean runningRight;
    private float stateTimer;
    private boolean luigiIsBig;
    private boolean runGrowAnim;
    private boolean timeToDefineBigLuigi;
    private boolean timeToRedefineLuigi;
    private boolean luigiIsDead;
    private boolean luigiWon;
    private boolean isPitTouched;


    private PlayScreen screen;

    private Array<Fireball> fireballs;

    public Luigi(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i * 16, 0, 16, 16));
        luigiRun = new Animation(0.1f, frames);

        frames.clear();
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), i * 16, 0, 16, 32));
        bigLuigiRun = new Animation(0.1f, frames);

        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 240, 0, 16, 32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32));
        growLuigi = new Animation<TextureRegion>(0.2f, frames);


        luigiJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), /*16*32*/80, 0, 16, 16);
        bigLuigiJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"), /*16*32*/80, 0, 16, 32);

        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0, 0, 16, 16); //Aplica a textura do "stand" mario
        bigLuigiStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"), 0, 0, 16, 32);

        luigiDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);


        defineLuigi();

        setBounds(0, 0, 16 / SuperLuigi.PPM, 16 / SuperLuigi.PPM);
        setRegion(marioStand);

        fireballs = new Array<Fireball>();
    }

    public void defineBigLuigi() {
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / SuperLuigi.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperLuigi.PPM);
        fdef.filter.categoryBits = SuperLuigi.LUIGI_BIT;
        fdef.filter.maskBits = SuperLuigi.GROUND_BIT |
                SuperLuigi.COIN_BIT |
                SuperLuigi.BRICK_BIT |
                SuperLuigi.ENEMY_BIT |
                SuperLuigi.OBJECT_BIT |
                SuperLuigi.ENEMY_HEAD_BIT |
                SuperLuigi.ITEM_BIT |
                SuperLuigi.PIT_BIT |
                SuperLuigi.VICTORY_BIT |
                SuperLuigi.FIREBALL_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -14 / SuperLuigi.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM), new Vector2(2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM));
        fdef.filter.categoryBits = SuperLuigi.LUIGI_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigLuigi = false;
    }


    public void defineLuigi() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(64 / SuperLuigi.PPM, 32 / SuperLuigi.PPM);
        //bdef.position.set(32,32);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperLuigi.PPM);

        fdef.filter.categoryBits = SuperLuigi.LUIGI_BIT;
        fdef.filter.maskBits = SuperLuigi.GROUND_BIT |
                SuperLuigi.COIN_BIT |
                SuperLuigi.BRICK_BIT |
                SuperLuigi.ENEMY_BIT |
                SuperLuigi.OBJECT_BIT |
                SuperLuigi.ENEMY_HEAD_BIT |
                SuperLuigi.ITEM_BIT |
                SuperLuigi.PIT_BIT |
                SuperLuigi.VICTORY_BIT |
                SuperLuigi.FIREBALL_BIT;

        //shape.setRadius(5);

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape(); //linha entre dois pontos
        head.set(new Vector2(-2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM), new Vector2(2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM));
        fdef.filter.categoryBits = SuperLuigi.LUIGI_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this); //aplica uma tag, detetando a cabeça do player

    }

    public void redefineLuigi() {
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperLuigi.PPM);
        fdef.filter.categoryBits = SuperLuigi.LUIGI_BIT;
        fdef.filter.maskBits = SuperLuigi.GROUND_BIT |
                SuperLuigi.COIN_BIT |
                SuperLuigi.BRICK_BIT |
                SuperLuigi.ENEMY_BIT |
                SuperLuigi.OBJECT_BIT |
                SuperLuigi.ENEMY_HEAD_BIT |
                SuperLuigi.ITEM_BIT |
                SuperLuigi.PIT_BIT |
                SuperLuigi.VICTORY_BIT |
                SuperLuigi.FIREBALL_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM), new Vector2(2 / SuperLuigi.PPM, 6 / SuperLuigi.PPM));
        fdef.filter.categoryBits = SuperLuigi.LUIGI_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineLuigi = false;

    }


    public void update(float dt) {

        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }

        if (luigiIsBig)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / SuperLuigi.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        if (timeToDefineBigLuigi)
            defineBigLuigi();
        if (timeToRedefineLuigi)
            redefineLuigi();

        for (Fireball ball : fireballs) {
            ball.update(dt);
            if (ball.isDestroyed())
                fireballs.removeValue(ball, true);
        }

    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {

            case DEAD:
                region = luigiDead;
                break;

            case GROWING:
                region = growLuigi.getKeyFrame(stateTimer);
                if (growLuigi.isAnimationFinished(stateTimer)) {
                    runGrowAnim = false;
                }
                break;
            case JUMPING:
                region = luigiIsBig ? bigLuigiJump : luigiJump;
                break;
            case RUNNING:
                region = luigiIsBig ? bigLuigiRun.getKeyFrame(stateTimer, true) : luigiRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = luigiIsBig ? bigLuigiStand : marioStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (luigiIsDead)
            return State.DEAD;

        else if (runGrowAnim)
            return State.GROWING;

        else if (b2body.getLinearVelocity().y > 0 ||
                (b2body.getLinearVelocity().y < 0 &&
                        previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void grow() {
        if (!isBig()) {
            runGrowAnim = true;
            luigiIsBig = true;
            timeToDefineBigLuigi = true;
            setBounds(getX(), getY(), getWidth(), getHeight() * 2);
            SuperLuigi.manager.get("audio/sounds/powerup.wav", Sound.class).play();
        }
    }

    public void die() {

        if (!isDead()) {
            SuperLuigi.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            SuperLuigi.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
            luigiIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = SuperLuigi.NOTHING_BIT;

            for (Fixture fixture : b2body.getFixtureList()) {
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public void jump() {
        if (currentState != State.JUMPING) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }


    public void hit(Enemy enemy) {
        if (enemy instanceof Turtle && ((Turtle) enemy).currentState == Turtle.State.STANDING_SHELL)
            ((Turtle) enemy).kick(enemy.b2body.getPosition().x > b2body.getPosition().x ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        else {
            if (luigiIsBig) {
                luigiIsBig = false;
                timeToRedefineLuigi = true;
                setBounds(getX(), getY(), getWidth(), getHeight() / 2);
                SuperLuigi.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
            } else {
                die();
            }
        }
    }

    public void draw(Batch batch) {
        super.draw(batch);
        for (Fireball ball : fireballs)
            ball.draw(batch);
    }

    public void fire() {
        fireballs.add(new Fireball(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, runningRight ? true : false));
    }

    public boolean isBig() {
        return luigiIsBig;
    }

    public boolean isDead() {
        return luigiIsDead;
    }

    public boolean victory() {
        return luigiWon;
    }

    public void luigiWon() {
        luigiWon = true;
    }

    public boolean luigiTouchedPit() {
        return isPitTouched;
    }

    public void pitTouched() {
        isPitTouched = true;
    }


    public float getStateTimer() {
        return stateTimer;
    }

}
