import processing.core.PImage;

import java.util.ArrayList;

public class Position {
    private BottleCap cap;
    private ColorFromPicture realColor;
    int x_leftCorner;
    int y_topCorner;
    int radius;

    public Position(int x_leftCorner, int y_topCorner, int radius) {
//        setRealColor(colorUnit.hue, colorUnit.saturation, colorUnit.brightness);
        this.x_leftCorner = x_leftCorner;
        this.y_topCorner = y_topCorner;
        this.radius = radius;
        cap = null;
        realColor = null;
    }

    public void setRealColor(int hue, int saturation, int brightness) {
        if (realColor != null)
            throw new RuntimeException("You can't set the color in the picture twice! That doesn't make sense!");
        realColor = new ColorFromPicture(hue, saturation, brightness, "PICTURE_COLOR");
    }
    // IDK which one to ues
    private void setRealColor(ColorFromPicture col) {
        if (realColor != null)
            throw new RuntimeException("You can't set the color in the picture twice! That doesn't make sense!");
        realColor = col;
    }

    /**
     *
     * @param cap the cap to be placed in the spot
     * @return the cap or null if there was no cap there
     */
    public BottleCap replaceCap(BottleCap cap) {
        if (cap == null) {
            setCap(cap);
            return null;
        }
        else {
            BottleCap oldCap = this.cap;
            this.cap = cap;
            return oldCap;
        }
    }

    public Position copyPosition() {
        Position newPos = new Position(this.x_leftCorner, this.y_topCorner, this.radius);
        if (this.realColor != null)
            newPos.setRealColor(this.realColor.hue, this.realColor.saturation, this.realColor.brightness);
        if (this.cap != null)
            newPos.replaceCap(new BottleCap(this.cap.hue,this.cap.saturation,this.cap.brightness, this.cap.getName()));
        return newPos;
    }

    private void setCap(BottleCap cap) {
        this.cap = cap;
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
        Position currPosition = new Position(0,0, (int) radius);
        boolean isOddRow = false;
        ArrayList<Position> positionList = new ArrayList<>();
        while (currPosition.y_topCorner <= img.height - yScalar) {
            while (currPosition.x_leftCorner <= img.width - xScalar) {
                positionList.add(new Position(currPosition.x_leftCorner, currPosition.y_topCorner, (int) radius));
                currPosition.x_leftCorner += xScalar;
            }
            currPosition.y_topCorner += yScalar;
            isOddRow = !isOddRow;
            currPosition.x_leftCorner = (isOddRow)? oddRowOffset : 0;
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

    public int getHue() {
        if (cap != null)
            return cap.hue;
        else if (realColor != null)
            return realColor.hue;
        else
            throw new RuntimeException("You called hue with out providing a cap of a real color!");
    }

    public int getSaturation() {
        if (cap != null)
            return cap.saturation;
        else if (realColor != null)
            return realColor.saturation;
        else
            throw new RuntimeException("You called saturation with out providing a cap of a real color!");
    }

    public int getBrightness() {
        if (cap != null)
            return cap.brightness;
        else if (realColor != null)
            return realColor.brightness;
        else
            throw new RuntimeException("You called brightness with out providing a cap of a real color!");
    }

    public BottleCap getCap() {
        return cap;
    }

    public double evaluatePos() {
        // TODO what does the evaluation look like?
        return Math.abs(realColor.brightness - cap.brightness);
//        return Math.abs(realColor.hue - cap.hue);
    }

    @Override
    public String toString() {
        return "Cap is " + cap.toString() + " X: " + x_leftCorner + " Y: " + y_topCorner;
    }
}
