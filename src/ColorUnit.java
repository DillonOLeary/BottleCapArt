import processing.core.PImage;
import java.util.ArrayList;

abstract public class ColorUnit implements Comparable
{
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

    public ColorUnit(BottleCapVisualizer visual, PImage circleArea) {
        readCircleInPicture(visual, circleArea);
    }
    private void readCircleInPicture(BottleCapVisualizer visual, PImage circleArea) {
        selectPixelsWithinColorUnit = new ArrayList<>();
        double radius = circleArea.width/2;
        int numRows = 10;
        int numCols = numRows;
        int centerX = circleArea.width/2;
        int centerY = circleArea.height/2;

        for (int row = circleArea.height/numRows/2; row < circleArea.height; row += circleArea.height/numRows) {
            for (int col = circleArea.width/numCols/2; col < circleArea.width; col += circleArea.width/numCols) {
                if (distanceToCenter(centerX,centerY,col,row) < radius) {
                    int[] hsb =  {visual.hueOfImage(circleArea, col, row),
                            visual.saturationOfImage(circleArea, col, row),
                            visual.brightnessOfImage(circleArea, col, row)};
                    selectPixelsWithinColorUnit.add(hsb);
                }
            }
        }
        calcMeansAndStdDev();
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

    public double distanceToCenter(int centerX, int centerY, int currX, int currY) {
        return Math.sqrt(Math.pow(centerY-currY,2) + Math.pow(centerX-currX,2));
    }
    public ColorUnit(int hue, int saturation, int brightness)
    {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }

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