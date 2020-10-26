import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class GenGraph {
    public static void main(String[] args) {
        List<String> vertexes = new ArrayList<>();
        List<String> edges = new ArrayList<>();
        final String test = args[0];
        try {
            Parser parser = new Parser();
            Tree tree = parser.parse(new ByteArrayInputStream(test.getBytes()));
            tree.getGraph(vertexes, edges);

            FileWriter writer = new FileWriter("graph.txt");
            writer.write("digraph G {\n");
            for (String v : vertexes) {
                writer.write('\t' + v + '\n');
            }
            writer.write('\n');
            for (String e : edges) {
                writer.write('\t' + e + '\n');
            }
            writer.write("}");
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occurred while generating graph");
        }
    }
}
