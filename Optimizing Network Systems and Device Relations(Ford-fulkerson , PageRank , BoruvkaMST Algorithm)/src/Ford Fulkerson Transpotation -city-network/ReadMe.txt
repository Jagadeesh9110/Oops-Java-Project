                               Ford-Fulkerson Algorithm
#Group Members:

M Jagadeeswar Reddy
B Harsha Vardhan
Tejaswi A.C

Algorithm:  Ford-Fulkerson Algorithm - Transportation City Network
Contributor:  B. Harsha Vardhan

Overview:

The Ford-Fulkerson algorithm is used to compute the maximum flow in a network by iteratively finding augmenting paths from the source to the sink. The algorithm adjusts flow values along these paths until no further increase is possible.

Requirements:

Basic knowledge of graph theory and network flow.
A CSV file containing city network data with connections and their respective capacities.
Java JDK for compilation and execution.
Proper CSV formatting to match the network structure.

Methodology:

Initialize the network with zero flow and create a residual graph.
Use Breadth-First Search (BFS) to find augmenting paths from source to sink.
Calculate the minimum capacity along each path (path flow) and update the residual graph accordingly.
Repeat the process until no more augmenting paths are found, yielding the maximum flow.

Usage Instructions:

Prepare a CSV file containing city connections and their respective capacities (first row should have city names, excluding the source city).
Run the program with the CSV file as input.
The result will be saved in fordOutput.txt with the format:
"Maximum flow from [Source City] to [Sink City]: [Flow Value]"

Conclusion:

The Ford-Fulkerson algorithm effectively solves the Maximum Flow problem by iteratively finding augmenting paths and adjusting the flow. It ensures the maximum possible flow between a source and a sink. While efficient, its performance can be improved with advanced methods such as the Edmonds-Karp algorithm. This algorithm is widely applicable in real-world scenarios such as transportation, communication networks, and traffic flow analysis, making it a key tool in network optimization.