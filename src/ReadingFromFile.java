import processing.core.PApplet;
import processing.core.PImage;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadingFromFile
{

    public static final String CAP_IMAGES_FOLDER = "CapImages/";

    public static void addTopsFromFile(String filename, ArrayList<BottleTopType> list, PApplet applet)
    {
        try {
            readFile(filename, list, applet);
        } catch (IOException e) {
            System.out.println("There was an error reading the file");
        }
    }

    private static void readFile(String filename, ArrayList<BottleTopType> list, PApplet applet) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line = br.readLine();
            int numCaps;
            PImage img;
            String[] lineElements;

            while (line != null) {
                lineElements = line.split(",");
                numCaps = Integer.parseInt(lineElements[0]);
                img = applet.loadImage(CAP_IMAGES_FOLDER + lineElements[1]);
                BottleCap col = new BottleCap(img);
                list.add( new BottleTopType(numCaps, col) );
                line = br.readLine();
                if (line == null || line.contains("/"))
                    break;
            }
        } finally {
            br.close();
        }
    }


}
