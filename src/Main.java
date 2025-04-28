// Student ID: [ w2082808 / 20230401 ] - Name: [ Morathennage Shribodhi Indrakheela Ranasinghe ]

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Absolute path to the text file
            String filename = "src/ladder_15.txt";

            // Check if file exists first
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Error: File not found at path: " + filename);
                System.out.println("Please check the path and try again.");
                return;
            }

            // Parse the network from the file
            FlowNetworkSolver.Graph graph = FlowNetworkReader.parseNetwork(filename);
            int source = 0;
            int sink = graph.n - 1;
            int maxFlow = FlowNetworkSolver.maxFlow(graph, source, sink);

            boolean verboseMode = graph.n <= 5000; // Only show detailed flow for small graphs


            if (verboseMode) {
                System.out.println("\nFlows along edges:");
                for (int u = 0; u < graph.n; u++) { // Iterate through each node
                    for (FlowNetworkSolver.Edge edge : graph.adjList[u]) { // Iterate through the edges of each node
                        if (!edge.isResidual() && edge.capacity > 0) { // Check if the edge is not a residual edge and has capacity
                            int usedFlow = edge.flow; // Get the flow through the edge
                            int originalCapacity = edge.capacity; // Get the original capacity of the edge
                            /*
                            * u is the starting node and edge.to is the destination node
                             */
                            if (usedFlow > 0) {
                                System.out.println("Edge " + u + " -> " + edge.to + " | flow = " + usedFlow + " / " + originalCapacity);
                            }
                        }
                    }
                }
            }

            System.out.println("\nMaximum Flow: " + maxFlow);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
