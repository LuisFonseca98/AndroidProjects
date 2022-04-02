package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.mygdx.game.MyGdxGame.log;

class GameScreenBox2D implements Screen, InputProcessor {

    private static final float WORLD_STEP = 1/60f;
    private static final int WORLD_VELOCITY_ITERATIONS = 6;
    private static final int WORLD_POSITION_ITERATIONS = 2;
    private static final float FIGURE_SIZE = 200f; // class constant with the figures size
    float angle;

    private int counterObjects;
    private int counterTime;//como são 60 frames cada frame é 0.0167s entao 12 frames faz 0.2, faz se um contador quando chega a 12 cria novo objeto até 20

    MyGdxGame gameScreenBox2D;
    SpriteBatch batch;
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera camera;
    AssetsLoader assetsLoader;

    //cria um random, usando esta lib
    RandomXS128 randomXS128 = new RandomXS128(System.currentTimeMillis());

    // class attribute , to support World objects - a permanent array
    Array<Body> bodies = new Array<>();

    float elapsedTime,rotationSpeed;

    public enum  FormType{
        Box,Circle,Triangle,Hexagon
    }


    public GameScreenBox2D(MyGdxGame gameScreenBox2D) {
        this.gameScreenBox2D = gameScreenBox2D;
        batch = new SpriteBatch();

        // First argument (Vector) sets the horizontal and vertical gravity forces
        // Second argument tells to render all bodies, even inactive bodies
        world = new World(new Vector2(0,0),false);
        // Builds a Box2DDebugRenderer object
        debugRenderer = new Box2DDebugRenderer();
        assetsLoader = new AssetsLoader();

        camera = buildCamera();
        batch.setProjectionMatrix(camera.combined);
        createWorldBoundaries();

        Gdx.input.setInputProcessor(this);

        createBoxBody(300,300);
        createCircleBody(300,600);
        createTriangleBody(300,900);
        createHexBody(300,1200);

        world.setGravity(new Vector2(0,-20));

    }

    // Create 4 boundary objects with thickness of .5f, limiting the device screen
    // Build one static body at 100 of the top, bottom, left and righ
    private void createWorldBoundaries() {
        createStaticBody(0, 100, camera.viewportWidth , .5f);
        createStaticBody(0, (int) (camera.viewportHeight - 100), camera.viewportWidth , .5f);
        createStaticBody(100, 0, .5f, camera.viewportHeight);
        createStaticBody(500, 0, .5f, camera.viewportWidth);

    }

    private void createStaticBody(int x, int y, float width, float height) {
        BodyDef WallBodyDef = new BodyDef();
        WallBodyDef.position.set(new Vector2(x,y));
        Body wallBody = world.createBody(WallBodyDef);
        PolygonShape wallBox = new PolygonShape();
        wallBox.setAsBox(width,height);
        wallBody.createFixture(wallBox,0.0f);


    }

    private OrthographicCamera buildCamera() {
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        // Set camera’s position in the center of the screen
        camera.position.set(camera.viewportWidth * .5f,
                camera.viewportHeight * .5f, 0f);

        //Update and return the camera
        camera.update();
        return camera;
    }

    private void createBoxBody(int screenX , int screenY) {
        PolygonShape box = new PolygonShape();
        box.setAsBox(FIGURE_SIZE/2,FIGURE_SIZE/2);// hx/hy half-width/height
        createBody(screenX , screenY , box, FormType.Box);

    }

    private void createCircleBody(int screenX,int screenY){
        CircleShape circle = new CircleShape();
        circle.setRadius(FIGURE_SIZE/2);
        createBody(screenX , screenY , circle, FormType.Circle);
    }

    private void createHexBody(int screenX,int screenY) {
        PolygonShape hex = new PolygonShape();
        Vector2[] vertices = new Vector2[6];
        vertices[0] = new Vector2(0,FIGURE_SIZE/2);
        vertices[1] = new Vector2(0,-FIGURE_SIZE/2);
        vertices[2] = new Vector2(-FIGURE_SIZE * 0.4375f, FIGURE_SIZE * 0.2578125f);
        vertices[3] = new Vector2(-FIGURE_SIZE * 0.4375f, -FIGURE_SIZE * 0.2578125f);
        vertices[4] = new Vector2(FIGURE_SIZE * 0.4375f, FIGURE_SIZE * 0.2578125f);
        vertices[5] = new Vector2(FIGURE_SIZE * 0.4375f, -FIGURE_SIZE * 0.2578125f);
        hex.set(vertices);
        createBody(screenX , screenY , hex, FormType.Hexagon);
    }

