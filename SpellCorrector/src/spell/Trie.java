package spell;

import static org.junit.jupiter.api.Assertions.fail;

public class Trie implements ITrie {

    private Node root = new Node();
    private int wordCount = 0;
    private int nodeCount = 1;

    public void add(String word) {
        var currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            var children = currentNode.getChildren();
            if (children[index] == null) {
                children[index] = new Node();
                nodeCount += 1;
            }
            currentNode = children[index];
            if (i == word.length() - 1) {
                if (currentNode.getValue() == 0) {
                    wordCount += 1;
                }
                currentNode.incrementValue();
            }
        }
    }

    public spell.Node find(String word) {
        var currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            var children = currentNode.getChildren();
            if (children[index] == null) {
                return null;
            }
            currentNode = children[index];
        }

        if (currentNode.getValue() == 0) {
            return null;
        }
        return currentNode;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        var currWord = new StringBuilder();
        var output = new StringBuilder();
        toStringHelper(root, currWord, output);
        return output.toString();
    }

    public int hashCode() {
        int sum = 0;
        var children = root.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                sum += i;
                sum += children[i].getValue();
            }
        }
        sum += wordCount + nodeCount;
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        var t = (Trie) o;
        if (t.nodeCount != nodeCount || t.wordCount != wordCount) {
            return false;
        }

        return equalsHelper(root, t.root);
    }

    private void toStringHelper(Node currentNode, StringBuilder currWord, StringBuilder output) {
        if (currentNode.getValue() > 0) {
            output.append(currWord.toString() + "\n");
        }

        var children = currentNode.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {

                char childLetter = (char) ('a' + i);
                currWord.append(childLetter);
                toStringHelper(children[i], currWord, output);
                currWord.deleteCharAt(currWord.length() - 1);
            }
        }
    }

    private boolean equalsHelper(Node currNodeA, Node currNodeB) {
        if (currNodeA.getValue() != currNodeB.getValue()) {
            return false;
        }
        for (int i = 0; i < currNodeA.getChildren().length; i++) {
            var childA = currNodeA.getChildren()[i];
            var childB = currNodeB.getChildren()[i];
            if ((childA != null && childB == null) || (childA == null && childB != null)) {
                return false;
            } else if (childA != null && childB != null) {

                var temp = equalsHelper(childA, childB);
                if (!temp)
                {
                    return false;
                }

            } else {
            }
        }
        return true;
    }

}
