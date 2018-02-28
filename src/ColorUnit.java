
abstract public class ColorUnit implements Comparable
{
    @Override
    public String toString()
    {
        return hue + "," + saturation + "," + brightness + "," + "," + name;
    }
//    @Override
//    public int compareTo(Object o)
//    {
//        if (o instanceof ColorUnit)
//            return compareBottleCapTypes((ColorUnit) o);
//        else
//        {
//            throw new RuntimeException();
//        }
//    }
//    private int compareBottleCapTypes(ColorUnit bC)
//    {
//        int hueComparison = this.hue - bC.hue;
//        int saturationComparison = this.saturation - bC.saturation;
//        int brightnessComparison = this.brightness - bC.brightness;
//        return brightnessComparison;
//    }

    protected int hue;
    protected int saturation;
    protected int brightness;
    private String name;

    public ColorUnit(int hue, int saturation, int brightness, String name)
    {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.name = name;
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

    public String getName()
    {
        return name;
    }

}