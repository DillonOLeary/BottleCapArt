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

    public BottleCap removeACap()
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
    public BottleTopType copyType() {
        return new BottleTopType(this.numCaps, COLOR_INFO);
    }
    @Override
    public int hashCode() {
        return COLOR_INFO.getName().hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof BottleTopType)
            return ((BottleTopType) o).COLOR_INFO.getName().equals(this.COLOR_INFO.getName());
//        else if (o instanceof BottleCap)
//            return ((BottleCap) o).getName().equals(this.COLOR_INFO.getName());
        else
            return false;
    }
    public void addCapBack(BottleCap cap) {
        numCaps++;
    }
}
