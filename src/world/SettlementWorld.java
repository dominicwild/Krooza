
package world;

import camera.HUD;
import handler.Assets;
import helper.Player;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import staticobjects.Chest;
import staticobjects.WorldArea;
import window.MainWindow;

/**
 * A world that has on-foot game play.
 * @author DominicWild
 */
public class SettlementWorld extends GameWorld{

    public SettlementWorld(MainWindow window, String map) {
        super(window, map);
    }

    @Override
    public void load() {
        super.load();
        HUD.hideHud = true;
        Vector2f mapSize = GameWorld.tileEngine.getMapSize();
        Player.character.setPosition(mapSize.x/2, mapSize.y/2);
        GameWorld.worldObjects.add(Player.character);
        Sprite mat = new Sprite(Assets.getTexture("exit_mat"));
        mat.scale(2,2);
        WorldArea exit = new WorldArea(new OverWorld(this.window,"Content/Map/main.csv"),this, mat);
        exit.setPosition((float) ((mapSize.x/2)*0.85), mapSize.y - (TileEngine.TILE_SIZE + 150));
        GameWorld.worldObjects.add(exit);
        
        Chest chest = new Chest();
        chest.setPosition((float) ((mapSize.x/2)*0.85), (TileEngine.TILE_SIZE + 150));
        GameWorld.worldObjects.add(chest);
    }

    @Override
    public void playerDeath() {
        
    }
    
}
