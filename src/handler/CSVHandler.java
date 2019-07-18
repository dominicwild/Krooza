package handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class created for the purposes of fetching data from a CSV. - Dominic Wild
 * @author Will Fantom
 */
public final class CSVHandler {

    private static final String FILE_EXT = ".csv",
                                COMMENT_TAG = "#";

    /**
     * Reads a csv file as a list of stings given line by line of the file
     *
     * @param path  the file path of the CSV
     * @return
     */
    public static ArrayList<String> readCSV(String path){
        if(!csvCheck(path))
            return null;

        ArrayList<String> file = new ArrayList<String>();

        try(BufferedReader r = new BufferedReader(new FileReader(path))){
            String l = "";
            while((l = r.readLine()) != null){
                if(!l.startsWith(COMMENT_TAG))
                    file.add(l);
            }
            r.close();
        } catch(IOException e){
            System.out.println("<!> Error: file '" + path + "' is not readable or does not exist");
            return null;
        }

        return file;

    }

    /**
     * Writes an array of strings to a CSV file line by line
     *
     * @param path
     * @param file
     */
    public static void writeCSV(String path, ArrayList<String> file){

        if(csvCheck(path)){

            try(FileWriter fw = new FileWriter(path)){
                for(String l : file)
                    fw.append(l + "\n");
                fw.close();
            } catch(IOException e){
                System.out.println("<!> Error: file '" + path + "' is not writable or does not exist");
            }

        }

    }

    private static boolean csvCheck(String path){
        if(!path.endsWith(FILE_EXT)){
            System.out.println("<!> Error: file '" + path + "' is not a CSV file");
            return false;
        }
        return true;
    }

}
