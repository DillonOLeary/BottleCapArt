import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class BottleCapVisualizer extends PApplet {
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;

    private static ArrayList<BottleTopType> capList;
    private static ArrayList<Position> positions;
    public static float percentCapUsage;
    private static PImage img;

    public static void main(String args[]) {
        if (args.length == 0)
            throw new IllegalArgumentException("Add argument percent cap usage, float between 0 and 1");
        percentCapUsage = Float.parseFloat(args[0]);

        capList = new ArrayList<>();
        ReadingFromFile.addTopsFromFile("testBottleCapInput.txt", capList);
//        realColorQueue = new PriorityQueue<ColorUnit>();
        PApplet.main("BottleCapVisualizer");
    }
    public void settings()
    {
        size(WINDOW_WIDTH,WINDOW_HEIGHT);
    }
    public void setup() {
        colorMode(HSB, 100);
        ellipseMode(CORNER);
        background(100);
        img = loadImage("state_street.jpg");

        resizePicture();
        positions = Position.setupPositions(img, capList, percentCapUsage);
        // Set all the real colors
        for (Position pos : positions) {
            pos.setRealColor(this.hueOfImage(pos), this.saturationOfImage(pos), this.brightnessOfImage(pos));
        }
        positions = LocalSearch.hillClimbing(positions, capList);
//        LocalSearch.SimulatedAnnealingAlgo(positions, capList);
        drawPicture();
        System.out.println("The width and height: " + img.width + " x " + img.height);
    }
    public void draw()
    {
        // Probably not going to use, although it would be nice to show the current progress of the algorithm
    }
    private void resizePicture()
    {
        while( (img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT) )
        {
            img.resize(img.width - 50, img.height - 50);
        }
        while ( !((img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT))
                && (img.width < WINDOW_WIDTH - 75 || img.height < WINDOW_HEIGHT - 75) )
        {
            img.resize(img.width + 50, img.height + 50);
        }
    }
    public void drawPicture()
    {
        for (Position pos : positions) {
            drawPosition(pos);
        }
    }

    public void drawPosition(Position pos) {
        fill(pos.getHue(), pos.getSaturation(), pos.getBrightness());
        stroke(pos.getHue(), pos.getSaturation(), pos.getBrightness());
        ellipse(pos.x_leftCorner, pos.y_topCorner, pos.radius * 2, pos.radius * 2);
    }

    // FIXME these aren't reading the correct hue saturation of brightness but give a general idea
    public int brightnessOfImage(Position pos) {
        return (int) brightness(img.get(pos.x_leftCorner, pos.y_topCorner));
    }
    public int hueOfImage(Position pos) {
        return (int) hue(img.get(pos.x_leftCorner, pos.y_topCorner));
    }
    public int saturationOfImage(Position pos) {
        return (int) saturation(img.get(pos.x_leftCorner, pos.y_topCorner));
    }

    public int brightnessOfImage(PImage img, int x, int y) {
        return (int) brightness(img.get(x, y));
    }
    public int hueOfImage(PImage img, int x, int y) {
        return (int) hue(img.get(x, y));
    }
    public int saturationOfImage(PImage img, int x, int y) {
        return (int) saturation(img.get(x, y));
    }

}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
