import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

abstract public class ColorUnit implements Comparable
{
    static PApplet app = new PApplet();
    PImage image;
    @Override
    public String toString()
    {
        return hue + "," + saturation + "," + brightness;
    }

    // these are the mean values
    protected int hue;
    protected int saturation;
    protected int brightness;

    // Range from 0-50 I think
    protected double hueStandardDeviation;
    protected double saturationStandardDeviation;
    protected double brightnessStandardDeviation;

    // Array of hsb within each point in the colorUnit
    protected ArrayList<int[]> selectPixelsWithinColorUnit;

    public ColorUnit(PImage circleArea) {
        image = circleArea;
    }

    private void calcMeansAndStdDev() {
        double hueSum = 0;
        double saturationSum = 0;
        double brightnessSum = 0;
        double hueNum = 0.0;
        double saturationNum = 0.0;
        double brightnessNum = 0.0;

        for (int[] i : selectPixelsWithinColorUnit) {
            hueSum += i[0];
            saturationSum += i[1];
            brightnessSum += i[2];
        }

        hue = (int) (hueSum/selectPixelsWithinColorUnit.size());
        saturation = (int) (saturationSum/selectPixelsWithinColorUnit.size());
        brightness = (int) (brightnessSum/selectPixelsWithinColorUnit.size());

        for (int[] i : selectPixelsWithinColorUnit) {
            hueNum += Math.pow((double) (i[0] - hue), 2);
            saturationNum += Math.pow((double) (i[1] - saturation), 2);
            brightnessNum += Math.pow((double) (i[2] - brightness), 2);
        }
        hueStandardDeviation = Math.sqrt(hueNum/selectPixelsWithinColorUnit.size());
        saturationStandardDeviation = Math.sqrt(saturationNum/selectPixelsWithinColorUnit.size());
        brightnessStandardDeviation = Math.sqrt(brightnessNum/selectPixelsWithinColorUnit.size());
    }

    /**
     * This method reads in the vector of hsb values in a picture.
     * It assumes you want to read in the values in a cirlce. Values
     * Outside of the radius of the circle are ignored. This applies
     * To both the bottle cap images and the true image sections
     *
     * @param circleArea the image that needs to be read
     */
    private void readCircleInPicture(PImage circleArea) {
        selectPixelsWithinColorUnit = new ArrayList<>();
        double radius = circleArea.width/2;
        int numRows = 10;
        int numCols = numRows;
        int centerX = circleArea.width/2;
        int centerY = circleArea.height/2;

        for (int row = circleArea.height/numRows/2; row < circleArea.height; row += circleArea.height/numRows) {
            for (int col = circleArea.width/numCols/2; col < circleArea.width; col += circleArea.width/numCols) {
                if (distanceToCenter(centerX,centerY,col,row) < radius) {
                    int[] hsb =  {hueOfImage(circleArea, col, row),
                            saturationOfImage(circleArea, col, row),
                            brightnessOfImage(circleArea, col, row)};
                    selectPixelsWithinColorUnit.add(hsb);
                }
            }
        }
    }

    public int brightnessOfImage(PImage img, int x, int y) {
        return (int) app.brightness(img.get(x, y));
    }
    public int hueOfImage(PImage img, int x, int y) {
        return (int) app.hue(img.get(x, y));
    }
    public int saturationOfImage(PImage img, int x, int y) {
        return (int) app.saturation(img.get(x, y));
    }

    public double distanceToCenter(int centerX, int centerY, int currX, int currY) {
        return Math.sqrt(Math.pow(centerY-currY,2) + Math.pow(centerX-currX,2));
    }
//    public ColorUnit(ArrayList<int[]> selectPixelsWithinColorUnit)
//    {
//        this.selectPixelsWithinColorUnit = selectPixelsWithinColorUnit;
//        calcMeansAndStdDev();
//    }

    public int getHue()
    {
        return hue;
    }

    public int getSaturation()
    {
        return saturation;
    }

    public int getBrightness()
    {
        return brightness;
    }



}