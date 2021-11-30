import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;

public class DirectedWeightedGraphC implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodeList;
    HashMap<Integer, HashMap<NodeData, EdgeData>> edgeList;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public DirectedWeightedGraphC() {
        nodeList = new HashMap<>();
        edgeList = new HashMap<>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mc = 0;
    }

    public DirectedWeightedGraphC(DirectedWeightedGraphC g) {
        for (NodeData n : g.nodeList.values()) {
            this.nodeList.put(n.getKey(), new Node(n));
            this.nodeSize++;

            for (HashMap<NodeData,EdgeData> h : g.edgeList.values()) {
                this.edgeList.put(n.getKey(),h);
                this.edgeSize++;
            }
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
        NodeData srcNode = getNode(src);
        return this.edgeList.get(srcNode.getKey()).get(srcNode);
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
        HashMap<NodeData,EdgeData> newEdge = new HashMap<>();
        newEdge.put(srcNode,givenEdge);
        this.edgeList.put(srcNode.getKey(), newEdge);
        edgeList.put(dstNode.getKey(), newEdge);
        edgeSize++;
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return this.edgeList.get(0).values().iterator();                    // mistake needs to be treated
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Node desiredNode = (Node) nodeList.get(node_id);
        return desiredNode.Edges.values().iterator();
    }

    @Override
    public NodeData removeNode(int key) {
        this.nodeSize--;
        this.mc++;
//      The remove func returns null if the object is not found
        return this.nodeList.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        this.edgeSize--;
        this.mc++;
        this.edgeList.get(src).remove(this.nodeList.get(src));
        return this.edgeList.get(dest).remove(this.nodeList.get(dest));
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
