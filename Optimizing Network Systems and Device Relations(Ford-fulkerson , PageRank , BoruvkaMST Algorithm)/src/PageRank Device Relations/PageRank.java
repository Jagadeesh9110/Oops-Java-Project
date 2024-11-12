package Algorithm2;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PageRank {

    public static Map<String, Double> calculatePageRank(Map<String, List<String>> graph, double dampingFactor, int maxIterations, double tolerance) {
        int N = graph.size();  // Total number of pages in the graph
        Map<String, Double> pageRank = new HashMap<>();

        // Initialize all pages with an initial rank of 1 / N
        for (String page : graph.keySet()) {
            pageRank.put(page, 1.0 / N);
        }

        // Perform iterations to update PageRank values`
        for (int i = 0; i < maxIterations; i++) {
            Map<String, Double> newPageRank = new HashMap<>();

            // Iterate over each page in the graph
            for (String page : graph.keySet()) {
                double rankSum = 0.0;

                // For each inbound page (pages that link to the current page)
                for (String inboundPage : graph.keySet()) {
                    if (graph.get(inboundPage).contains(page)) {
                        rankSum += pageRank.get(inboundPage) / graph.get(inboundPage).size();
                    }
                }

                // Update the new PageRank for the current page
                double newRank = (1 - dampingFactor) / N + dampingFactor * rankSum;
                newPageRank.put(page, newRank);
            }

            // Check if the PageRank has converged
            double delta = 0.0;
            for (String page : pageRank.keySet()) {
                delta += Math.abs(newPageRank.get(page) - pageRank.get(page));
            }

            // If the change is smaller than tolerance, the algorithm has converged
            if (delta < tolerance) {
                break;
            }

            pageRank = newPageRank;  // Update the PageRank values
        }

        return pageRank;
    }

    // Method to read the graph from the input file with your specific format
    public static Map<String, List<String>> readGraphFromFile(String filePath) throws IOException {
        Map<String, List<String>> graph = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Reading the file and parsing the graph
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("->");
            if (parts.length < 2) continue; // Skip malformed lines
            String page = parts[0].trim();
            String[] links = parts[1].split(",");
            List<String> linkedPages = new ArrayList<>();
            for (String link : links) {
                linkedPages.add(link.trim());
            }
            graph.put(page, linkedPages);
        }

        reader.close();
        return graph;
    }

    // Method to write the PageRank results to the output file
    public static void writePageRankToFile(Map<String, Double> pageRank, String outputFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

        // Writing the PageRank values to the file
        for (Map.Entry<String, Double> entry : pageRank.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        writer.close();

    }

    public static void main(String[] args) {
        // Use JFileChooser to select the input file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select the graph file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String inputFileName = selectedFile.getAbsolutePath();
            String outputFileName = "pageOutput.txt";  // Output file name

            try {
                // Read the graph from the selected input file
                Map<String, List<String>> graph = readGraphFromFile(inputFileName);

                // Parameters for the PageRank algorithm
                double dampingFactor = 0.85;
                int maxIterations = 100;
                double tolerance = 1.0e-6;

                // Calculate PageRank
                Map<String, Double> pageRank = calculatePageRank(graph, dampingFactor, maxIterations, tolerance);

                // Write the PageRank results to the output file
                writePageRankToFile(pageRank, outputFileName);

                System.out.println("PageRank values written to: " + outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error reading or writing files.");
            }
        } else {
            System.out.println("No file selected.");
        }
    }
}


