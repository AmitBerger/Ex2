import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphC implements DirectedWeightedGraph {
    private int nodeSize = 0;
    private int edgeSize = 0;
    private List<Node> NodeList;
    private List<Edge> EdgeList;
    private ArrayList[] g;

    public DirectedWeightedGraphC(List<NodeData> NodeL, List<EdgeData> EdgeL) {
        this.edgeSize = EdgeL.size();
        this.nodeSize = NodeL.size();
        this.EdgeList = new ArrayList<Edge>();
        this.NodeList = new ArrayList<Node>();
        this.g = new ArrayList[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            this.NodeList.add((Node)NodeL.get(i));
            g[i] = new ArrayList<Edge>();
        }
        for (int i = 0; i < edgeSize; i++) {
            this.EdgeList.add((Edge)EdgeL.get(i));
            Edge temp = this.EdgeList.get(i);
            g[temp.getSrc()].add(temp);
//            g[temp.getSrc()].add(temp);
            g[temp.getDest()].add(temp);
        }
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
        NodeList.add((Node) n);
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
