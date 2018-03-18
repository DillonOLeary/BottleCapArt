import processing.core.PImage;

import java.util.ArrayList;

public class Position extends ColorUnit {
    private BottleCap cap;
    private ColorFromPicture realColor;
    int x_leftCorner;
    int y_topCorner;

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
        double radius = findRadius(numCapsInPicture, (double) img.height, (double) img.width);
        // calculate the xScalar and yScalar
        double xScalar = 2 * radius;
        double yScalar = Math.sqrt(3) * radius;
        // calculate the oddRowOffset
        // THIS WILL MAKE THE ROW OFFSET LESS ACCURATE
        int oddRowOffset = (int) Math.round(radius);
        Position currPosition = new Position(0,0);
        boolean isOddRow = false;
        ArrayList<Position> positionList = new ArrayList<>();
        while (currPosition.y_topCorner <= img.height - yScalar) {
            while (currPosition.x_leftCorner <= img.width - xScalar) {
                positionList.add(new Position(currPosition.x_leftCorner, currPosition.y_topCorner));
                currPosition.x_leftCorner += xScalar;
            }
            currPosition.y_topCorner += yScalar;
            currPosition.x_leftCorner = (isOddRow)? oddRowOffset : 0;
            isOddRow = !isOddRow;
        }
        return positionList;
    }

    private static double findRadius(int numCaps, double height, double width) {
        double capRadius = 1.0;
        double widthGivenRatio = findWidth(numCaps, capRadius, height/width);
        double widthFactor = width/widthGivenRatio;
        return capRadius * widthFactor;
    }

    private static double findWidth(int numCaps, double radius, double hToWRatio) {
        int capsInPicture = 0;
        double rectangleHeight = radius * Math.sqrt(3);
        double rectangleWidth = 2 * radius;
        double width = (hToWRatio > 1)? radius : radius / hToWRatio;
        double height = width * hToWRatio;
        double previousWidth = width;

        // Current loop requires at least a like 10 or so caps
        if (numCaps < 15)
            throw new IllegalArgumentException("C'mon, only " + numCaps + " bottle tops!?");
        while (capsInPicture <= numCaps) {
            previousWidth = width;
            double dToNextX = width / rectangleWidth % 1;
            double dToNextY = height / rectangleHeight % 1;
            width += (dToNextY * hToWRatio > dToNextX)? dToNextY / hToWRatio : dToNextX;
            height = width * hToWRatio;
            capsInPicture = findNumCaps(radius, height, width);
        }
        return previousWidth;
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
