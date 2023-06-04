package pl.edu.pw.dekodowaniehuffmanaostateczne;

public record HuffmanNode (
        Integer data,
        String code,
        HuffmanNode leftChild,
        HuffmanNode rightChild
) implements Comparable<HuffmanNode> {

    public boolean isLeaf() {
        return (leftChild() == null) && (rightChild() == null);
    }

    public HuffmanNode traverse(char direction) {
        // false - left, true - right

        if (direction == '0')
            return leftChild;
        else
            return rightChild;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return code.compareTo(o.code());
    }

    public boolean hasLeftChild() {
        return leftChild != null;
    }

    public boolean hasRightChild() {

        return rightChild != null;
    }

    public char character() {
        if (data >= 32 && data <= 127) {
            return Character.toChars(data)[0];
        } else {
            return ' ';
        }
    }
}
