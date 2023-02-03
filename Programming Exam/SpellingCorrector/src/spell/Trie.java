package spell;

public class Trie implements ITrie {
    private int wordCount = 0;
    private int nodeCount = 1;

    private Node root = new Node();

    public void add(String word) {
        var currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char currentLetter = word.charAt(i);
            int index = currentLetter - 'a';
            var children = currentNode.getChildren();
            var childNode = children[index];
            if (childNode == null) {
                childNode = new Node();
                children[index] = childNode;
                nodeCount += 1;
            }
            currentNode = childNode;
            if (i == word.length() - 1) {
                if (currentNode.getValue() == 0) {
                    wordCount += 1;
                }
                currentNode.incrementValue();
            }
        }
    }

    public Node find(String word) {
        var currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            var children = currentNode.getChildren();
            char c = word.charAt(i);
            int index = c - 'a';
            currentNode = children[index];
            if (currentNode == null) {
                return null;
            }
        }

        if (currentNode.getValue() > 0) {

            return currentNode;
        } else {
            return null;
        }
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        var currentWord = new StringBuilder();
        var output = new StringBuilder();

        toStringHelper(root, currentWord, output);

        return output.toString();
    }

    @Override
    public int hashCode() {
        int sum = 0;
        var children = root.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {

                sum += children[i].getValue();
                sum += i;
            }
        }
        sum += wordCount + nodeCount;
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }

        var trieB = (Trie) (o);
        if (trieB.nodeCount != nodeCount || trieB.wordCount != wordCount) {
            return false;
        }

        return equalsHelper(root, trieB.root);
    }

    private void toStringHelper(Node currentNode, StringBuilder currentWord, StringBuilder output) {
        if (currentNode.getValue() > 0) {
            output.append(currentWord.toString() + "\n");
        }

        var children = currentNode.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                char nodeLetter = (char) ('a' + i);
                currentWord.append(nodeLetter);
                toStringHelper(children[i], currentWord, output);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }

    }

    private boolean equalsHelper(Node currentNodeA, Node currentNodeB) {
        if (currentNodeA.getValue() != currentNodeB.getValue()) {
            return false;
        }

        var childrenA = currentNodeA.getChildren();
        var childrenB = currentNodeB.getChildren();

        for (int i = 0; i < childrenA.length; i++) {
            if (childrenA[i] == null && childrenB[i] != null) {
                return false;
            }
            if (childrenB[i] == null && childrenA[i] != null) {
                return false;
            }
            if (childrenA[i] != null && childrenB[i] != null) {
                var temp = equalsHelper(childrenA[i], childrenB[i]);
                if (!temp) {
                    return false;
                }
            }
        }
        return true;
    }
}
