package com.mygdx.game.Utils;

import com.badlogic.gdx.utils.ObjectMap;

public class LanguagesValues {

    public enum Keys{
        BACK,
        VICTORY,
        DEFEAT,
        LVL1,
        LVL2,
        OPTIONS,
        MUSIC,
        WLCM,
        PLAYAGAIN,
        NICE_WORK,
        NEXT_LEVEL,
        FINISH_GAME,
        GO_MM,
        TIME,
        LVL;
    }

    public ObjectMap<String,String> map;
    public LanguagesValues(){};
    public String getValue(Keys keys){
        return map.get(keys.name());
    }


}
