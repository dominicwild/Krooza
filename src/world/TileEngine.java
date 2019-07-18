package world;

import handler.CSVHandler;
import org.jsfml.system.Vector2f;
import sequence.SequenceType;
import window.MainWindow;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.system.Vector2i;
import staticobjects.SolidEntity;

/**
 * A class that deals with the creation of the base map. Placing down the tiles,
 * borders and any general map data.
 *
 * @author Will Fantom, Dominic Wild
 */
public class TileEngine implements Runnable {

    //WINDOW
    private MainWindow window;

    //MAP
    private ArrayList<String> mapData;
    private Vector2i mapSize;
    protected static int TILE_SIZE;

    //TILES
    private Tile[][] tiles;

    //LOADING
    private Thread load;
    private boolean loaded;
    private boolean boundariesAdded;
    private boolean running;

    /**
     *
     * @param window
     * @param mapPath
     */
    public TileEngine(MainWindow window, String mapPath) {

        this.window = window;
        this.mapData = CSVHandler.readCSV(mapPath);
        this.load = new Thread(this);
        this.loaded = false;
        this.boundariesAdded = false;

        this.mapSize = this.getMapSizeTiles();

        this.tiles = new Tile[(int) this.mapSize.x][(int) this.mapSize.y];
        TILE_SIZE = Integer.parseInt(this.mapData.get(2));
        for (int x = 0; x < this.mapSize.x; x++) {
            for (int y = 0; y < this.mapSize.y; y++) {
                this.tiles[x][y] = new Tile(x * TILE_SIZE, y * TILE_SIZE);
            }
        }
    }

    /**
     * Returns the size of the map in tiles
     *
     * @return
     */
    public Vector2i getMapSizeTiles() {
        return new Vector2i(Integer.parseInt(this.mapData.get(0)), Integer.parseInt(this.mapData.get(1)));
    }

    /**
     * Returns the size of the map in pixels
     *
     * @return
     */
    public Vector2f getMapSize() {
        return new Vector2f(Float.parseFloat(this.mapData.get(0)) * Integer.parseInt(this.mapData.get(2)),
                Float.parseFloat(this.mapData.get(1)) * Integer.parseInt(this.mapData.get(2)));
    }

