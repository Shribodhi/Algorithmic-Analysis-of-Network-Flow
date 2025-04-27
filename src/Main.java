import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Usage: java main <inputfile>");
            return;
        }

        String filename = args[0];
        try{
            FlowNetworkSolver.Graph graph = FlowNetworkReader.parseNetwork(filename);
            int source = 0;
            int sink = graph.n - 1;
            int maxFlow = FlowNetworkSolver.maxFlow(graph, source, sink);

            boolean verboseMode = graph.n <= 2000; // Only show detailed flow for small graphs

            if(verboseMode){
                System.out.println("\nFlow along edges:");
                for(int u = 0; u < graph.n; u++){
                    for(FlowNetworkSolver.Edge edge : graph.adjList[u]){
                        // Only show original edges (non-residual) and real flow
                        if(!edge.isResidual() && edge.capacity > 0){
                            int usedFlow = edge.flow;
                            int originalCapacity = edge.capacity;
                            if(usedFlow > 0){
                                System.out.println("Edge " + u + " ->" + edge.to + "| flow = " + usedFlow + " / " + originalCapacity);
                            }
                        }
                    }
                }
            }
            System.out.println("\nMaximum Flow: " + maxFlow);
        }
        catch(IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}