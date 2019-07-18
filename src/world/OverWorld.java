
package world;

import camera.HUD;
import handler.Assets;
import helper.Player;

import menu.MenuType;
import movingobjects.EnemyHealer;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import staticobjects.GarageArea;
import staticobjects.SandWorm;
import staticobjects.SolidEntity;
import staticobjects.VendorArea;
import staticobjects.WorldArea;
import window.MainWindow;

/**
 * The main world for the main game mode. Supports garages, vendors, settlements and all game features.
 * @author DominicWild
 */
public class OverWorld extends GameWorld{

    private EnemySpawner spawner;
    private final double SPAWN_RATE = (double)(1.0/(MainWindow.FRAMERATE_TARGET*10));
    public static Vector2f homeLocation;
    
    public OverWorld(MainWindow window, String map) {
        super(window, map);
        this.spawner = new EnemySpawner(SPAWN_RATE,this.window.getCamera().getSize());
        //this.homeLocation = new Vector2f(this.tileEngine.getMapSize().x/4, this.tileEngine.getMapSize().y/4);
    }

    @Override
    public void unload() {
        super.unload();
    }

    @Override
    public void load() {
        super.load(); 
        HUD.hideHud = false;
        this.homeLocation = new Vector2f(this.tileEngine.getMapSize().x/4, this.tileEngine.getMapSize().y/4);
        Player.car.setToMaxHP();
        GameWorld.worldObjects.add(Player.car);
        Player.car.setPosition(this.tileEngine.getMapSize().x/4, this.tileEngine.getMapSize().y/4);
        for (int i = 0; i < 30; i++) {
            this.placeEntity(new WorldArea(new HordeWorld(this.window, "Content/Map/horde.csv"), this, this.randomCoordinate()));
        }
        
        for (int i = 0; i < 200; i++) {
         this.placeEntity(new SandWorm(Player.car, this.randomCoordinate()));
         this.placeEntity(new SolidEntity(new Sprite(Assets.TEXTURE.get("rock1"))) );
        }
        
        for(int i=0;i<20;i++){
            this.placeEntity(new GarageArea());
            this.placeEntity(new VendorArea());
            
        }
        
        for(int i=0;i<10;i++){
            this.placeEntity(new WorldArea(new SettlementWorld(this.window,"Content/Map/settlement1.csv"),this,this.randomCoordinate()));
            this.placeEntity(new WorldArea(new SettlementWorld(this.window,"Content/Map/settlement2.csv"),this,this.randomCoordinate()));
        }
        
        EnemyHealer healer = new EnemyHealer();
        healer.setPosition(homeLocation.x + 200, homeLocation.y);
        GameWorld.worldObjects.add(healer);
        EnemyHealer healer2 = new EnemyHealer();
        healer2.setPosition(homeLocation.x + 400, homeLocation.y);
        GameWorld.worldObjects.add(healer2);
        
        Vector2f carPos = Player.car.getPosition();
        SandWorm w = new SandWorm(Player.car, new Vector2f(carPos.x, carPos.y - 400));
        GameWorld.worldObjects.add(w);

        //Adding a rock
        SolidEntity theRock = new SolidEntity(new Sprite(Assets.TEXTURE.get("rock1")));
        theRock.getObject().setPosition(carPos.x, carPos.y - 150);
        GameWorld.worldObjects.add(theRock);
        //Add a garage
        GarageArea garage = new GarageArea();
        garage.getObject().setPosition(Player.car.getPosition().x + 200, Player.car.getPosition().y - 200);
        GameWorld.worldObjects.add(garage);
        //Add a vendor
        VendorArea vendor = new VendorArea();
        vendor.getObject().setPosition(Player.car.getPosition().x + 200, Player.car.getPosition().y + 200);
        GameWorld.worldObjects.add(vendor);
       
        WorldArea hordeArea1 = new WorldArea(new HordeWorld(this.window,"Content/Map/horde.csv"),this,new Vector2f(carPos.x - 1000,carPos.y));
        GameWorld.worldObjects.add(hordeArea1);
        
        WorldArea settlementArea = new WorldArea(new SettlementWorld(this.window,"Content/Map/settlement1.csv"),this,new Vector2f(carPos.x,carPos.y + 1000));
        GameWorld.worldObjects.add(settlementArea);
        
    }

    @Override
    protected void triggerEvents() {
        super.triggerEvents(); 
        this.spawner.spawnRoll();
    } 
   
    private void placeEntity(SolidEntity entity) {
        while (!this.onValidTile(entity)) {
            entity.setPosition(this.randomCoordinate());
        }
        GameWorld.worldObjects.add(entity);
        this.setTileOccupied(entity);
    }

    @Override
    public void playerDeath() {
        /*Player.car.setPosition(this.homeLocation);
        Player.car.setToMaxHP();
        GameWorld.worldObjects.add(Player.car);*/
        this.window.setMenu(MenuType.GAME_OVER);
    }
    
    private Vector2f randomCoordinate(){
        Vector2f mapSize = GameWorld.tileEngine.getMapSize();
        return new Vector2f((float)(mapSize.x*Math.random()), (float)(mapSize.y*Math.random()));
    }
    
    private boolean onValidTile(SolidEntity entity){
        Vector2f entityPos = entity.getPosition();
        Tile onTile = GameWorld.tileEngine.getTileAt(entityPos);
        if(!onTile.isOccupied()){
            return true;
        } else {
            return false;
        }
    }
    
    private void setTileOccupied(SolidEntity entity) {
        Vector2f entityPos = entity.getPosition();
        Tile onTile = GameWorld.tileEngine.getTileAt(entityPos);
        onTile.setOccupied(true);
    }
    
}
