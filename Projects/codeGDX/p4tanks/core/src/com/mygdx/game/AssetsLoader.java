package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

public class AssetsLoader extends BaseAssetsLoader  {
    final Texture backTexture,notesTexture,tankTexture,textTexture,shadowTexture;
    final Sound explosion , music;
    final BitmapFont font, shadow;

    AssetsLoader(){
        // Textures
        addDisposable(backTexture= new Texture(Gdx.files.internal("back.jpg")));
        addDisposable(notesTexture= new Texture(Gdx.files.internal("notes.png")));
        addDisposable(tankTexture= new Texture(Gdx.files.internal("tank.png")));
        addDisposable(textTexture= new Texture(Gdx.files.internal("text.png")));
        addDisposable(shadowTexture= new Texture(Gdx.files.internal("shadow.png")));

        // Sounds, LibGDX supports audio formats: ogg, mp3 and wav
        String soundName = "explosion.ogg";
        addDisposable(explosion= Gdx.audio.newSound(Gdx.files.internal(soundName)));
        soundName = "wagner_the_ride_of_the_valkyries.ogg";
        addDisposable(music = Gdx.audio.newSound(Gdx.files.internal(soundName)));

        //Fonts
        addDisposable(font = new BitmapFont(Gdx.files.internal("text.fnt")));
        font.getData().setScale(2f,2f);

        addDisposable(shadow = new BitmapFont(Gdx.files.internal("shadow.fnt")));
        shadow.getData().setScale(2f,2f);

    }
}

class BaseAssetsLoader {
    private List<Disposable> disposableResources = new ArrayList<>();
    public void addDisposable(Disposable disposable) {
        disposableResources.add(disposable);
    }
    public Disposable getDisposable(int index) {
        return disposableResources.get(index);
    }
    // Extract square textureRegions from texture and build animation with them
    static Animation<TextureRegion>buildAnimationFromTexture(Texture texture, int textRegSize, boolean flipHorizontally, boolean flipVertically, Animation.PlayMode playMode) {
        int numberInWidth = texture.getWidth()/textRegSize;
        int numberInHeight = texture.getHeight()/textRegSize ;
        int numberOfTextureRegions = numberInWidth*numberInHeight ;
        TextureRegion[] texRegions = new TextureRegion[numberOfTextureRegions];
        for(int i = 0, x = 0, y = 0; i <texRegions.length ; ++i) {
            texRegions[i] = new TextureRegion (texture , x*textRegSize , y*textRegSize, textRegSize, textRegSize);
            texRegions[i].flip(flipHorizontally,flipVertically);
            if (++ x > numberInWidth ) {
                x = 0;
                ++y;
            }
        }
        Animation<TextureRegion> animation = new Animation<>(0.5f, texRegions );
        animation.setPlayMode(playMode);
        return animation ;
    }
    void dispose () {
        for (Disposable disposableResource:disposableResources) {
            disposableResource.dispose();
        }
    }
}