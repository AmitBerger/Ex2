import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;


import java.util.*;

public class DirectedWeightedGraphC implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> NodeList;
    private HashMap<ArrayList<Integer>, EdgeData> EdgeList;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public DirectedWeightedGraphC() {
        this.NodeList = new HashMap<>();
        this.EdgeList = new HashMap<>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mc = 0;
    }

    @Override
    public NodeData getNode(int key) {
        return (NodeList.isEmpty()) ? null : NodeList.get(key);
    }

    @Override
    public void addNode(NodeData n) {

        if (NodeList.containsKey(n.getKey())) {
            NodeList.replace(n.getKey(), n);
        } else {
            NodeList.put(n.getKey(), n);
            nodeSize++;
        }
        mc++;
    }


    @Override
    public EdgeData getEdge(int src, int dest) {
        ArrayList<Integer> requestedEdge = new ArrayList<Integer>();
        requestedEdge.add(src);
        requestedEdge.add(dest);
        return EdgeList.getOrDefault(requestedEdge, null);
    }

    @Override
    public void connect(int src, int dest, double w) {
        NodeData srcNode = getNode(src);
        NodeData dstNode = getNode(dest);
//      Assuming there can be an edge from one node to itself
        if (srcNode == null || dstNode == null) {
            return;
        }
        ArrayList<Integer> givenEdgeKey = new ArrayList<Integer>();
        givenEdgeKey.add(src);
        givenEdgeKey.add(dest);
        Edge givenEdge = new Edge(src, dest, w);
        EdgeList.put(givenEdgeKey, givenEdge);
        edgeSize++;
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return NodeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return EdgeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return NodeList.size();
    }

    @Override
    public int edgeSize() {
        return EdgeList.size();
    }

    @Override
    public int getMC() {
        return 0;
    }


}
