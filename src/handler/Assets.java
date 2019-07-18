package handler;

import org.jsfml.audio.Music;
import org.jsfml.graphics.Texture;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class used to easily fetch game images and sounds from within the games
 * content folder. Making it easy to not have to handle with internal folder
 * structure. - Dominic Wild
 *
 * @author Will Fantom
 */
public final class Assets {

    //FOLDERS
    private static File IMAGE_FILE = new File("./Content/Assets/Images"),
            SOUND_FILE = new File("./Content/Assets/Sounds");

    //EXTENSIONS
    private static String IMG_EXT = ".png",
            SND_EXT = ".wav";

    //ASSETS
    public static HashMap<String, Texture> TEXTURE;
    public static HashMap<String, Music> MUSIC;

    public Assets(){

        TEXTURE = new HashMap<String, Texture>();
        MUSIC = new HashMap<String, Music>();

        this.loadImages(IMAGE_FILE);
        this.loadSounds(SOUND_FILE);

    }

    private void loadImages(File file){

        //Load Files
        File[] image = file.listFiles();

        //Load Names
        String[] imageName = file.list();

        //Load Textures
        for(int i=0 ; i<image.length ; i++){
            if(image[i].isDirectory()){
                this.loadImages(image[i]);
            } else if(imageName[i].endsWith(IMG_EXT)){
                TEXTURE.put(imageName[i].substring(0, imageName[i].length()-4), new Texture());
                try {
                    TEXTURE.get(imageName[i].substring(0, imageName[i].length()-4)).loadFromFile(image[i].toPath());
                    TEXTURE.get(imageName[i].substring(0, imageName[i].length()-4)).setSmooth(true);
                } catch (IOException e) {
                    System.out.println("<!> Error: Could not load a texture");
                }
            }
        }

    }

    private void loadSounds(File file){

        //Load Files
        File[] sound = file.listFiles();

        //Load Names
        String[] soundName = file.list();

        //Load Sounds
        for(int i=0 ; i<sound.length ; i++){
            if(sound[i].isDirectory()){
                this.loadSounds(sound[i]);
            } else if(soundName[i].endsWith(SND_EXT) || soundName[i].endsWith(".ogg")){
                MUSIC.put(soundName[i].substring(0, soundName[i].length()-4), new Music());
                try {
                    MUSIC.get(soundName[i].substring(0, soundName[i].length()-4)).openFromFile(sound[i].toPath());
                } catch (IOException e) {
                    System.out.println("<!> Error: Could not load a sound");
                }
            }
        }

    }

    /**
     * Gets a loaded texture given a file name
     *
     * @param name  name of the texture to load
     * @return
     */
    public static Texture getTexture(String name){

        if(TEXTURE.get(name) != null){
            return TEXTURE.get(name);
        } else {
            System.out.println("<!> Error: No texture found with name " + name);
            return new Texture();
        }

    }

    /**
     * Gets a sound given its file name
     *
     * @param name  the sounds file name
     * @return
     */
    public static Music getSound(String name){

        if(MUSIC.get(name) != null){
            return MUSIC.get(name);
        } else {
            System.out.println("<!> Error: No sound found with name " + name);
            return new Music();
        }

    }

    /**
     * Dumps a given texture from the TEXTURE hashmap
     * @param name  the texture name to dump
     */
    public static void dumpTexture(String name){

        if(TEXTURE.get(name) != null){
            TEXTURE.remove(name);
        } else {
            System.out.println("<!> Error: No texture found to dump with name " + name);
        }

    }

}
