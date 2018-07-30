import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class State {
    private ArrayList<Position> positions;
    private static Random rnd = new Random();
    ArrayList<BottleTopType> unusedTops;

    public State(ArrayList<Position> positions, ArrayList<BottleTopType> capList) {
        this.positions = new ArrayList<>();
        for (Position pos : positions)
            this.positions.add(pos.copyPosition());
        this.unusedTops = new ArrayList<>();
        for (BottleTopType type : capList)
            this.unusedTops.add(type.copyType());
        for (Position pos : positions) {
            for (BottleTopType type : unusedTops) {
                if (type.getCOLOR_INFO().equals(pos.getCap()))
                    type.removeACap();
            }
        }
    }

    public State(State currState) {
        this.positions = new ArrayList<>();
        for (Position pos : currState.getPositions())
            this.positions.add(pos.copyPosition());
        this.unusedTops = new ArrayList<>();
        for (BottleTopType type : currState.getUnusedTops())
            this.unusedTops.add(type.copyType());
    }

    public void next() {
        // TODO figure out next for a state. for now its a random mutation
        // mutate 1% of the caps
        int fivePercentOfCaps = (int) (positions.size() * .01);
//        for (int i=0; i<fivePercentOfCaps; i++) {
        randomSwap();
//        }
    }
    private void randomSwap() {
        // TODO for now i'll keep it internal to the positions but it should draw from both the positions and the bank
        // draw first cap from either the bank of the picture
        if (rnd.nextInt(100) > (int) (BottleCapVisualizer.percentCapUsage * 100))
            swapWithBank();
        else
            swapOnPicture();
    }
    private void swapWithBank() {
        int capFromBankPosition = rnd.nextInt(unusedTops.size());
        BottleCap capFromBank = unusedTops.get(capFromBankPosition).removeACap();
        int positionOnPicture = rnd.nextInt(positions.size());
        boolean addedBack = false;
        for (BottleTopType type : unusedTops) {
            if (type.getCOLOR_INFO().equals(positions.get(positionOnPicture).getCap())) {
                type.addCapBack(positions.get(positionOnPicture).getCap());
                addedBack = true;
                break;
            }
        }
        if (!addedBack)
            unusedTops.add(new BottleTopType(1, positions.get(positionOnPicture).getCap()));
        if (unusedTops.get(capFromBankPosition).getNumCaps() <= 0)
            unusedTops.remove(capFromBankPosition);
        positions.get(positionOnPicture).replaceCap(capFromBank);
    }
    private void swapOnPicture() {
        int firstPosition = rnd.nextInt(positions.size());
        int secondPosition = rnd.nextInt(positions.size());
        BottleCap tempCap = positions.get(firstPosition).getCap();
        positions.get(firstPosition).replaceCap(positions.get(secondPosition).getCap());
        positions.get(secondPosition).replaceCap(tempCap);
    }

    /**
     *
     * @return a number <= 0, the higher to 0 the closer the eval is to what the picture calls for
     */
    public double evaluateState() {
        // TODO what do I want for an evaluation??
        // For now, return the average difference in brightnesses for the states
        double totalDifference = 0;
        for (Position pos: positions) {
            totalDifference += pos.evaluatePos();
        }
        return 0 - totalDifference / positions.size();
    }

    public ArrayList<BottleTopType> getUnusedTops() {
        return unusedTops;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }
    public List<State> generateSuccessors() {
        // TODO how many successors?? I'll just go with 10
        List<State> successors = new ArrayList<>();
        for (int i=0; i<10; i++) {
            State newState = new State(this);
            newState.next();
            successors.add(newState);
        }
        return successors;
    }
    public void fillPositionsWithRandomTops() {
        for (Position pos : positions) {
            int capTypeToRemoveFrom = rnd.nextInt(unusedTops.size());
            pos.replaceCap(unusedTops.get(capTypeToRemoveFrom).removeACap());
            if (unusedTops.get(capTypeToRemoveFrom).getNumCaps() <= 0)
                unusedTops.remove(capTypeToRemoveFrom);
        }
    }

    @Override
    public String toString() {
        return "State evaluation: " + evaluateState();
    }
}
