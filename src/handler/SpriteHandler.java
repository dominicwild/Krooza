package handler;

import org.jsfml.graphics.Sprite;

/**
 * Set of static methods to mutate/transform sprites in JSFML
 */
public final class SpriteHandler {

    public static void centerOrigin(Sprite s){
        s.setOrigin((s.getLocalBounds().left + (s.getLocalBounds().width/2)) , (s.getLocalBounds().top) + (s.getLocalBounds().height/2));
    }

    public static void rotate(Sprite s, int r){
        s.rotate(r);
    }

    public static void scale(Sprite s, float sf){
        s.scale(sf, sf);
    }

    public static void scale(Sprite s, float sfX, float sfY){
        s.scale(sfX, sfY);
    }

}
