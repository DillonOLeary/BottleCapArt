import java.util.ArrayList;

public class SimulatedAnnealing {
    public static void SimulatedAnnealingAlgo(BottleCapVisualizer visualizer, ArrayList<Position> positions, ArrayList<BottleTopType> capList)
    {
        ArrayList<BottleTopType> UnusedTops = new ArrayList<>(capList);
        // Pick initial state, s
        // k=0
        // while k <k_max
        //      T = temperature(k)
        //      Randomly pick state t from neighbors of s
        //      if f(t) > f(s) then s=t
        //      else if (e^((f(t)-f(s))/T) > random() then s=t
        //      k++
        //  return s
    }
}
