import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphC implements DirectedWeightedGraph {
    private int nodeSize = 0;
    private int edgeSize = 0;
    private List<NodeData> NodeList;
    private List<EdgeData> EdgeList;

    public DirectedWeightedGraphC(List<NodeData> NodeL, List<EdgeData> EdgeL) {
        this.NodeList = NodeL;
        this.EdgeList = EdgeL;
    }

    @Override
    public NodeData getNode(int key) {
        if (NodeList.isEmpty())
            return null;
        return NodeList.get(key);
    }

    @Override
    public void addNode(NodeData n) {
        for (int i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).getKey() == n.getKey()) {
                System.out.println("The node already exist!");
                return;
            }
        }
        nodeSize++;
        NodeList.add(n);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        NodeList.get(src).getKey();
        NodeList.get(dest).getKey();
        return null;
    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
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
