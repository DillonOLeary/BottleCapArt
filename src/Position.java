import processing.core.PImage;

import java.util.ArrayList;

public class Position extends ColorUnit {
    private BottleCap cap;
    private ColorFromPicture realColor;
    private int x_leftCorner;
    private int y_topCorner;

    public Position(int x_leftCorner, int y_topCorner) {
        super(0, 0, 0, "default");
        this.x_leftCorner = x_leftCorner;
        this.y_topCorner = y_topCorner;
    }

    public static ArrayList<Position> setupPositions(PImage img, ArrayList<BottleTopType> capList, float percentCapUsage) {
        int totalCaps = 0;
        for (BottleTopType type : capList) {
            totalCaps += type.getNumCaps();
        }
        int numCapsInPicture = (int) (totalCaps * percentCapUsage);
        double radius = Math.sqrt((img.width * img.height) / (2 * Math.sqrt(3) * ((double) numCapsInPicture)));
        // calculate the xScalar and yScalar
        double xScalar = 2 * radius;
        double yScalar = Math.sqrt(3) * radius;
        // calculate the oddRowOffset
        double oddRowOffset = radius;
        // calculate the numRows and numColumns
        // loop through picture based on

//        int bottleCapHeightNum = ((int)((float)img.height / .9)) / BottleCapVisualizer.BOTTLE_CAP_DIAMETER;
//        int bottleCapWidthNum = img.width / BottleCapVisualizer.BOTTLE_CAP_DIAMETER;
//        int total_count = 0;
//        // TODO add another priority queue with each pixel, then compare each image pixel circle with the caps
//        for (int xPosition=0; xPosition<bottleCapWidthNum; xPosition++)
//        {
//            for (int yPosition=0; yPosition<bottleCapHeightNum; yPosition++)
//            {
//                int alternator;
//                if ( yPosition % 2 == 0 ) alternator = BottleCapVisualizer.BOTTLE_CAP_DIAMETER / 2;
//                else alternator = 0;
//
//                // make sure the far right column isn't printed
//                if ((xPosition == bottleCapWidthNum - 1) && alternator > 0)
//                    continue;
//                int yBottleCapPosition = (int)((.85)*((float)(yPosition*BottleCapVisualizer.BOTTLE_CAP_DIAMETER + (BottleCapVisualizer.BOTTLE_CAP_DIAMETER/2))))
//                        + (BottleCapVisualizer.BOTTLE_CAP_DIAMETER/2);
//                int xBottleCapPosition = xPosition*BottleCapVisualizer.BOTTLE_CAP_DIAMETER + (BottleCapVisualizer.BOTTLE_CAP_DIAMETER/2) + alternator
//                        + (BottleCapVisualizer.BOTTLE_CAP_DIAMETER/2);
//
//                addToRealColorQueue(yBottleCapPosition, xBottleCapPosition, img);
//                total_count++;
//            }
//        }
//        while ( !realColorQueue.isEmpty() )
//        {
//            printBestBottleCap();
//        }
//        if (notEnoughCapsForPicture)
//            System.out.println("Not enough bottle caps to make picture!!");
//        System.out.println("Total number of caps in picture: " + total_count)
        return new ArrayList<>();
    }

    @Override
    public int compareTo(Object o) {
        // FIXME unimplemented
        return 0;
    }
}
