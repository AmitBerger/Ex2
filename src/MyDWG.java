import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class MyDWG implements DirectedWeightedGraph {

    HashMap<Integer, NodeData> nodeList;
    HashMap<Integer, HashMap<Integer, EdgeData>> edgeList;
    private int nodeSize;
    private int edgeSize;
    private int mc;

    public MyDWG() {
        this.nodeList = new HashMap<>();
        this.edgeList = new HashMap<>();
        this.nodeSize = 0;
        this.edgeSize = 0;
        this.mc = 0;
    }

    public MyDWG(DirectedWeightedGraph g) {
        this.nodeList = new HashMap<>();
        this.edgeList = new HashMap<>();
        Iterator<NodeData> nodeIter = g.nodeIter();
        Iterator<EdgeData> edgeIter = g.edgeIter();
        while (nodeIter.hasNext()){
            NodeData currentNode = nodeIter.next();
            this.nodeList.put(currentNode.getKey(),currentNode);
            HashMap<Integer,EdgeData> currEdgeHash = new HashMap<>();
            this.edgeList.put(currentNode.getKey(),currEdgeHash);
        }
        while (edgeIter.hasNext()){
            EdgeData currentEdge = edgeIter.next();
            this.edgeList.get(currentEdge.getSrc()).put(currentEdge.getDest(),currentEdge);
        }
        this.edgeSize = g.edgeSize();
        this.nodeSize = g.nodeSize();
        this.mc = g.getMC();
    }

    @Override
    public NodeData getNode(int key) {
        return (nodeList.isEmpty()) ? null : nodeList.get(key);
    }

    @Override
    public void addNode(NodeData n) {

        if (!this.nodeList.containsKey(n.getKey())) {
            this.nodeList.put(n.getKey(), n);
            nodeSize++;
            mc++;
        }
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
        return new Iterators.NodeIterator(this);
//        return (Iterator<NodeData>) this.nodeList.values();
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterators.EdgeIterator(this);
//        return (Iterator<EdgeData>) this.edgeList.values();                    // mistake needs to be treated
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterators.SpesificEdgeIterator(this , node_id);
//        return (Iterator<EdgeData>) this.edgeList.get(node_id).values();
    }

    @Override
    public NodeData removeNode(int key) {

        for (int node : this.edgeList.keySet()) {
            if (this.edgeList.get(node).containsKey(key)) {
                this.edgeList.get(node).remove(key);
            }
        }
        this.edgeList.remove(key);
        this.nodeSize--;
        this.mc++;
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

    public void setNodeSize(int newVal){
        nodeSize = newVal;
    }

    public void setEdgeSize(int newVal){
        this.edgeSize = newVal;
    }

    public void setMcSize(int newVal){
        this.mc = newVal;
    }
    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdgeList(){
        return this.edgeList;
    }
    public HashMap<Integer, EdgeData> getSpecificNodeEdges(int nodeKey){
        return this.edgeList.get(nodeKey);
    }

    @Override
    public String toString() {
        return "MyDWG{" +
                "nodeList=" + nodeList +
                ", edgeList=" + edgeList +
                ", nodeSize=" + nodeSize +
                ", edgeSize=" + edgeSize +
                ", mc=" + mc +
                '}';
    }
}

