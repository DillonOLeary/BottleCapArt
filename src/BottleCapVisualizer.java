import javafx.geometry.Pos;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the high level construction of the
 * app. It calls the set up and draws the result
 */
public class BottleCapVisualizer extends PApplet {
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 750;
    private static final String IMAGE_FILE_NAME = "obama.jpg";
    private static final String CAP_INFORMATION_FILE = "CapImages/caps.txt";

    // the positions that the bottle caps are placed
    private static ArrayList<Position> positions;
    // the percent of total number of caps that should be used
    public static float percentCapUsage;
    // the image that the bottle caps are trying to make
    private PImage targetImage;
    private State currState;

    public static void main(String args[]) {
        if (args.length == 0)
            throw new IllegalArgumentException("Add argument percent cap usage, float between 0 and 1");
        percentCapUsage = Float.parseFloat(args[0]);
        PApplet.main("BottleCapVisualizer");
    }

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void setup() {
        ArrayList<BottleTopType> capList = new ArrayList<>();
        ReadingFromFile.addTopsFromFile(CAP_INFORMATION_FILE, capList, this);
        colorMode(HSB, 100);
        ellipseMode(CORNER);
        background(100);
        targetImage = loadImage(IMAGE_FILE_NAME);
        resizePicture();
        positions = Position.setupPositions(targetImage, capList, percentCapUsage);
        // Set all the real colors
        for (Position pos : positions) {
            pos.setRealColor(targetImage.get(pos.x_leftCorner, pos.y_topCorner,
                    pos.radius * 2, pos.radius * 2));
        }
//        positions =  LocalSearch.hillClimbing(positions, capList);
        currState = new State(positions, capList);
        currState.fillPositionsWithRandomTops();
        positions = currState.getPositions();
        resizePictures(positions);
    }

    private static void resizePictures(ArrayList<Position> positions) {
        for (Position pos : positions)
            if (pos.getCap().image.width != pos.radius * 2)
                pos.getCap().image.resize(pos.radius * 2, 0);
    }

    public void draw() {
        // Probably not going to use, although it would be nice to show the current progress of the algorithm
        List<State> successors = currState.generateSuccessors();
        State nextBestState = successors.get(0);
        for (State state : successors) {
            if (state.evaluateState() > nextBestState.evaluateState())
                nextBestState = state;
        }
        if (nextBestState.evaluateState() <= currState.evaluateState()) {
            System.out.println("DONE");

        }
        currState = nextBestState;
        System.out.println("This round's best state: " + currState);
        positions = currState.getPositions();
        drawPictureWithCaps();
    }

    /**
     * Change the picture size so it fits within the window
     */
    private void resizePicture() {
        while ((targetImage.width > WINDOW_WIDTH) || (targetImage.height > WINDOW_HEIGHT)) {
            targetImage.resize(targetImage.width - 50, targetImage.height - 50);
        }
        while (!((targetImage.width > WINDOW_WIDTH) || (targetImage.height > WINDOW_HEIGHT))
                && (targetImage.width < WINDOW_WIDTH - 75 || targetImage.height < WINDOW_HEIGHT - 75)) {
            targetImage.resize(targetImage.width + 50, targetImage.height + 50);
        }
    }

    /**
     * This method draws the picture with the actual cropped cap photos
     * instead of with the colors.
     */
    public void drawPictureWithCaps() {
        background(0);
        for (Position pos : positions)
            drawPositionWithCaps(pos);
    }

    /**
     * Draw the particular cap
     * @param pos the position to draw
     */
    public void drawPositionWithCaps(Position pos) {
        image(pos.getCap().image, pos.x_leftCorner, pos.y_topCorner);
    }

    /*
    ---------DEPRECIATED---------

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
    */
}
// notes from Jack:
// loss funtion!! least squares
// oct tree!
// hsv colors instead of rgb
// shl hsv
