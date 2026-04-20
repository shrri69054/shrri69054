import java.util.*;
import java.util.stream.*;

class Tree {
    private final String label;
    private final List<Tree> children;

    public Tree(String label) {
        this(label, new ArrayList<>());
    }

    public Tree(String label, List<Tree> children) {
        this.label = label;
        this.children = children;
    }

    public static Tree of(String label) {
        return new Tree(label);
    }

    public static Tree of(String label, List<Tree> children) {
        return new Tree(label, children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return label.equals(tree.label)
                && children.size() == tree.children.size()
                && children.containsAll(tree.children)
                && tree.children.containsAll(children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children);
    }

    @Override
    public String toString() {
        return "Tree{" + label +
                ", " + children +
                "}";
    }

    public Tree fromPov(String fromNode) {
        List<String> chain = parentChain(fromNode);
        if (chain.isEmpty()) {
            throw new UnsupportedOperationException("Tree could not be reoriented");
        }
        chain = chain.subList(1, chain.size());

        Tree pov = this;
        for (String label : chain) {
            Tree newRoot = pov
                .children
                .stream()
                .filter(child -> child.label.equals(label))
                .findFirst()
                .get();
            Tree newChild = new Tree(
                pov.label,
                pov.children
                    .stream()
                    .filter(child -> child != newRoot)
                    .toList()
            );
            pov = new Tree(
                newRoot.label,
                Stream.concat(newRoot.children.stream(), Stream.of(newChild)).toList()
            );
        }

        return pov;
    }

    public List<String> pathTo(String fromNode, String toNode) {
        List<String> chainFrom = parentChain(fromNode);
        List<String> chainTo = parentChain(toNode);
        if (chainFrom.isEmpty() || chainTo.isEmpty()) {
            throw new UnsupportedOperationException("No path found");
        }

        String closestCommonAncestor = IntStream.range(0, Math.min(chainFrom.size(), chainTo.size()))
            .takeWhile(idx -> chainFrom.get(idx).equals(chainTo.get(idx)))
            .mapToObj(chainFrom::get)
            .reduce((acc, last) -> last)
            .get();

        int ccaIdx = chainFrom.indexOf(closestCommonAncestor);
        return Stream.concat(
            chainFrom.subList(ccaIdx, chainFrom.size()).reversed().stream(),
            chainTo.subList(ccaIdx + 1, chainTo.size()).stream()
        ).toList();
    }

    private List<String> parentChain(String node) {
        if (label.equals(node)) {
            return List.of(node);
        }
        if (children.isEmpty()) {
            return List.of();
        }
        return children
            .stream()
            .map(child -> child.parentChain(node))
            .filter(chain -> !chain.isEmpty())
            .map(chain -> Stream.concat(Stream.of(label), chain.stream()).toList())
            .findFirst()
            .orElseGet(List::of);
    }
}