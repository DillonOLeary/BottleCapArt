import processing.core.PGraphics;
import processing.core.PImage;

/**
 * This class is meant to provide the methods in a PApplet object without
 * having to pass around the reference to the the BottleCapVisualizer
 */
public class ColorUtils {
    PGraphics g;
    private final int HSB = 3;
    public ColorUtils() {
        g = new PGraphics();
        g.colorMode(HSB, 100);
    }
    public int brightnessOfImage(PImage img, int x, int y) {
        return (int) g.brightness(img.get(x, y));
    }

    public int hueOfImage(PImage img, int x, int y) {
        return (int) g.hue(img.get(x, y));
    }

    public int saturationOfImage(PImage img, int x, int y) {
        return (int) g.saturation(img.get(x, y));
    }
}
