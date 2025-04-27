// Student ID: [ w2082808 / 20230401 ] - Name: [ Morathennage Shribodhi Indrakheela Ranasinghe ]

import java.io.*;
import java.util.List;

public class FlowNetworkSolver {

    // Defining the Edges with the capacities and flows
    static class Edge{
        // Initializing the edge's variables
        int to, capacity, flow;
        Edge residual;

        // Constructor of the Edge class
        Edge(int to, int capacity){
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }

        boolean isResidual(){
            return capacity ==0;
        }

        int remainingCapacity(){
            return capacity - flow;
        }

        void augment(int bottleNeck){
            flow += bottleNeck;
            residual.flow -= bottleNeck;
        }

        static class Graph{
            int n;
            List<Edge>[] adjList;

            @SuppressWarnings("unchecked")
            Graph(int n){
                this.n = n;
                adjList = new List[n];
                for(int )
            }

        }

    }

}