    private void createTriangleBody(int screenX, int screenY) {
        PolygonShape triangle = new PolygonShape();
        Vector2[] verticesTriangle = new Vector2[3];
        verticesTriangle[0] = new Vector2(0, FIGURE_SIZE /2);
        verticesTriangle[1] = new Vector2(-FIGURE_SIZE/2, -FIGURE_SIZE/2);
        verticesTriangle[2] = new Vector2(FIGURE_SIZE/2,  -FIGURE_SIZE/2);
        triangle.set(verticesTriangle);
        createBody(screenX,screenY,triangle,FormType.Triangle);

    }


    private void createBody(int screenX, int screenY,Shape shape,FormType formType){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(screenX,screenY);
        Body body = world.createBody(bodyDef);
        body.createFixture(createFixtureDef(shape));
        body.setUserData(formType);
    }

    private FixtureDef createFixtureDef(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        return fixtureDef;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.3f, 0.8f, 1); // set colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        elapsedTime = delta;
        angle+=delta*rotationSpeed;
        angle%=360;      // Limits the angle to be <= 360
        while (angle < 0)  //
            angle+=360;

        debugRenderer.render(world,camera.combined);
        batch.begin();
        world.getBodies(bodies);

        if(counterTime == 12){
            createRandomObjects();
            counterTime = 0;
        } else counterTime++;

        for(Body body: bodies) {
            Object data = body.getUserData();
            if (data != null) {
                Animation<TextureRegion> animation;
                if (data == FormType.Circle) {
                    animation = assetsLoader.faceCircleAnimation;
                } else if (data == FormType.Triangle) {
                    animation = assetsLoader.faceTriAnimation;
                } else if (data == FormType.Hexagon) {
                    animation = assetsLoader.faceHexAnimation;
                } else {
                    animation = assetsLoader.faceBoxAnimation;
                }
                TextureRegion region = animation.getKeyFrame(elapsedTime, true);
                batch.draw(region,body.getPosition().x - FIGURE_SIZE/2,body.getPosition().y - FIGURE_SIZE/2,
                        FIGURE_SIZE/2,FIGURE_SIZE/2 ,FIGURE_SIZE,FIGURE_SIZE,1,1,(body.getAngle()*57.2957795f)%360);
            }
        }
        batch.end();
        world.step(WORLD_STEP, WORLD_VELOCITY_ITERATIONS,WORLD_POSITION_ITERATIONS);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        log("touchDown on x:" + screenX + ", y " + screenY);
        int y = Gdx.graphics.getHeight() - screenY;
        log("insert object at x:" + screenX + ", y " + y);
        addRandomBody(screenX,y);
        return false;
    }

    private void addRandomBody(int screenX, int screenY) {
        switch (FormType.values()[random.nextInt(FormType.values().length)]) {
            case Box:
                createBoxBody(screenX,screenY);
                break;
            case Circle:
                createCircleBody(screenX,screenY);
                break;
            case Triangle:
                createTriangleBody(screenX,screenY);
                break;
            case Hexagon:
                createHexBody(screenX,screenY);
                break;
        }
    }

    private void createRandomObjects(){
        if(counterObjects < 20) {
            switch (FormType.values()[random.nextInt(FormType.values().length)]) {
                case Box:
                    createBoxBody((int) camera.viewportWidth / 2, (int)camera.viewportHeight-200);
                    break;
                case Circle:
                    createCircleBody((int) camera.viewportWidth / 2, (int)camera.viewportHeight-200);
                    break;
                case Triangle:
                    createTriangleBody((int) camera.viewportWidth / 2, (int)camera.viewportHeight-200);
                    break;
                case Hexagon:
                    createHexBody((int) camera.viewportWidth / 2, (int)camera.viewportHeight-200);
            }
            counterObjects++;
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            log("back pressed");
            gameScreenBox2D.setInicialScreen();
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        assetsLoader.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
