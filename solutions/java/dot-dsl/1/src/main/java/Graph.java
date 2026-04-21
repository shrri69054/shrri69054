import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final ArrayList<Node> node;
    private final ArrayList<Edge> edge;
    private final Map<String, String> attribute;

    public Graph() {
        this.node = new ArrayList<>();
        this.edge = new ArrayList<>();
        this.attribute = new HashMap<>();
    }

    public Graph(Map<String, String> attributes) {
        this.node = new ArrayList<>();
        this.edge = new ArrayList<>();
        this.attribute = attributes;
    }

    public Collection<Node> getNodes() {
        return this.node;
    }

    public Collection<Edge> getEdges() {
        return this.edge;
    }

    public Graph node(String name) {
        this.node.add(new Node(name));
        return this;
    }

    public Graph node(String name, Map<String, String> attributes) {
        this.node.add(new Node(name, attributes));
        return this;
    }

    public Graph edge(String start, String end) {
        this.edge.add(new Edge(start, end));
        return this;
    }

    public Graph edge(String start, String end, Map<String, String> attributes) {
        this.edge.add(new Edge(start, end, attributes));
        return this;
    }

    // Add the missing getAttributes method
    public Map<String, String> getAttributes() {
        return this.attribute;
    }
}
