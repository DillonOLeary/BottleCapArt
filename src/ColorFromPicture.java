import processing.core.PImage;

import java.util.ArrayList;

public class ColorFromPicture extends ColorUnit
{
    String name;
    @Override
    public int compareTo(Object o)
    {
        if (o instanceof ColorUnit)
            return compareBottleCapTypes((ColorUnit) o);
        else
        {
            throw new RuntimeException();
        }
    }
    private int compareBottleCapTypes(ColorUnit bC)
    {
        // sort by brightness for now
//        int hueComparison = ((Integer) this.hue).compareTo(bC.hue);
//        int saturationComparison = ((Integer) this.saturation).compareTo(bC.saturation);
//        int brightnessComparison = ((Integer) this.brightness).compareTo(bC.brightness);
        int hueComparison = this.hue - bC.hue;
        int saturationComparison = this.saturation - bC.saturation;
        int brightnessComparison = this.brightness - bC.brightness;
//        System.out.println("hueComparison: " + hueComparison + " this - that hue: " + (this.hue - bC.hue) + " saturationComparison: " +
//                saturationComparison + " brightnessComparison: " + brightnessComparison);

//        return 3*brightnessComparison + saturationComparison + hueComparison;
//        return 0;
//        return brightnessComparison;
        return brightnessComparison;
//        return saturationComparison;

    }
    ColorFromPicture(PImage area, String name)
    {
        super(area);
        this.name = name;
    }
}
