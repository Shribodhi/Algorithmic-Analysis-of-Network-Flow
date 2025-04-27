import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// This class is used to read the flow network from a file
public class FlowNetworkReader {

    // This method reads the flow network from a file and returns a Graph object
    public static FlowNetworkSolver.Graph parseNetwork(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(br.readLine().trim()); // Read the number of nodes
        FlowNetworkSolver.Graph graph = new FlowNetworkSolver.Graph(n); // Create a new graph with n nodes

        String line; // Initialize a variable to hold each line of the file
        while ((line = br.readLine()) != null){
            String[] parts = line.trim().split("\\s+"); // Split the line into parts

            int from = Integer.parseInt(parts[0]); // Read the source node
            int to = Integer.parseInt(parts[1]); // Read the destination node
            int capacity = Integer.parseInt(parts[2]); // Read the capacity of the edge

            // Add the edge to the graph
            graph.addEdge(from, to, capacity);
        }
        br.close();
        return graph;
    }

    
}
