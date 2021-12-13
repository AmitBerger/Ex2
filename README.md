# DIRECTED-WEIGHTED-GRAPH:

**Authors:** Orel Zamler && Amit Berger 

****************************Description:****************************

In this project we implemented an infrastructure of a directed weighted graph.
Given a json file containing a specific graph data: list of Nodes/Vertexs and Edges.

The project is divided into two main class:

1. Class Implementation.MyDWG which holds the graph.
2. Class Implementation.MyDWGAlgorithm, in charge of the graph functions.

****************************incentive:****************************

Imagine the nodes as coordinates on a map, and the edges as a path between those two.


# Graph:

The DirectionalWeightedGraph interface is implemented in Implementation.MyDWGAlgorithm class.

**Class mission** -> hold the given nodes and edges data.

**Implementation.MyDWG data structure:**

Its nodes and edges are stored in HashMaps.
The edges HashMap contains an inner HashMap. Together, both keys represent the source and destination of a given edge.

There are some functions for our usage as well:
 * getters && setters
 * adding/removing a node/edge
 * connect - builds an edge
 * Iterators

**Implementation.Node:**

The NodeData interface is implemented in Implementation.Node class.
This class represents the set of operations applicable on a node/vertex in a directional weighted graph.
Each node has a unique id number and a location("coordinates on a map").

**Implementation.Edge:**
The EdgeData interface is implemented in Implementation.Edge class.
This class represents the set of operations applicable on a directional edge.
Every edge has its own source, destination (nodes) and weight.
The weight represents the cost of arrival from the source vertex to the destination vertex.
There are no negative weights nor edge from vertex to itself.

# Algorithm:

The DirectedWeightedGraphAlgorithms interface is implemented in Implementation.MyDWGAlgorithm class.
Which has its own graph object.

**class mission** -> implements functions that operates on the graph.

**abilities:** 
 * copy(Deep).
 * init - given a graph, initialize.
 * isConnected -  Check if there is valid path from each node to the others.
 * shortestPathDist - returns the weight of the shortest path between two Nodes.
 * shortestPath - returns the actual shortest path(Nodes).
 * center - Finds the NodeData which minimizes the max distance to all the other nodes.
 * tsp - Computes a list of consecutive nodes which go over all the nodes in cities.
 * save - Saves the graph info into a json file.
 * load - Loads a graph info from given json file.


**Example of a json file contant:**
{
  "Edges": [{"src": 0, "w": 1.3, "dest": 4}, {"src": 1, "w": 1.2, "dest": 0}, {"src": 1, "w": 1.8, "dest": 4}, {"src": 2, "w": 1.7, "dest": 1}, {"src": 3, "w": 1.5, "dest": 2}, {"src": 3, "w": 1, "dest": 0}, {"src": 4, "w": 4, "dest": 2}, {"src": 4, "w": 1.4, "dest": 3}],
  
  "Nodes": [{"pos": "35.19589389346247,32.10152879327731,0.0", "id": 0}, {"pos": "35.20319591121872,32.10318254621849,0.0", "id": 1}, {"pos": "35.20752617756255,32.1025646605042,0.0", "id": 2}, {"pos": "35.21007339305892,32.10107446554622,0.0", "id": 3}, {"pos": "35.21310882485876,32.104636394957986,0.0", "id": 4}]
}

**api uml:**

![api_uml](https://user-images.githubusercontent.com/93476230/145725578-b1b78cee-4eeb-4fa9-98b2-e30f34a2ebba.png)

**GUI uml:**

![GUI_uml](https://user-images.githubusercontent.com/93476230/145725588-65d97540-85ac-4016-b3b6-7bd529885b8f.png)


**Results:**

![Ex2_res](https://user-images.githubusercontent.com/93476230/145724684-a8b8d5a8-1a85-4fe8-8f75-26e00522d16e.png)

**GUI Example for G1.json**

![Ex2_G1_load](https://user-images.githubusercontent.com/93476230/145724724-8c66ca2d-34c2-4c56-aced-2f045b394c83.png)


**How to run my code:**

In the commend line in this format: java -jar (path)Ex2.jar G1.json 

*Example:*

![How to run my code example](https://user-images.githubusercontent.com/93476230/145724659-4aabc35d-4eec-4d08-a1ff-82c9a06752e3.png)


