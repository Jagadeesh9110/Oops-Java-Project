                                  Boruvka's Algorithm
#Group Members:

M Jagadeeswar Reddy
B Harsha Vardhan
Tejaswi A.C

Algorithm:  Boruvka's Algorithm - Graph with Vertices and Edges
Contributor: Tejaswi A.C

Overview:

Boruvka's algorithm is used to find the Minimum Spanning Tree (MST) of a graph. It works by iteratively finding the cheapest edge for each component of the graph and merging the components until all vertices are connected.

Requirements:

Basic knowledge of graph theory and Minimum Spanning Tree (MST).
A graph with vertices and edges, where each edge has a weight (representing cost).
Java JDK for compilation and execution.
Properly formatted graph data representing vertices and edges.

Methodology:

Initialize each vertex as a separate component.
Find the minimum weight edge for each component and add it to the MST.
Merge the components connected by the chosen edges.
Repeat until all vertices are connected by the MST.

Usage Instructions:

Prepare a dataset in the form of a graph, where each edge has a weight (cost), and vertices are numbered.
Run the program with the graph dataset as input.
The output will be saved in boruvkaOutput.txt, showing the edges in the Minimum Spanning Tree (MST).

Conclusion:

Boruvka's algorithm is an efficient method for finding the Minimum Spanning Tree of a graph. By iteratively choosing the cheapest edges and merging components, it ensures that the minimum cost tree is found. It is a foundational algorithm in network design, used in applications like optimizing road networks, electrical grids, and computer networks.