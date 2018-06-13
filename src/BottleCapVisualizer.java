import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class BottleCapVisualizer extends PApplet {
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 750;
    public static final String IMAGE_FILE_NAME = "obama.jpg";
    public static final String CAP_INFORMATION_FILE = "CapImages/caps.txt";

    private static ArrayList<BottleTopType> capList;
    private static ArrayList<Position> positions;
    public static float percentCapUsage;
    private static PImage img;

    public static void main(String args[]) {
        if (args.length == 0)
            throw new IllegalArgumentException("Add argument percent cap usage, float between 0 and 1");
        percentCapUsage = Float.parseFloat(args[0]);
//        realColorQueue = new PriorityQueue<ColorUnit>();
        PApplet.main("BottleCapVisualizer");
    }

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void setup() {
        capList = new ArrayList<>();
        ReadingFromFile.addTopsFromFile(CAP_INFORMATION_FILE, capList, this);
        colorMode(HSB, 100);
        ellipseMode(CORNER);
        background(100);
        img = loadImage(IMAGE_FILE_NAME);

        resizePicture();
        positions = Position.setupPositions(img, capList, percentCapUsage);
        // Set all the real colors
        for (Position pos : positions) {
            pos.setRealColor(img.get(pos.x_leftCorner, pos.y_topCorner,
                    pos.radius * 2, pos.radius * 2));
        }
        positions = LocalSearch.hillClimbing(positions, capList);
//        LocalSearch.SimulatedAnnealingAlgo(positions, capList);
//        drawPicture();
        System.out.println("The width and height: " + img.width + " x " + img.height);
    }

    public void draw() {
        // Probably not going to use, although it would be nice to show the current progress of the algorithm
        PImage tempImg = positions.get(0).getCap().image;
        tempImg.resize(positions.get(0).radius * 2, 0);
        image(positions.get(0).getCap().image, 0, 0);
        fill(positions.get(0).getHue(), positions.get(0).getSaturation(), positions.get(0).getBrightness());
        stroke(positions.get(0).getHue(), positions.get(0).getSaturation(), positions.get(0).getBrightness());
        rect(positions.get(0).radius * 2, positions.get(0).radius * 2, positions.get(0).radius * 2, positions.get(0).radius * 2);
    }

    private void resizePicture() {
        while ((img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT)) {
            img.resize(img.width - 50, img.height - 50);
        }
        while (!((img.width > WINDOW_WIDTH) || (img.height > WINDOW_HEIGHT))
                && (img.width < WINDOW_WIDTH - 75 || img.height < WINDOW_HEIGHT - 75)) {
            img.resize(img.width + 50, img.height + 50);
        }
    }

    public void drawPicture() {
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
//    public int brightnessOfImage(Position pos) {
//        return (int) brightness(img.get(pos.x_leftCorner, pos.y_topCorner));
//    }
//    public int hueOfImage(Position pos) {
//        return (int) hue(img.get(pos.x_leftCorner, pos.y_topCorner));
//    }
//    public int saturationOfImage(Position pos) {
//        return (int) saturation(img.get(pos.x_leftCorner, pos.y_topCorner));
//    }

}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
