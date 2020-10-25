import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Tree {
    public String getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "node='" + node + '\'' +
                ", children=" + children +
                '}';
    }

    public List<Tree> getChildren() {
        return children;
    }

    private final String node;
    private final List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }
}
