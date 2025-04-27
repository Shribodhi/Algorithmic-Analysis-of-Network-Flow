import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlowNetworkReader {

    public static FlowNetworkSolver.Graph parseNetwork(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(br.readLine().trim());
        FlowNetworkSolver.Graph graph = new FlowNetworkSolver.Graph(n);

        String line;
        while ((line = br.readLine()) != null){
            String[] parts = line.trim().split("\\s+");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            int capacity = Integer.parseInt(parts[2]);
            graph.addEdge(from, to, capacity);
        }
        br.close();
        return graph;
    }







}
