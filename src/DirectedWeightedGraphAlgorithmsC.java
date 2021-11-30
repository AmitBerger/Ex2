import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DirectedWeightedGraphAlgorithmsC implements DirectedWeightedGraphAlgorithms {

    DirectedWeightedGraphC graph;
    int IsConnected = 1;
    int NotYetConnected = 0;

    public DirectedWeightedGraphAlgorithmsC() {
        this.graph = new DirectedWeightedGraphC();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DirectedWeightedGraphC) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DirectedWeightedGraphC(this.graph);
    }

    @Override
//  Took the idea from an implementation we did last year for printing a tree (up to down).
//  In this way we can go throw every node child by order.
//  Which is perfect for this function! only here we play with the tag to achieve our goal instead of printing.
    public boolean isConnected() {
        if (this.graph.nodeSize() < 0) {
            return false;
        } else if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1) {
            return true;
        }
        for (int i = 0; i < this.graph.nodeSize(); i++) {
            this.graph.nodeList.get(i).setTag(NotYetConnected);
        }
        Queue<NodeData> queue = new LinkedList<>();
     /* The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
        Added the first element of the nodeList to the queue because,
        there is no preference to start with a specific node */
        queue.add(this.graph.nodeList.get(0));
        while (!queue.isEmpty()){
        /* The next line returns the NodeData that was removed, but we have no use for it here.
            Unlike the original idea for printing the tree */
            queue.remove();
            for (NodeData n: this.graph.nodeList.values()) {
                queue.add(n);
                n.setTag(IsConnected);
            }
        }
        for (NodeData n: this.graph.nodeList.values()) {
            if (n.getTag() == NotYetConnected){
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
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
}

}