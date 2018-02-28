import processing.core.PApplet;
import processing.core.PImage;
import java.util.PriorityQueue;
import java.util.ArrayList;
//import java.util.TreeMap;

public class BottleCapVisualizer extends PApplet
{
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 750;
    private static final int BOTTLE_CAP_DIAMETER = 25;
    private static ArrayList<BottleTopType> capList;
    private static PriorityQueue<ColorUnit> realColorQueue;
    private static boolean notEnoughCapsForPicture;

    public static void main(String args[])
    {
        System.out.println("1 compare to 2: "  + ((Integer) 1).compareTo(2));
        PriorityQueue<Integer> test = new PriorityQueue<Integer>();
        test.add(9);
        test.add(5);
        test.add(6);
        System.out.println(test);
        notEnoughCapsForPicture = false;
        capList = new ArrayList<BottleTopType>();
        ReadingFromFile.addTopsFromFile("testBottleCapInput.txt", capList);
        realColorQueue = new PriorityQueue<ColorUnit>();
        PApplet.main("BottleCapVisualizer");
    }
    public void settings()
    {
        size(WINDOW_WIDTH,WINDOW_HEIGHT);
    }
    public void setup()
    {
        colorMode(HSB, 100);
        background(100);
        PImage img = loadImage("logo.jpg");

        resizePicture(img);
        drawPicture(img);
    }
    private void resizePicture(PImage img)
    {
        while( (img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT) )
        {
            img.resize(img.width - 50, img.height - 50);
        }
        while ( !((img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT))
                && (img.width < WINDOW_WIDTH - 75 || img.height < WINDOW_HEIGHT - 75) )
        {
            img.resize(img.width + 50, img.height + 50);
        }
    }
    private void drawPicture(PImage img)
    {
        int bottleCapHeightNum = ((int)((float)img.height / .9)) / BOTTLE_CAP_DIAMETER;
        int bottleCapWidthNum = img.width / BOTTLE_CAP_DIAMETER;
        int total_count = 0;
        // TODO add another priority queue with each pixel, then compare each image pixel circle with the caps
        for (int xPosition=0; xPosition<bottleCapWidthNum; xPosition++)
        {
            for (int yPosition=0; yPosition<bottleCapHeightNum; yPosition++)
            {
                int alternator;
                if ( yPosition % 2 == 0 ) alternator = BOTTLE_CAP_DIAMETER / 2;
                else alternator = 0;

                // make sure the far right column isn't printed
                if ((xPosition == bottleCapWidthNum - 1) && alternator > 0)
                    continue;
                int yBottleCapPosition = (int)((.85)*((float)(yPosition*BOTTLE_CAP_DIAMETER + (BOTTLE_CAP_DIAMETER/2))))
                        + (BOTTLE_CAP_DIAMETER/2);
                int xBottleCapPosition = xPosition*BOTTLE_CAP_DIAMETER + (BOTTLE_CAP_DIAMETER/2) + alternator
                        + (BOTTLE_CAP_DIAMETER/2);

                addToRealColorQueue(yBottleCapPosition, xBottleCapPosition, img);
                total_count++;
            }
        }
        while ( !realColorQueue.isEmpty() )
        {
            printBestBottleCap();
        }
        if (notEnoughCapsForPicture)
            System.out.println("Not enough bottle caps to make picture!!");
        System.out.println("Total number of caps in picture: " + total_count);
    }
    private void addToRealColorQueue(int yBottleCapPosition, int xBottleCapPosition, PImage img)
    {

        int xPositionOfMiddle = xBottleCapPosition + (BOTTLE_CAP_DIAMETER/2);
        int yPositionOfMiddle = yBottleCapPosition + (BOTTLE_CAP_DIAMETER/2);

        float ImgPixColorHue = hue(img.get(xPositionOfMiddle,yPositionOfMiddle));
        float ImgPixColorSaturation = saturation(img.get(xPositionOfMiddle,yPositionOfMiddle));
        float ImgPixColorBrightness = brightness(img.get(xPositionOfMiddle,yPositionOfMiddle));


        realColorQueue.add(new TrueColor((int)ImgPixColorHue, (int)ImgPixColorSaturation,
                (int)ImgPixColorBrightness, "real img pix",xBottleCapPosition , yBottleCapPosition));
    }
    private void printBestBottleCap()
    {
        TrueColor realCap = (TrueColor) realColorQueue.remove();
        ColorUnit cap = realCap;
        if ( !capList.isEmpty() )
        {
            cap = getClosestColor(realCap);
//            ColorUnit upper = capList.ceilingKey(realCap);
//            ColorUnit lower = capList.floorKey(realCap);
//            if ( upper == null )
//            {
//                closest = lower;
//            } else if ( lower == null) {
//                closest = upper;
//            } else {
//
//                if (Math.abs(upper.compareTo(realCap)) <= Math.abs(lower.compareTo(realCap)) )
//                    closest = upper;
//                else
//                    closest = lower;
//            }
//            try {
//                cap = capList.get(closest).removeACap();
//            } catch (RuntimeException e) {
//                capList.remove(closest);
//            }
        } else {
            cap = realCap;
            notEnoughCapsForPicture = true;
        }
        fill(cap.getHue(), cap.getSaturation(), cap.getBrightness());
        stroke(cap.getHue(), cap.getSaturation(), cap.getBrightness());
        ellipse(realCap.getxPosition(), realCap.getyPosition(), BOTTLE_CAP_DIAMETER, BOTTLE_CAP_DIAMETER);
    }

    private ColorUnit getClosestColor(TrueColor realCap) {
        BottleTopType closest = null;
        for ( BottleTopType capType : capList)
        {
            if ( capType.getCOLOR_INFO().compareTo(closest.getCOLOR_INFO()) <= 0) //FIXME this may not be right
            {
                closest = capType;
                try {
                    capType.removeACap();
                } catch (RuntimeException e) {
                    capList.remove(closest);
                }
            }
        }

        return closest.getCOLOR_INFO();
    }

}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
