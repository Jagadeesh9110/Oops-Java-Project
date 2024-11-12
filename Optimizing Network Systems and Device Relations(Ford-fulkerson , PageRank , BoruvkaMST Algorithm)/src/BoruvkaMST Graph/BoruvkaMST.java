package Algorithm3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

class Edge {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

class DisjointSet {
    int[] parent, rank;

    DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    int find(int u) {
        if (parent[u] != u) {
            parent[u] = find(parent[u]);
        }
        return parent[u];
    }

    void union(int u, int v) {
        int root1 = find(u);
        int root2 = find(v);
        if (root1 != root2) {
            if (rank[root1] > rank[root2]) {
                parent[root2] = root1;
            } else if (rank[root1] < rank[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                rank[root1]++;
            }
        }
    }
}

class Graph {
    int V, E;
    List<Edge> edges;

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        edges = new ArrayList<>();
    }

    void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }
}

public class BoruvkaMST {
    public static void boruvkasMST(Graph graph, String outputFileName) throws IOException {
        int V = graph.V;
        int E = graph.E;
        DisjointSet ds = new DisjointSet(V);
        int[] cheapest = new int[V];
        int mstWeight = 0;
        int numTrees = V;

        while (numTrees > 1) {
            for (int i = 0; i < V; i++) {
                cheapest[i] = -1;
            }

            for (int i = 0; i < E; i++) {
                Edge edge = graph.edges.get(i);
                int set1 = ds.find(edge.src);
                int set2 = ds.find(edge.dest);
                if (set1 != set2) {
                    if (cheapest[set1] == -1 || graph.edges.get(cheapest[set1]).weight > edge.weight)
                        cheapest[set1] = i;
                    if (cheapest[set2] == -1 || graph.edges.get(cheapest[set2]).weight > edge.weight)
                        cheapest[set2] = i;
                }
            }

            for (int i = 0; i < V; i++) {
                if (cheapest[i] != -1) {
                    Edge edge = graph.edges.get(cheapest[i]);
                    int set1 = ds.find(edge.src);
                    int set2 = ds.find(edge.dest);

                    if (set1 != set2) {
                        mstWeight += edge.weight;
                        ds.union(set1, set2);
                        numTrees--;
                    }
                }
            }
        }

        // Write the MST weight to the specified output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Minimum Spanning Tree Weight: " + mstWeight + "\n");
        }
    }

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select the input file for the graph");

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line = reader.readLine();
                String[] firstLine = line.split(" ");
                int V = Integer.parseInt(firstLine[0]);
                int E = Integer.parseInt(firstLine[1]);
                Graph graph = new Graph(V, E);

                for (int i = 0; i < E; i++) {
                    line = reader.readLine();
                    String[] edgeData = line.split(" ");
                    int src = Integer.parseInt(edgeData[0]);
                    int dest = Integer.parseInt(edgeData[1]);
                    int weight = Integer.parseInt(edgeData[2]);
                    graph.addEdge(src, dest, weight);
                }

                // Specify the output file name
                String outputFileName = "mst_output.txt";
                boruvkasMST(graph, outputFileName);
                System.out.println("MST result written to " + outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection canceled. Program will exit.");
        }
    }
}
