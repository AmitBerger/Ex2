import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class myDWG implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodeList;
    HashMap<Integer, HashMap<Integer, EdgeData>> edgeList;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public myDWG() {
        nodeList = new HashMap<>();
        edgeList = new HashMap<>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mc = 0;
    }

    public myDWG(myDWG g) {
        for (int node : g.nodeList.keySet()) {
            this.nodeList.put(node, g.nodeList.get(node));
            this.nodeSize++;
        }
        for (int node : g.edgeList.keySet()) {
            this.edgeList.put(node, g.edgeList.get(node));
            this.edgeSize++;
        }
        this.mc = g.getMC();
    }

    @Override
    public NodeData getNode(int key) {
        return (nodeList.isEmpty()) ? null : nodeList.get(key);
    }

    @Override
    public void addNode(NodeData n) {

        if (nodeList.containsKey(n.getKey())) {
            nodeList.replace(n.getKey(), n);
        } else {
            nodeList.put(n.getKey(), n);
            nodeSize++;
        }
        mc++;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.edgeList.get(src).get(dest);
    }

    @Override
    public void connect(int src, int dest, double w) {

        NodeData srcNode = getNode(src);
        NodeData dstNode = getNode(dest);
        Edge givenEdge = new Edge(src, dest, w);
//      Assuming there can be an edge from one node to itself
        if (srcNode == null || dstNode == null) {
            return;
        }
        HashMap<Integer, EdgeData> newEdge = new HashMap<>();
        newEdge.put(dest, givenEdge);
        this.edgeList.put(src, newEdge);
        edgeSize++;
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return (Iterator<NodeData>) this.nodeList.values();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return (Iterator<EdgeData>) this.edgeList.values();                    // mistake needs to be treated
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return (Iterator<EdgeData>) this.edgeList.get(node_id).values();
    }

    @Override
    public NodeData removeNode(int key) {
        this.nodeSize--;
        this.mc++;
        this.edgeList.remove(key);
        for (int node : this.edgeList.keySet()) {
            if (this.edgeList.get(node).containsKey(key)) {
                this.edgeList.get(node).remove(key);
            }
        }
//      The remove func returns null if the object is not found
        return this.nodeList.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        this.edgeSize--;
        this.mc++;
        //      The remove func returns null if the object is not found
        return this.edgeList.get(src).remove(dest);
    }

    @Override
    public int nodeSize() {
        return nodeList.size();
    }

    @Override
    public int edgeSize() {
        return edgeList.size();
    }

    @Override
    public int getMC() {
        return this.mc;
    }
}
