import java.util.ArrayList;
import java.util.List;

public class LocalSearch {
    public static ArrayList<Position> hillClimbing(ArrayList<Position> positions, ArrayList<BottleTopType> capList) {
        State currState = new State(positions, capList);
        currState.fillPositionsWithRandomTops();
        currState = getState(currState);
        return currState.getPositions();
    }

    private static State getState(State currState) {
        while(true) {
            List<State> successors = currState.generateSuccessors();
            State nextBestState = successors.get(0);
            for (State state : successors) {
                if (state.evaluateState() > nextBestState.evaluateState())
                    nextBestState = state;
            }
            if (nextBestState.evaluateState() <= currState.evaluateState())
                break;
            currState = nextBestState;
            System.out.println("This round's best state: " + currState);
        }
        return currState;
    }
}
