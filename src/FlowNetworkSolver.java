import java.util.*;

public class FlowNetworkSolver {

    // Defining the Edges with the capacities and flows
    static class Edge {
        // Initializing the edge's variables
        int to, capacity, flow; // The destination node, capacity of the edge, and flow through the edge
        Edge residual; // This is to keep track of the residual edge

        // Constructor to initialize the edge
        Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        // This method checks if the edge is a residual edge
        boolean isResidual() {
            return capacity == 0;
        }

        // This method returns the remaining capacity of the edge
        int remainingCapacity() {
            return capacity - flow;
        }


        // This method is used to augment the flow through the edge
        void augment(int bottleNeck) {
            flow += bottleNeck; // Increase the flow by the bottleneck value
            residual.flow -= bottleNeck; // Decrease the flow of the residual edge by the bottleneck value
        }
    }

    // This class represents the graph structure
    static class Graph {
        int n; // Number of nodes in the graph
        List<Edge>[] adjList; // Adjacency list to represent the graph


        /*
        * Constructor to initialize the graph with the number of nodes
        * This constructor initializes the adjacency list for each node
        * and creates an empty list for each node
         */
        @SuppressWarnings("unchecked") // Suppress warnings for unchecked cast
        Graph(int n) {
            this.n = n;
            adjList = new List[n];
            for (int i = 0; i < n; i++) {
                adjList[i] = new ArrayList<>();
            }
        }


        /*
        * The method adds an edge to the graph
        * The edge is added in both directions
        * This is done to keep track of the flow in both directions
         */
        void addEdge(int from, int to, int capacity) {

            Edge e1 = new Edge(to, capacity); // Forward edge
            Edge e2 = new Edge(from, 0); // Residual edge

            e1.residual = e2;
            e2.residual = e1;

            /*
            * The forward edge is added to the adjacency list of the from node
            * The residual edge is added to the adjacency list of the to node
             */
            adjList[from].add(e1);
            adjList[to].add(e2);
        }
    }


    // This method performs a breadth-first search (BFS) to find the augmenting path
    private static int bfs(Graph graph, int s, int t, Edge[] parentMap) {

        Arrays.fill(parentMap, null); // Initialize the parent map to null
        Queue<Integer> queue = new ArrayDeque<>(); // Create a queue for BFS
        queue.offer(s); // Add the source node to the queue

        /*
         * This is the main loop of the BFS
         * loop continues until the queue is empty
         */
        while (!queue.isEmpty()) {
            int current = queue.poll(); // Remove the first element from the queue and set it as the current node
            for (Edge edge : graph.adjList[current]) { // Iterate through the edges of the current node


                /*
                * Check if:
                * 1) The edge has remaining capacity to push flow through
                * 2) The destination node hasn't been visited in this BFS iteration
                * 3) The destination is not the source node
                 */
                if (edge.remainingCapacity() > 0 && parentMap[edge.to] == null && edge.to != s) {
                    parentMap[edge.to] = edge;  // Set the parent of the destination node to the current edge
                    if (edge.to == t) { // If the destination node is the sink node
                        return findBottleNeck(parentMap, s, t);
                    }
                    queue.offer(edge.to); // Add the destination node to the queue
                }
            }
        }
        return 0; // Return 0 if no augmenting path is found
    }

    // This method finds the bottleneck capacity of the augmenting path
    private static int findBottleNeck(Edge[] parentMap, int s, int t) {
        int bottleNeck = Integer.MAX_VALUE; // Initialize the bottleneck to the maximum value

        // Iterate through the parent map to find the minimum capacity
        for (Edge e = parentMap[t]; e != null; e = parentMap[e.residual.to]) {
            bottleNeck = Math.min(bottleNeck, e.remainingCapacity()); //
        }
        return bottleNeck;
    }

    /*
    * This method calculates the maximum flow in the graph using the Edmonds-Karp algorithm
    * It uses BFS to find the augmenting paths and updates the flow accordingly
    * The method returns the total flow from the source to the sink
     */

    // Using BFS to find the maximum flow and updating the flow in the graph
    // The method takes the graph, source node, and sink node as input
    // It returns the maximum flow from the source to the sink
    // The method uses the BFS algorithm to find the augmenting paths
    // The method also keeps track of the flow in the graph and updates it accordingly
    public static int maxFlow(Graph graph, int s, int t) {
        int flow = 0;
        Edge[] parentMap = new Edge[graph.n]; // Array to keep track of the parent edges in the augmenting path
        int newFlow;

        // This loop continues until no more augmenting paths are found
        while ((newFlow = bfs(graph, s, t, parentMap)) > 0) {
            for (Edge e = parentMap[t]; e != null; e = parentMap[e.residual.to]) { // Iterate through the parent map
                e.augment(newFlow);
            }
            flow += newFlow; // Update the total flow
            System.out.println("Augmented flow by: " + newFlow + ", Current total flow: " + flow);
        }
        return flow; // Return the total flow from the source to the sink
    }
}
