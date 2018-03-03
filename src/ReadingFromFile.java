import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadingFromFile
{
    public static void addTopsFromFile(String filename, ArrayList<BottleTopType> list)
    {
        try {
            readFile(filename, list);
        } catch (IOException e) {
            System.out.println("There was an error reading the file");
        }
    }

    private static void readFile(String filename, ArrayList<BottleTopType> list) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line = br.readLine();
            int hue;
            int saturation;
            int brightness;
            int numCaps;
            String capsName;
            String[] lineElements;

            while (line != null) {
                lineElements = line.split(",");
                hue = Integer.parseInt(lineElements[0]);
                saturation = Integer.parseInt(lineElements[1]);
                brightness = Integer.parseInt(lineElements[2]);
                numCaps = Integer.parseInt(lineElements[3]);
                capsName = lineElements[4];
                BottleCap col = new BottleCap(hue, saturation, brightness, capsName);
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
