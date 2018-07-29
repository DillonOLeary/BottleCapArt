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
    PImage maskImage;

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
        LocalSearch.SimulatedAnnealingAlgo(positions, capList);
//        drawPicture();
        drawPictureWithCaps();
        System.out.println("The width and height: " + img.width + " x " + img.height);
    }

    public void draw() {
        // Probably not going to use, although it would be nice to show the current progress of the algorithm
//        int capPostionToTest = 55;
//        PImage tempImg = positions.get(capPostionToTest).getCap().image;
//        tempImg.resize(positions.get(capPostionToTest).radius * 2, 0);
//        image(positions.get(capPostionToTest).getCap().image, 0, 0);
//        fill(positions.get(capPostionToTest).getHue(), positions.get(capPostionToTest).getSaturation(), positions.get(capPostionToTest).getBrightness());
//        stroke(positions.get(capPostionToTest).getHue(), positions.get(capPostionToTest).getSaturation(), positions.get(capPostionToTest).getBrightness());
//        rect(positions.get(capPostionToTest).radius * 2, 0, positions.get(capPostionToTest).radius * 2, positions.get(capPostionToTest).radius * 2);
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

    /**
     * This method draws the picture with the actual cap photos, hopefully cropped,
     * instead of with the colors.
     */
    public void drawPictureWithCaps() {
        for (Position pos : positions)
            drawPositionWithCaps(pos);
//        drawPositionWithCaps(positions.get(0));
    }

    public void drawPositionWithCaps(Position pos) {
        PImage tempImg = pos.getCap().image;
        System.out.println("IMAGE WIDTH: " + tempImg.width + "  HEIGHT: " + tempImg.height);
//        tempImg.resize(140,140);
        PImage maskImage = loadImage("InvertedCircle.jpg");
        tempImg.mask(maskImage);
        //tempImg.resize(pos.radius * 2, 0);
        image(pos.getCap().image, pos.x_leftCorner, pos.y_topCorner);
    }
}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
