import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Tree {
    public String getNode() {
        return node;
    }

//    public boolean eval(Map<Character, Boolean> variableValues) {
//        return false;
//    }

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
