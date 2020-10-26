import java.util.Arrays;
import java.util.List;

public class Tree {
    private String genGraphId(int id) {
        return "V" + id;
    }

    private String getFullVertexRepresentation() {
        return graphId + "[label=\"" + node + "\"];";
    }

    private String getEdge(String fromGraphId, String toGraphId) {
        return fromGraphId + " -> " + toGraphId;
    }

    public void getGraph(List<String> vertexes, List<String> edges) {
        graphId = genGraphId(vertexes.size());
        vertexes.add(getFullVertexRepresentation());
        for (Tree ch : children) {
            ch.getGraph(vertexes, edges);
        }
        for (Tree ch : children) {
            edges.add(getEdge(graphId, ch.graphId));
        }
    }

    @Override
    public String toString() {
        if (children.isEmpty()) {
            return node;
        } else if (children.get(0) == Parser.EPS) {
            return "";
        } else {
            StringBuilder res = new StringBuilder();
            for (var child : children) {
                res.append(child.toString());
            }
            return res.toString();
        }
    }

    private String graphId;
    private final String node;
    private final List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }
}
