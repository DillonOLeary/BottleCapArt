import processing.core.PImage;

public class BottleCap extends ColorUnit {
    private String name;

    public String getName() {
        return name;
    }

    public BottleCap(PImage img) {
        super(img);
        this.name = img.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ColorUnit)
            return compareBottleCapTypes((ColorUnit) o);
        else {
            throw new RuntimeException();
        }
    }

    private int compareBottleCapTypes(ColorUnit bC) {
        int hueComparison = this.hue - bC.hue;
        int saturationComparison = this.saturation - bC.saturation;
        int brightnessComparison = this.brightness - bC.brightness;

        return brightnessComparison;
//        return saturationComparison;

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BottleCap)
            return ((BottleCap) o).getName().equals(this.name);
        else
            return false;
    }
}
