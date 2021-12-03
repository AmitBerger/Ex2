
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
import api.NodeData;

import java.io.*;
import java.util.*;

public class MyDWGAlgorithm implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph graph;
    int IsConnected = 1;
    int NotYetConnected = 0;

    public MyDWGAlgorithm() {
        this.graph = new MyDWG();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new MyDWG(this.graph);
    }

    @Override
//  Took the idea from an implementation we did last year, for printing a tree (up to down).
//  In this way we can go throw every node child by order.
//  Which is perfect for this function! only here we will play with the tag to achieve our goal, instead of printing.
    public boolean isConnected() {
        NodeData someNode = this.getGraph().nodeIter().next();
        boolean oneWayConnected = isNodeConnected(this.graph, someNode);
        if (!oneWayConnected) {
            return false;
        }
        MyDWG reverseGraph = reverseGraph();
        return isNodeConnected(reverseGraph, someNode);
    }

    public boolean isNodeConnected(DirectedWeightedGraph g, NodeData specificNode) {
        /* Our strategy -> pick a starting node and put if in a queue. Then run in a loop until the queue is empty and
         * Remove the head and go throw all the nodes he is connected with one by one. for each,
         *  add the current node to the queue, and change its tag to "isConneted".
         *  In the end only the tag of the nodes we have seen in the process will show "IsConnected".
         * Last, check if all  the nodes tags show "IsConnected"  */
        if (g.nodeSize() < 0) {
            return false;
        } else if (g.nodeSize() == 0 || g.nodeSize() == 1) {
            return true;
        }
        Queue<NodeData> NodeQueue = new LinkedList<>();
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData n = nodeIter.next();
            n.setTag(NotYetConnected);
        }
//     The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
//     In addition, it doesn't meter which node will be the starting one.
        NodeQueue.add(specificNode);
        while (!NodeQueue.isEmpty()) {
            NodeData currentNode = NodeQueue.remove();

            Iterator<EdgeData> edgeIter = g.edgeIter(currentNode.getKey());
            while (edgeIter.hasNext()) {
                EdgeData currentEdge = edgeIter.next();
                NodeData currentEdgeNode = g.getNode(currentEdge.getDest());
                NodeQueue.add(currentEdgeNode);
                currentEdgeNode.setTag(IsConnected);
            }
        }
        nodeIter = g.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            if (currentNode.getTag() == NotYetConnected) {
                return false;
            }
        }
        return true;
    }

    private MyDWG reverseGraph() {
        MyDWG newGrph = new MyDWG();
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            newGrph.nodeList.put(currentNode.getKey(), currentNode);
        }
        Iterator<EdgeData> edgeIter = this.graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData currentEdge = edgeIter.next();
            HashMap<Integer,EdgeData> reversedEdge = new HashMap<>();
            reversedEdge.put(currentEdge.getSrc(),currentEdge);
            newGrph.edgeList.put(currentEdge.getDest(),reversedEdge);
        }
        return newGrph;
    }

    @Override
    public double shortestPathDist(int src, int dest) {              // in progress
        if (this.graph.nodeSize() < 2) {
            return -1;
        }
        HashMap<Integer, Double> allPaths = new HashMap<>();
        Queue<NodeData> NodeQueue = new LinkedList<>();
     /* The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
        Started with first element of the nodeList because, there is no preference to start with a specific node */
        Iterator<NodeData> nodeIter = this.graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData n = nodeIter.next();
            n.setTag(NotYetConnected);
            allPaths.put(n.getKey(), 0.0);
        }
//     The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
//     In addition, it doesn't meter which node will be the starting one.
        NodeQueue.add(this.graph.getNode(src));
        while (!NodeQueue.isEmpty()) {
            NodeData currentNode = NodeQueue.remove();
            boolean flag = false;

            Iterator<EdgeData> edgeIter = this.graph.edgeIter(currentNode.getKey());
            while (edgeIter.hasNext() && !flag) {
                EdgeData currentEdge = edgeIter.next();
                NodeData currentEdgeNode = this.graph.getNode(currentEdge.getDest());
                NodeQueue.add(currentEdgeNode);
                Double currentWight = allPaths.get(currentEdgeNode.getKey());
                allPaths.put(currentNode.getKey(), (currentWight + currentEdge.getWeight()));
                flag = (currentEdgeNode.getKey() == dest);
                currentEdgeNode.setTag(IsConnected);
            }
        }
        double shortestPath = Double.MAX_VALUE;
        for (int i = 0; i < allPaths.size(); i++) {
            int currentNodeTag = this.graph.getNode(i).getTag();
            if ((currentNodeTag == IsConnected) && allPaths.get(i) < shortestPath) {
                shortestPath = allPaths.get(i);
            }
        }
        return (shortestPath == 0.0) ? -1 : shortestPath;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    public static void main(String[] args) {
        MyDWGAlgorithm g = new MyDWGAlgorithm();
        g.load("G1.json");
    }
}

