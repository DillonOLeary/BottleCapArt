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
        double radius = findRadius(numCapsInPicture, img.height, img.width);

        //        double radius = 1.81583407312 Sqrt[(1/k) (m (n/(2 Sqrt[3])) - m/(4 Sqrt[3]));
        //        // calculate the xScalar and yScalar
        //        double xScalar = 2 * radius;
        //        double yScalar = Math.sqrt(3) * radius;
        //        // calculate the oddRowOffset
        //        double oddRowOffset = radius;
        //        // calculate the numRows and numColumns
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

    private static double findWidth(int numCaps, double radius, double hToWRatio) {
        int capsInPicture = 0;
        double rectangleHeight = radius * Math.sqrt(3);
        double rectangleWidth = 2 * radius;
        double width = (hToWRatio > 1)? radius : radius * 1 / hToWRatio;
        double height = width * hToWRatio;

        // Current loop requires at least a like 10 or so caps
        while (capsInPicture <= numCaps) {
            double dToNextX = width / rectangleWidth % 1;
            double dToNextY = height / rectangleHeight % 1;
            width += (dToNextY * hToWRatio > dToNextX)? dToNextY * (1/hToWRatio) : dToNextX;
            height = width * hToWRatio;
            capsInPicture = findNumCaps(radius, height, width);
        }

        return width;
    }

    private static double findRadius(int numCaps, double height, double width) {
        double capRadius = 1.0;
        double widthGivenRatio = findWidth(numCaps, capRadius, height/width);
        double widthFactor = width/widthGivenRatio;
        return capRadius * widthFactor;
    }

    private static int findNumCaps(double radius, double height, double width) {
        double rectangleHeight = radius * Math.sqrt(3);
        double rectangleWidth = 2 * radius;
        int capsIfEachRowEqual = ((int) ( height / rectangleHeight)) *
                ((int) (((width) - radius) / rectangleWidth));
        int capsIfeachRowUnequal = ((int) ( height / rectangleHeight)) *
                ((int) (width / rectangleWidth) - (int) (height / rectangleHeight));
        return Math.max(capsIfEachRowEqual, capsIfeachRowUnequal);
    }

    @Override
    public int compareTo(Object o) {
        // FIXME unimplemented
        return 0;
    }
}
