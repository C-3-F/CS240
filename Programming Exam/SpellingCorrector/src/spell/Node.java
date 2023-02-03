package spell;

public class Node implements INode {
    private int value = 0;
    private Node[] children = new Node[26];

    public int getValue() {
        return value;
    }

    public void incrementValue() {
        value += 1;
    }

    public Node[] getChildren() {
        return children;
    }
}