    /**
     * Returns if the map is loaded
     */
    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Starts loading the map
     */
    public void loadTiles() {
        if (!this.isLoaded()) {
            this.load.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(TileEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //DRAWING
    public void draw() {
        for (int x = 0; x < mapSize.x; x++) {
            for (int y = 0; y < mapSize.y; y++) {
                if (this.tiles[x][y].getIsVisible()) {
                    this.window.draw(this.tiles[x][y]);
                }
            }
        }
    }

    private boolean isVisible(int x, int y) {
        float right = this.tiles[x][y].getGlobalBounds().left + this.tiles[x][y].getGlobalBounds().width;
        float left = this.tiles[x][y].getGlobalBounds().left;
        float base = this.tiles[x][y].getGlobalBounds().top + this.tiles[x][y].getGlobalBounds().height;
        float top = this.tiles[x][y].getGlobalBounds().top;

        float xDist = (this.window.getCamera().getSize().x / 2) + Integer.parseInt(this.mapData.get(2));
        float yDist = (this.window.getCamera().getSize().y / 2) + Integer.parseInt(this.mapData.get(2));

        if ((right >= window.getCamera().getCenter().x - xDist)
                && (left <= window.getCamera().getCenter().x + xDist)
                && (base >= window.getCamera().getCenter().y - yDist)
                && (top <= window.getCamera().getCenter().y + yDist)) {
            return true;
        }
        return false;
    }

    //LOADING
    public void run() {
        this.running = true;
        this.loaded = false;
        this.window.setSequence(SequenceType.LOADING);
        
        this.loadBase();
        this.loadBorders();
        this.loadMain();
        this.loadTarPits();
        this.loadLakes();
        this.loadRoads();
        if (!boundariesAdded) {
            this.createBoundaries();
            this.boundariesAdded = true;
        }
        
        this.window.setSequence(null);
        this.loaded = true;

        while (this.window.isOpen() && this.running) {
            for (int x = 0; x < mapSize.x; x++) {
                for (int y = 0; y < mapSize.y; y++) {
                    this.tiles[x][y].setVisible(this.isVisible(x, y));
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBase() {
        String baseTile = this.mapData.get(4);
        for (int x = 0; x < this.mapSize.x; x++) {
            for (int y = 0; y < this.mapSize.y; y++) {
                this.tiles[x][y].setTexture(baseTile);
                this.tiles[x][y].setType(this.stringToType(baseTile));   
            }
        }
    }

    private void loadBorders() {
        String borderTile = this.mapData.get(5);
        String cornerTile = this.mapData.get(6);
        int b = Integer.parseInt(this.mapData.get(3));
        int bBot = Integer.parseInt(this.mapData.get(1)) - b;
        int bRight = Integer.parseInt(this.mapData.get(0)) - b;

        for (int x = b + 1; x < bRight - 1; x++) {
            this.tiles[x][b].setTexture(borderTile, 2);
            this.tiles[x][b].setOccupied(true);
        }
        for (int x = b + 1; x < bRight - 1; x++) {
            this.tiles[x][bBot - 1].setTexture(borderTile, 0);
            this.tiles[x][bBot - 1].setOccupied(true);
        }
        for (int y = b + 1; y < bBot - 1; y++) {
            this.tiles[b][y].setTexture(borderTile, 1);
            this.tiles[b][y].setOccupied(true);
        }
        for (int y = b + 1; y < bBot - 1; y++) {
            this.tiles[bRight - 1][y].setTexture(borderTile, 3);
            this.tiles[bRight - 1][y].setOccupied(true);
        }

        this.tiles[b][b].setTexture(cornerTile, 2);
        this.tiles[b][b].setOccupied(true);
        this.tiles[bRight - 1][b].setTexture(cornerTile, 3);
        this.tiles[bRight - 1][b].setOccupied(true);
        this.tiles[b][bBot - 1].setTexture(cornerTile, 1);
        this.tiles[b][bBot - 1].setOccupied(true);
        this.tiles[bRight - 1][bBot - 1].setTexture(cornerTile, 0);
        this.tiles[bRight - 1][bBot - 1].setOccupied(true);
    }
    
    private void createBoundaries(){
        for(int i=1;i<this.mapSize.x-1;i++){
            GameWorld.worldObjects.add(new SolidEntity(this.tiles[i][0]));
            GameWorld.worldObjects.add(new SolidEntity(this.tiles[i][this.mapSize.y-1]));
        }
        for(int i=1;i<this.mapSize.y-1;i++){
            GameWorld.worldObjects.add(new SolidEntity(this.tiles[0][i]));
            GameWorld.worldObjects.add(new SolidEntity(this.tiles[this.mapSize.x-1][i]));
        }
    }

    private void loadMain() {
        String mainTile = this.mapData.get(7);
        int mainTileVar = Integer.parseInt(this.mapData.get(8));
        int bDist = Integer.parseInt(this.mapData.get(3));

        for (int x = bDist + 1; x < Integer.parseInt(this.mapData.get(0)) - 1 - bDist; x++) {
            for (int y = bDist + 1; y < Integer.parseInt(this.mapData.get(1)) - 1 - bDist; y++) {
                Random r = new Random();
                if (r.nextInt(50) < 45) {
                    this.tiles[x][y].setTexture(mainTile + String.valueOf(1));
                    this.tiles[x][y].setType(this.stringToType(mainTile));
                } else {
                    String path = mainTile + String.valueOf(r.nextInt(mainTileVar - 1) + 2);
                    this.tiles[x][y].setTexture(path);
                    this.tiles[x][y].setType(this.stringToType(path));
                }
            }
        }
    }

    private void loadTarPits() {
        if (!this.mapData.get(9).toLowerCase().equals("none")) {
            String[] pitsS = this.mapData.get(9).split(" ");
            for (int t = 0; t < pitsS.length; t++) {
                Vector2f pit = new Vector2f(Float.parseFloat(pitsS[t].split(",")[0]), Float.parseFloat(pitsS[t].split(",")[1]));
                if (pit.x <= this.mapSize.x && pit.y <= this.mapSize.y) {
                    TerrainBuilder builder = new TerrainBuilder(1, pit, tiles);
                }
            }
        }

    }

    private void loadLakes() {
        if (!this.mapData.get(10).toLowerCase().equals("none")) {
            String[] lakeS = this.mapData.get(10).split(" ");
            for (int t = 0; t < lakeS.length; t++) {
                Vector2f pit = new Vector2f(Float.parseFloat(lakeS[t].split(",")[0]), Float.parseFloat(lakeS[t].split(",")[1]));
                if (pit.x <= this.mapSize.x && pit.y <= this.mapSize.y) {
                    TerrainBuilder builder = new TerrainBuilder(0, pit, tiles);
                }
            }
        }
    }

    private void loadRoads() {
        if (!this.mapData.get(11).toLowerCase().equals("none")) {
            String[] roadS = this.mapData.get(11).split(" ");
            for (int t = 0; t < roadS.length; t++) {
                Vector2f pit = new Vector2f(Float.parseFloat(roadS[t].split(",")[0]), Float.parseFloat(roadS[t].split(",")[1]));
                if (pit.x <= this.mapSize.x && pit.y <= this.mapSize.y) {
                    TerrainBuilder builder = new TerrainBuilder(2, pit, tiles);
                }
            }
        }
    }

    public Tile getTileAt(float x, float y) {
        return getTileAt(new Vector2f(x, y));
    }

    public Tile getTileAt(Vector2f point) {
        int xPosTile = (int) (point.x / TILE_SIZE);
        int yPosTile = (int) (point.y / TILE_SIZE);
        return this.tiles[xPosTile][yPosTile];
    }
    
    public static TileType stringToType(String type){
        if(type.contains("TILE_R")){
            return TileType.ROAD;
        } else if(type.contains("TILE_S")){
            return TileType.SAND;
        } else if(type.contains("TILE_T")){
            return TileType.TAR;
        } else if(type.contains("TILE_W")){
            return TileType.WATER;
        } else {
            return TileType.NULL;
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    
    
    
}
