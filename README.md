# DIRECTED-WEIGHTED-GRAPH:

*Authors:* Orel Zamler && Amit Berger 
****************************Description:****************************
In this project we implemented an infrastructure of a directed weighted graph.

Given a json file containing a specific graph data: list of Nodes and Edges.
It can do as follows:

The project is divided into two main class:
1. Class MyDWG which holds the graph.
2. Class MyDWGAlgorithm, in charge of the graph functions.

****************************incentive****************************
Imagine the nodes as coordinates on a map, and the edges as a path between those two.

****************************implementation****************************

***Graph:***

The DirectionalWeightedGraph interface is implemented in MyDWGAlgorithm class:
Class mission -> hold the given nodes and edges data.
MyDWG data structure:
Its nodes and edges are stored in HashMaps.
The edges HashMap contains an inner HashMap. Together, both keys represent the source and destination of a given edge.
There are some functions for our usage as well:
    * getters && setters
    * adding/removing a node/edge
    * connect - builds an edge
    * Iterators

Node:
The NodeData interface is implemented in Node class.
This class represents the set of operations applicable on a node/vertex in a directional weighted graph.
Each node has a unique id number and a location("coordinates on a map").

Edge:
The EdgeData interface is implemented in Edge class.
This class represents the set of operations applicable on a directional edge.
Every edge has its own source, destination (nodes) and weight.
The weight represents the cost of arrival from the source vertex to the destination vertex.
There are no negative weights nor edge from vertex to itself.

***Algorithm:***

The DirectedWeightedGraphAlgorithms interface is implemented in MyDWGAlgorithm class.
Which has its own graph object.
class mission -> implements functions that operates on the graph.

abilities:
    * copy(Deep).
    * init - given a graph, initialize.
    * isConnected -  Check if there is valid path from each node to the others.
    * shortestPathDist - returns the weight of the shortest path between two Nodes.
    * shortestPath - returns the actual shortest path(Nodes).
    * center - Finds the NodeData which minimizes the max distance to all the other nodes.
    * tsp - Computes a list of consecutive nodes which go over all the nodes in cities.
    * save - Saves the graph info into a json file.
    * load - Loads a graph info from given json file.
