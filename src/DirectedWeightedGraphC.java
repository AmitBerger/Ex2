import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import java.util.*;

public class DirectedWeightedGraphC implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodeList;
    HashMap<ArrayList<Integer>, EdgeData> edgeList;
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
        for (NodeData n: g.nodeList.values()) {
            this.nodeList.put(n.getKey(), new Node(n));
            for (EdgeData e: g.edgeList.values()) {
                ArrayList<Integer> currentKey = new ArrayList<>();
                currentKey.add(e.getSrc());
                currentKey.add(e.getDest());
                this.edgeList.put(currentKey,e);
            }
        }
        this.nodeSize = g.nodeSize;
        this.edgeSize = g.edgeSize;
        this.mc = g.getMC();
    }

    @Override
    public NodeData getNode(int key)
    {
        return (nodeList.isEmpty()) ? null : nodeList.get(key);
    }

    @Override
    public void addNode(NodeData n) {

        if (nodeList.containsKey(n.getKey()))
        {
            nodeList.replace(n.getKey(), n);
        }
        else
        {
            nodeList.put(n.getKey(), n);
            nodeSize++;
        }
        mc++;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        ArrayList<Integer> requestedEdge = new ArrayList<>();
        requestedEdge.add(src);
        requestedEdge.add(dest);
        return edgeList.getOrDefault(requestedEdge, null);
    }

    @Override
    public void connect(int src, int dest, double w) {

        ArrayList<Integer> givenEdgeKey = new ArrayList<>();
        givenEdgeKey.add(src);
        givenEdgeKey.add(dest);

        NodeData srcNode = getNode(src);
        NodeData dstNode = getNode(dest);
//      Assuming there can be an edge from one node to itself
        if (srcNode == null || dstNode == null)
        {
            return;
        }
        Edge givenEdge = new Edge(src, dest, w);
        edgeList.put(givenEdgeKey, givenEdge);
        edgeSize++;
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return nodeList.values().iterator();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return edgeList.values().iterator();
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
        ArrayList<Integer> needToBeRemoved = new ArrayList<>();
        needToBeRemoved.add(src);
        needToBeRemoved.add(dest);
        this.edgeSize--;
        this.mc++;
        return this.edgeList.remove(needToBeRemoved);
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
