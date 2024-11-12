package Algorithm1;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FordFulkersonCityNetwork {

    static final int MAX_CITIES = 100;
    static int[][] capacityMatrix = new int[MAX_CITIES][MAX_CITIES];
    static Map<String, Integer> cityIndexMap = new HashMap<>();
    static String[] indexCityMap = new String[MAX_CITIES];

    // Read city network data from a CSV file
    public static void readCityNetwork(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String[] cities = br.readLine().split(",");

            // Map cities to indexes
            for (int i = 1; i < cities.length; i++) {
                cityIndexMap.put(cities[i], i - 1);
                indexCityMap[i - 1] = cities[i];
            }

            // Read the capacity matrix
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 1; col < values.length; col++) {
                    capacityMatrix[row][col - 1] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading the file. Please ensure it's in the correct CSV format.");
        }
    }

    // BFS for finding an augmenting path in the residual graph
    public static boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[MAX_CITIES];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < MAX_CITIES; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    queue.add(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }
        return visited[sink];
    }

    // Ford-Fulkerson algorithm to find the maximum flow
    public static int fordFulkerson(int source, int sink) {
        int u, v;
        int[][] residualGraph = new int[MAX_CITIES][MAX_CITIES];

        // Initialize residual graph with original capacities
        for (u = 0; u < MAX_CITIES; u++)
            System.arraycopy(capacityMatrix[u], 0, residualGraph[u], 0, MAX_CITIES);

        int[] parent = new int[MAX_CITIES];
        int maxFlow = 0;

        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (v = sink; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    // Write or display the maximum flow result
    public static void outputResult(String sourceCity, String sinkCity, int maxFlow, boolean toFile) throws IOException {
        String result = "Maximum flow from " + sourceCity + " to " + sinkCity + " is: " + maxFlow;

        if (toFile) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("fordOutput.txt"))) {
                writer.write(result);
                System.out.println("Result written to fordOutput.txt");
            }
        } else {
            System.out.println(result);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Open file chooser to select CSV file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int result = fileChooser.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("No file selected. Exiting program.");
            return;
        }

        String inputFileName = fileChooser.getSelectedFile().getAbsolutePath();

        // Load city network from the selected file
        readCityNetwork(inputFileName);

        // Display available cities
        System.out.println("Available cities:");
        cityIndexMap.keySet().forEach(System.out::println);

        // Get source and destination cities
        System.out.print("Enter source city: ");
        String sourceCity = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String sinkCity = scanner.nextLine();

        if (!cityIndexMap.containsKey(sourceCity) || !cityIndexMap.containsKey(sinkCity)) {
            System.out.println("Invalid cities. Exiting program.");
            return;
        }

        int source = cityIndexMap.get(sourceCity);
        int sink = cityIndexMap.get(sinkCity);

        // Compute maximum flow
        int maxFlow = fordFulkerson(source, sink);

        // Ask if output should be saved to a file
        System.out.print("Save result to a file? (yes/no): ");
        boolean toFile = scanner.nextLine().equalsIgnoreCase("yes");

        outputResult(sourceCity, sinkCity, maxFlow, toFile);
    }
}
