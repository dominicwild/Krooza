package world;

import handler.CSVHandler;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that builds terrain structures. Such as, lakes and tar pits. - Dominic Wild
 * @author Dom Potts
 */
public class TerrainBuilder {

    private final static String PATH = "Content/Map/";
    private String terrainType;
    private int terrainSubType;
    private Tile[][] mapTiles;
    private ArrayList<String> file;

    public TerrainBuilder(int type, Vector2f start, Tile[][] tiles){
        this.mapTiles = tiles;
        Random rand = new Random();

        if(type==0){
            terrainType = "Lake";
            terrainSubType = rand.nextInt(2)+1;
        }
        else if(type==1){
            terrainType = "TarPit";
            terrainSubType = rand.nextInt(3)+1;
        }
        else{
            terrainType = "Road";
            terrainSubType = rand.nextInt(6)+1;
        }

        String absPath = (PATH+terrainType+terrainSubType+ ".csv");
        this.file = CSVHandler.readCSV(absPath);
        for(int y=0 ; y<file.size() ; y++){
            String s = file.get(y);
            int maxX = (int)(s.split(",").length + start.x);
            for(int x = (int)start.x; x<maxX; x++){
                this.setTile(x, (int)(y+start.y), s.split(",")[(int)(x-start.x)]);
            }
        }
    }

    private void setTile(int x, int y, String tileInfo){
        if(!tileInfo.toLowerCase().equals("nnn")){
            String type = String.valueOf(tileInfo.charAt(0));
            String subType = String.valueOf(tileInfo.charAt(1));
            String rotation = String.valueOf(tileInfo.charAt(2));
            type = "TILE_" + type;
            String tile = (type+"_"+subType);
            int rot = Integer.parseInt(rotation);
            mapTiles[x][y].setTexture(tile, rot);
            mapTiles[x][y].setType(TileEngine.stringToType(tile));
        }
    }

}
