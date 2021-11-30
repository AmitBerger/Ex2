import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DirectedWeightedGraphAlgorithmsC implements DirectedWeightedGraphAlgorithms {

    DirectedWeightedGraphC graph;

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
        return (DirectedWeightedGraph) new DirectedWeightedGraphC(this.graph);
    }

    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() < 0) {
            return false;
        } else if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1) {
            return true;
        }
        for (int i = 0; i < this.graph.nodeSize(); i++) {
            this.graph.nodeList.get(i).setTag(0);
        }
        Queue<NodeData> queue = new LinkedList<>();

        return false;
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
