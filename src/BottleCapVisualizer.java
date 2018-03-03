import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
//import java.util.TreeMap;

public class BottleCapVisualizer extends PApplet
{
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;
    public static final int BOTTLE_CAP_DIAMETER = 25;
    private static ArrayList<BottleTopType> capList;
//    private static PriorityQueue<ColorUnit> realColorQueue;
//    private static boolean notEnoughCapsForPicture;
    private static ArrayList<Position> positions;
    private static float percentCapUsage;

    public static void main(String args[])
    {
        if (args.length == 0)
            throw new IllegalArgumentException("Add argument percent cap usage, float between 0 and 1");
        percentCapUsage = Float.parseFloat(args[0]);

        capList = new ArrayList<>();
        ReadingFromFile.addTopsFromFile("testBottleCapInput.txt", capList);
//        realColorQueue = new PriorityQueue<ColorUnit>();
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
        positions = Position.setupPositions(img, capList, percentCapUsage);
        drawPicture(img);
    }
    public void draw()
    {
        // Probably not going to use, although it would be nice to show the current progress of the algorithm
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
        SimulatedAnnealing.SimulatedAnnealingAlgo(positions);
//        for (Position pos : positions) {
//            fill(pos.getHue(), pos.getSaturation(), pos.getBrightness());
//            stroke(pos.getHue(), pos.getSaturation(), pos.getBrightness());
//            ellipse(pos.getxPosition(), pos.getyPosition(), BOTTLE_CAP_DIAMETER, BOTTLE_CAP_DIAMETER);
//        }

    }
//    private void addToRealColorQueue(int yBottleCapPosition, int xBottleCapPosition, PImage img)
//    {
//
//        int xPositionOfMiddle = xBottleCapPosition + (BOTTLE_CAP_DIAMETER/2);
//        int yPositionOfMiddle = yBottleCapPosition + (BOTTLE_CAP_DIAMETER/2);
//
//        float ImgPixColorHue = hue(img.get(xPositionOfMiddle,yPositionOfMiddle));
//        float ImgPixColorSaturation = saturation(img.get(xPositionOfMiddle,yPositionOfMiddle));
//        float ImgPixColorBrightness = brightness(img.get(xPositionOfMiddle,yPositionOfMiddle));
//
//
//        realColorQueue.add(new ColorFromPicture((int)ImgPixColorHue, (int)ImgPixColorSaturation,
//                (int)ImgPixColorBrightness, "real img pix",xBottleCapPosition , yBottleCapPosition));
//    }
//    private void printBestBottleCap()
//    {
//        ColorFromPicture realCap = (ColorFromPicture) realColorQueue.remove();
//        ColorUnit cap = realCap;
//        if ( !capList.isEmpty() )
//        {
//            cap = getClosestColor(realCap);
////            ColorUnit upper = capList.ceilingKey(realCap);
////            ColorUnit lower = capList.floorKey(realCap);
////            if ( upper == null )
////            {
////                closest = lower;
////            } else if ( lower == null) {
////                closest = upper;
////            } else {
////
////                if (Math.abs(upper.compareTo(realCap)) <= Math.abs(lower.compareTo(realCap)) )
////                    closest = upper;
////                else
////                    closest = lower;
////            }
////            try {
////                cap = capList.get(closest).removeACap();
////            } catch (RuntimeException e) {
////                capList.remove(closest);
////            }
//        } else {
//            cap = realCap;
//            notEnoughCapsForPicture = true;
//        }
//        fill(cap.getHue(), cap.getSaturation(), cap.getBrightness());
//        stroke(cap.getHue(), cap.getSaturation(), cap.getBrightness());
//        ellipse(realCap.getxPosition(), realCap.getyPosition(), BOTTLE_CAP_DIAMETER, BOTTLE_CAP_DIAMETER);
//    }

//    private ColorUnit getClosestColor(ColorFromPicture realCap) {
//        BottleTopType closest = null;
//        for ( BottleTopType capType : capList)
//        {
//            if ( capType.getCOLOR_INFO().compareTo(closest.getCOLOR_INFO()) <= 0) //FIXME this may not be right
//            {
//                closest = capType;
//                try {
//                    capType.removeACap();
//                } catch (RuntimeException e) {
//                    capList.remove(closest);
//                }
//            }
//        }
//
//        return closest.getCOLOR_INFO();
//    }

}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
