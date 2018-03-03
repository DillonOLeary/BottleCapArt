public class BottleTopType
{
    private final BottleCap COLOR_INFO;

    public int getNumCaps() {
        return numCaps;
    }

    private int numCaps;
    BottleTopType(int numCaps, BottleCap col)
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

    public BottleCap getCOLOR_INFO() {
        return COLOR_INFO;
    }

    @Override
    public int hashCode() {
        return COLOR_INFO.getName().hashCode();
    }
}
