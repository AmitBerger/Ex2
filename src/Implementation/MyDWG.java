package Implementation;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        while (nodeIter.hasNext()) {
            NodeData currentNode = nodeIter.next();
            this.nodeList.put(currentNode.getKey(), currentNode);
            HashMap<Integer, EdgeData> currEdgeHash = new HashMap<>();
            this.edgeList.put(currentNode.getKey(), currEdgeHash);
        }
        while (edgeIter.hasNext()) {
            EdgeData currentEdge = edgeIter.next();
            this.edgeList.get(currentEdge.getSrc()).put(currentEdge.getDest(), currentEdge);
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
        if (this.edgeList.containsKey(src) && this.edgeList.get(src).containsKey(dest)) {
            return this.edgeList.get(src).get(dest);
        }
        return null;
    }

    @Override
    public void connect(int src, int dest, double w) {

        NodeData srcNode = getNode(src);
        NodeData dstNode = getNode(dest);
//      Assuming there can be an edge from one node to itself
        if (srcNode == null || dstNode == null) {
            return;
        }
        Edge givenEdge = new Edge(src, dest, w);
        if (!this.edgeList.containsKey(src)) {
            HashMap<Integer, EdgeData> newEdge = new HashMap<>();
            newEdge.put(dest, givenEdge);
            this.edgeList.put(src, newEdge);
        } else {
            if (this.edgeList.get(src).containsKey(dest)) {
                this.edgeSize--;
            }
            this.edgeList.get(src).put(dest, givenEdge);
        }

        this.nodeList.get(src).setWeight(this.nodeList.get(src).getWeight() + 1);       // need to fix always increasing
        this.edgeSize++;
        mc++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<NodeData>() {
            Iterator<NodeData> nodeIter = nodeList.values().iterator();
            private int errorChecker = getMC();
            private NodeData currentPos = null;
            private NodeData lastPos = null;

            @Override
            public boolean hasNext() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                return nodeIter.hasNext();
            }

            @Override
            public NodeData next() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                lastPos = currentPos;
                currentPos = nodeIter.next();
                return currentPos;
            }

            @Override
            public void remove() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                // Get the last Pos:
                if (currentPos != null) {
                    Iterator<NodeData> newIter = nodeList.values().iterator();
                    NodeData i = null;
                    while (newIter.hasNext() && i != currentPos) { //for case of remove
                        lastPos = i;
                        i = newIter.next();
                    }
                    // Now we have the last pos!
                    removeNode(currentPos.getKey());
                    i = null;
                    // Now lets Creat a new iterator that doest have the removed value
                    nodeIter = nodeList.values().iterator();
                    // Restore the position
                    while (nodeIter.hasNext() && i != lastPos) { //for case of remove
                        i = nodeIter.next();
                    }
                    currentPos = i;
                }
                else{
                    throw new RuntimeException("Need to move forward in order to remove the next element");
                }
                errorChecker = getMC();
            }
        };
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<Integer> SrcKeySet = edgeList.keySet().iterator();
            Iterator<EdgeData> currentNodeEdges = edgeIter(SrcKeySet.next());
            private int errorChecker = getMC();

            @Override
            public boolean hasNext() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                if (!currentNodeEdges.hasNext()) {
                    if (!SrcKeySet.hasNext()) {
                        return false;
                    } else {
                        int currentKeyPos = SrcKeySet.next();
                        currentNodeEdges = edgeIter(currentKeyPos);
                    }
                }
                return currentNodeEdges.hasNext();
            }

            @Override
            public EdgeData next() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                return currentNodeEdges.next();
            }

            @Override
            public void remove() {
                if (errorChecker != getMC()) {
                    throw new RuntimeException("A change in the graph accord");
                }
                // Run time exp will be sent in the currentNodeEdges iterator if necessary!
                currentNodeEdges.remove();
                // Updating the errorChecker that we won't get a runTimeExp.
                errorChecker = getMC();
            }
        };
    }

@Override
public Iterator<EdgeData> edgeIter(int node_id) {
    return new Iterator<EdgeData>() {
        Iterator<EdgeData> edgeIter = edgeList.get(node_id).values().iterator();
        private EdgeData currentPos = null;
        private EdgeData lastPos = null;
        private int errorChecker = getMC();

        @Override
        public boolean hasNext() {
            if (errorChecker != getMC()) {
                throw new RuntimeException("A change in the graph accord");
            }
            return edgeIter.hasNext();
        }

        @Override
        public EdgeData next() {
            if (errorChecker != getMC()) {
                throw new RuntimeException("A change in the graph accord");
            }
            currentPos = edgeIter.next();
            return currentPos;
        }

        @Override
        public void remove() {
            EdgeData i = null;
            if (errorChecker != getMC()) {
                throw new RuntimeException("A change in the graph accord");
            } else if (currentPos != null) {
                Iterator<EdgeData> iterLast = edgeList.get(node_id).values().iterator();
                i = null;
                while (iterLast.hasNext() && i != currentPos) { //go to last
                    lastPos = i;
                    i = iterLast.next();
                }
                removeEdge(currentPos.getSrc(), currentPos.getDest());
                edgeIter = edgeList.get(node_id).values().iterator();
                i = null;
                while (edgeIter.hasNext() && i != lastPos) { //go to new current
                    i = edgeIter.next();
                }
                currentPos = i;
                errorChecker = getMC();
            }
            else{
                throw new RuntimeException("Need to move forward in order to remove the next element");
            }
        }
    };
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
        return this.edgeSize;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    public HashMap<Integer, HashMap<Integer, EdgeData>> getEdgeList() {
        return this.edgeList;
    }

    public HashMap<Integer, NodeData> getNodeList() {
        return this.nodeList;
    }

    @Override
    public String toString() {
        return "Implementation.MyDWG{" +
                "nodeList=" + nodeList +
                ", edgeList=" + edgeList +
                ", nodeSize=" + nodeSize +
                ", edgeSize=" + edgeSize +
                ", mc=" + mc +
                '}';
    }
}

