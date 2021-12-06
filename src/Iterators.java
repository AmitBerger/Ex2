import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class Iterators {

    public static class NodeIterator implements Iterator<NodeData> {

        MyDWG graph;
        int currentPos;

        public NodeIterator(MyDWG g) {
            this.graph = g;
            currentPos = 0;
        }

        @Override
        public boolean hasNext() {
            return currentPos < (this.graph.nodeSize());
        }

        @Override
        public NodeData next() {
            return this.graph.getNode(currentPos++);
        }

        @Override
        public void remove() {
            this.graph.removeNode(currentPos--);
        }

        public void remove(int key) {

            this.graph.removeNode(key);
            currentPos--;
        }

        @Override
        public void forEachRemaining(Consumer action) {
            Iterator.super.forEachRemaining(action);
        }
    }

    public static class EdgeIterator implements Iterator<EdgeData> {

        MyDWG graph;
        int currentSrcPos;
        int currentDstPos;

        public EdgeIterator(MyDWG g) {
            this.graph = g;
            currentSrcPos = 0;
            currentDstPos = 0;
        }

        @Override
        public boolean hasNext() {
            return currentDstPos < (this.graph.edgeList.get(currentSrcPos).size());
        }

        @Override
        public EdgeData next() {
            EdgeData ans = null;
            if (currentSrcPos < this.graph.nodeSize()) {
                ans = this.graph.edgeList.get(currentSrcPos).get(currentDstPos++);
                if (!this.graph.edgeList.get(currentSrcPos).containsKey(currentDstPos + 1)) {
                    currentSrcPos++;
                    currentDstPos = 0;
                }
            }
            return ans;
        }

        @Override
        public void remove() {
            this.graph.removeEdge(currentSrcPos,currentDstPos);
        }

        public void remove(EdgeData e) {

            this.graph.removeEdge(e.getSrc(), e.getDest());
            currentDstPos--;
        }

        @Override
        public void forEachRemaining(Consumer action) {
            Iterator.super.forEachRemaining(action);
        }
    }


    public static class SpesificEdgeIterator implements Iterator<EdgeData> {

        MyDWG graph;
        Iterator<Integer> keySet;
        int currentPos = 0;
        int lastPos;
        int NodeKey;

        public SpesificEdgeIterator(MyDWG g, int nodeKey) {
            this.graph = g;
            this.keySet = this.graph.edgeList.get(NodeKey).keySet().iterator();
            this.NodeKey = nodeKey;
        }

        @Override
        public boolean hasNext() {
            return currentPos < (this.graph.edgeList.get(NodeKey).size());
        }

        @Override
        public EdgeData next() {
            this.lastPos = currentPos;
            this.currentPos = keySet.next();
            return this.graph.edgeList.get(NodeKey).get(currentPos);
        }

        @Override
        public void remove() {
            this.graph.removeEdge(NodeKey,currentPos);
            keySet = this.graph.edgeList.get(NodeKey).keySet().iterator();
            currentPos = keySet.next();
            while (currentPos != lastPos){
                currentPos = keySet.next();
            }
            currentPos = keySet.next();
        }

        public void remove(EdgeData e) {

            this.graph.removeEdge(e.getSrc(), e.getDest());
            keySet = this.graph.edgeList.get(NodeKey).keySet().iterator();
            currentPos = keySet.next();
            while (currentPos != lastPos){
                currentPos = keySet.next();
            }
            currentPos = keySet.next();
        }

        @Override
        public void forEachRemaining(Consumer action) {
            Iterator.super.forEachRemaining(action);
        }
    }
}