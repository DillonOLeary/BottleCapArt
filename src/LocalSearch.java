import java.util.ArrayList;
import java.util.List;

public class LocalSearch {
    public static ArrayList<Position> hillClimbing(ArrayList<Position> positions, ArrayList<BottleTopType> capList) {
        State currState = new State(positions, capList);
        currState.fillPositionsWithRandomTops();
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
            System.out.println("This round's best state: " + currState.evaluateState());
        }
        return currState.getPositions();
    }
    public static void SimulatedAnnealingAlgo(ArrayList<Position> positions, ArrayList<BottleTopType> capList)
    {
        // Pick initial state, s
        // k=0
        // while k < k_max
        //      T = temperature(k)
        //      Randomly pick state t from neighbors of s
        //      if f(t) > f(s) then s=t
        //      else if (e^((f(t)-f(s))/T) > random() then s=t
        //      k++
        //  return s
    }
}
