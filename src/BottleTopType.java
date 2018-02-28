public class BottleTopType
{
    private final AvailableColor COLOR_INFO;
    private int numCaps;
    BottleTopType(int numCaps, AvailableColor col)
    {
        this.COLOR_INFO = col;
        this.numCaps = numCaps;
    }

    public ColorUnit removeACap()
    {
        if (numCaps <= 0)
        {
            throw new RuntimeException("No more caps left!");
        }
        numCaps--;
        return COLOR_INFO;
    }

    public AvailableColor getCOLOR_INFO() {
        return COLOR_INFO;
    }
}
