import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.EdgeData;
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
//  Took the idea from an implementation we did last year, for printing a tree (up to down).
//  In this way we can go throw every node child by order.
//  Which is perfect for this function! only here we will play with the tag to achieve our goal, instead of printing.
    public boolean isConnected() {
        /* Our strategy -> pick a starting node and put if in a queue. Then run in a loop until the queue is empty and
         * Remove the head and go throw all the nodes he is connected with one by one. for each,
         *  add the current node to the queue, and change its tag to "isConneted".
         *  In the end only the tag of the nodes we have seen in the process will show "IsConnected".
         * Last, check if all  the nodes tags show "IsConnected"  */
        if (this.graph.nodeSize() < 0) {
            return false;
        } else if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1) {
            return true;
        }
        Queue<NodeData> NodeQueue = new LinkedList<>();
        Queue<NodeData> EdgeQueue = new LinkedList<>();
        for (NodeData n : this.graph.nodeList.values()) {
            n.setTag(NotYetConnected);
        }
//     The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
//     In addition, it doesn't meter which node will be the starting one.
        NodeQueue.add(this.graph.nodeList.get(0));
        while (!NodeQueue.isEmpty()) {
            NodeData currentNode = NodeQueue.remove();
            for (NodeData n: this.graph.edgeList.get(currentNode.getKey()).keySet()) {
                NodeQueue.add(n);
                n.setTag(IsConnected);
            }
        }
        for (NodeData n : this.graph.nodeList.values()) {
            if (n.getTag() == NotYetConnected) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {              // in progress
        if (this.graph.nodeSize() < 2) {
            return -1;
        }
        for (int i = 0; i < this.graph.nodeSize(); i++) {
            this.graph.nodeList.get(i).setTag(NotYetConnected);
        }
        double[] allPaths = new double[this.graph.nodeList.size()];
        Queue<NodeData> queue = new LinkedList<>();
     /* The next line is legal because in the beginning we confirmed that our graph nodeSize > 1.
        Started with first element of the nodeList because, there is no preference to start with a specific node */
        queue.add(this.graph.nodeList.get(0));
        while (!queue.isEmpty()) {
            NodeData currentNode = queue.remove();
            for (NodeData n : this.graph.nodeList.values()) {
                queue.add(n);
                n.setTag(IsConnected);
            }

        }
        double shortestPath = Double.MAX_VALUE;
        for (int i = 0; i < allPaths.length; i++) {
            if (allPaths[i] < shortestPath) {
                shortestPath = allPaths[i];
            }
        }
        return (shortestPath == 0)? -1 : shortestPath;
    }
        @Override
        public List<NodeData> shortestPath ( int src, int dest){
            return null;
        }

        @Override
        public NodeData center () {
            return null;
        }

        @Override
        public List<NodeData> tsp (List < NodeData > cities) {
            return null;
        }

        @Override
        public boolean save (String file){
            return false;
        }

        @Override
        public boolean load (String file){
            return false;
        }
    }

}