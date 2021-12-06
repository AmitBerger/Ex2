import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class GraphTest {
    Location p1 = new Location(35.19589389346247, 32.10152879327731, 0.0);
    Location p2 = new Location(35.20319591121872, 32.10318254621849, 0.0);
    Location p3 = new Location(35.20752617756255, 32.1025646605042, 0.0);
    Location p4 = new Location(35.21007339305892, 32.10107446554622, 0.0);
    Location p5 = new Location(35.21310882485876, 32.104636394957986, 0.0);

    NodeData a = new Node(0, p1);
    NodeData b = new Node(1, p2);
    NodeData c = new Node(2, p3);
    NodeData d = new Node(3, p4);
    NodeData e = new Node(4, p5);

    EdgeData e1 = new Edge(0, 3, 1.0);
    EdgeData e2 = new Edge(0, 2, 2.0);
    EdgeData e3 = new Edge(1, 2, 3.5);
    EdgeData e4 = new Edge(4, 1, 5.3);
    EdgeData e5 = new Edge(3, 2, 2.4);

    MyDWG dwg = new MyDWG();

    public static void main(String[] args) {
        Location p1 = new Location(35.19589389346247, 32.10152879327731, 0.0);
        Location p2 = new Location(35.20319591121872, 32.10318254621849, 0.0);
        Location p3 = new Location(35.20752617756255, 32.1025646605042, 0.0);
        Location p4 = new Location(35.21007339305892, 32.10107446554622, 0.0);
        Location p5 = new Location(35.21310882485876, 32.104636394957986, 0.0);

        NodeData a = new Node(0, p1);
        NodeData b = new Node(1, p2);
        NodeData c = new Node(2, p3);
        NodeData d = new Node(3, p4);
        NodeData e = new Node(4, p5);

        EdgeData e1 = new Edge(0, 3, 1.0);
        EdgeData e2 = new Edge(0, 2, 2.0);
        EdgeData e3 = new Edge(1, 2, 3.5);
        EdgeData e4 = new Edge(4, 1, 5.3);
        EdgeData e5 = new Edge(3, 2, 2.4);

        MyDWG dwg = new MyDWG();
        dwg.connect(e1.getSrc(), e1.getDest(),e1.getWeight());
        dwg.connect(e2.getSrc(), e2.getDest(),e2.getWeight());
        dwg.connect(e3.getSrc(), e3.getDest(),e3.getWeight());
        dwg.connect(e4.getSrc(), e4.getDest(),e4.getWeight());
        dwg.connect(e5.getSrc(), e5.getDest(),e5.getWeight());



        dwg.addNode(a);
        dwg.addNode(b);
        dwg.addNode(c);
        dwg.addNode(d);
        dwg.addNode(e);
        System.out.println(dwg);
        MyDWGAlgorithm algorithm = new MyDWGAlgorithm();
        System.out.println(algorithm.shortestPathDist(a.getKey(),b.getKey()));
        System.out.println();

    }


    @Test
    void getNode() {
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void getEdge() {
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.5);
        EdgeData e1 = new Edge(0, 2, 1.5);
        assertEquals(dwg.edgeList.get(a.getKey()).get(c.getKey()).getSrc(), e1.getSrc());
        assertEquals(dwg.edgeList.get(a.getKey()).get(c.getKey()).getDest(), e1.getDest());
        assertEquals(dwg.edgeList.get(a.getKey()).get(c.getKey()).getWeight(), e1.getWeight());
    }

    @Test
    void addNode() {
        dwg.addNode(a);
        assertEquals(dwg.getNode(0), a);
    }

    @Test
    void connect() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.0);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void nodeIter() {
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
        assertEquals(0, dwg.nodeSize());
        dwg.addNode(a);
        assertEquals(dwg.getNode(a.getKey()), a);
        dwg.removeNode(a.getKey());
        assertEquals(0, dwg.nodeSize());
    }

    @Test
    void removeEdge() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.0);
        assertEquals(1, dwg.edgeSize());
        dwg.removeEdge(a.getKey(), c.getKey());
        assertEquals(0, dwg.edgeSize());

    }

    @Test
    void nodeSize() {
        assertEquals(dwg.nodeSize(), 0);
        dwg.addNode(a);
        dwg.addNode(c);
        assertEquals(dwg.nodeSize(), 2);
        dwg.removeNode(a.getKey());
        assertEquals(dwg.nodeSize(), 1);
    }

    @Test
    void edgeSize() {
        assertEquals(0, dwg.edgeSize());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 1.278647826423);
        assertEquals(1, dwg.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0, dwg.getMC());
        dwg.addNode(a);
        assertEquals(1, dwg.getMC());
        dwg.removeNode(a.getKey());
        assertEquals(2, dwg.getMC());
        dwg.addNode(a);
        dwg.addNode(c);
        dwg.connect(a.getKey(), c.getKey(), 2.0);
        assertEquals(5, dwg.getMC());
        dwg.removeEdge(0, 2);
        assertEquals(6, dwg.getMC());
    }
}