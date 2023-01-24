package spell;

public class Node implements INode 
{

    private Node[] children = new Node[26];
    private int count = 0;


    public int getValue()
    {
        return count;
    }

    public void incrementValue()
    {
        count += 1;
    }

    public Node[] getChildren()
    {
        return children;
    }
}