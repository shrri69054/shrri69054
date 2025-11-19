import java.util.Objects;

class Zipper
{
    int value;

    public Zipper left;

    public Zipper right;

    public Zipper up;

    Zipper(int val)
    {
        value = val;
    }

    BinaryTree toTree()
    {
        Zipper top = this;

        while (top.up != null)
        {
            top = top.up;
        }
        return new BinaryTree(top);
    }

    int getValue()
    {
        return value;
    }

    Zipper setLeft(Zipper leftChild)
    {
        left = leftChild;
        leftChild.up = this;
        return left;
    }

    Zipper setRight(Zipper rightChild)
    {
        right = rightChild;
        if( Objects.nonNull(rightChild))
        {
            rightChild.up = this;
        }
        return this;
    }

    void setValue(int val)
    {
        value = val;
    }
}

class BinaryTree
{
    Zipper root;

    BinaryTree(int value)
    {
        root = new Zipper(value);
    }

    BinaryTree(Zipper root)
    {
        this.root = root;
    }

    Zipper getRoot()
    {
        return root;
    }

    String printTree()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("value: ");
        sb.append(root.value);
        sb.append(", left: ");
        sb.append(printElem(root.left));
        sb.append(", right: ");
        sb.append(printElem(root.right));

        return sb.toString();
    }

    private String printElem(Zipper elem)
    {
        if (Objects.isNull(elem))
        {
            return "null";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("{ value: ");
        sb.append(elem.value);
        sb.append(", left: ");
        sb.append(printElem(elem.left));
        sb.append(", right: ");
        sb.append(printElem(elem.right));
        sb.append(" }");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        BinaryTree that = (BinaryTree) o;
        return Objects.equals(root, that.root);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(root);
    }
}