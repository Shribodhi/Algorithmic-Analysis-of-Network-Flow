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

        
        void addEdge(int from, int to, int capacity) {
            Edge e1 = new Edge(to, capacity);
            Edge e2 = new Edge(from, 0); // Residual edge
            e1.residual = e2;
            e2.residual = e1;
            adjList[from].add(e1);
            adjList[to].add(e2);
        }
    }

    private static int bfs(Graph graph, int s, int t, Edge[] parentMap) {
        Arrays.fill(parentMap, null);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (Edge edge : graph.adjList[current]) {
                if (edge.remainingCapacity() > 0 && parentMap[edge.to] == null && edge.to != s) {
                    parentMap[edge.to] = edge;
                    if (edge.to == t) {
                        return findBottleNeck(parentMap, s, t);
                    }
                    queue.offer(edge.to);
                }
            }
        }
        return 0;
    }

    private static int findBottleNeck(Edge[] parentMap, int s, int t) {
        int bottleNeck = Integer.MAX_VALUE; // This is a local variable right ?
        for (Edge e = parentMap[t]; e != null; e = parentMap[e.residual.to]) {
            bottleNeck = Math.min(bottleNeck, e.remainingCapacity());
        }
        return bottleNeck;
    }

    public static int maxFlow(Graph graph, int s, int t) {
        int flow = 0;
        Edge[] parentMap = new Edge[graph.n];

        int newFlow;
        while ((newFlow = bfs(graph, s, t, parentMap)) > 0) {
            for (Edge e = parentMap[t]; e != null; e = parentMap[e.residual.to]) {
                e.augment(newFlow);
            }
            flow += newFlow;
            System.out.println("Augmented flow by: " + newFlow + ", Current total flow: " + flow);
        }
        return flow;
    }
}
