public class BottleCap extends ColorUnit
{
    private String name;

    public String getName() {
        return name;
    }

    public BottleCap(int hue, int saturation, int brightness, String name) {
        super(hue, saturation, brightness);
        this.name = name;
    }

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
        int hueComparison = this.hue - bC.hue;
        int saturationComparison = this.saturation - bC.saturation;
        int brightnessComparison = this.brightness - bC.brightness;

        return brightnessComparison;
//        return saturationComparison;

    }
}
