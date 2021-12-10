import Implementation.Edge;
import Implementation.Location;
import Implementation.MyDWGAlgorithm;
import Implementation.Node;
import api.DirectedWeightedGraph;
import api.NodeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
        MyDWGAlgorithm ga = new MyDWGAlgorithm();
        DirectedWeightedGraph g;
        NodeData nd0;
        NodeData nd1;
        NodeData nd2;
        Node n0;
        Node n1;
        Node n2;
        Edge e0;
        Edge e1;
        Edge e2;

    @BeforeEach
    void initial(){
        ga.load("data/G1.json");
        g = ga.getGraph();
        nd0 = new Node(0);
        nd1 = new Node(1,new Location(0,1,2));
        nd2 = new Node(nd0);
        n0 = new Node(0);
        n1 = new Node(1,new Location(0,1,2));
        n2 = new Node(nd0);
        e0 = new Edge(0,0,0);
        e1 = new Edge(1,1,1);
        e2 = new Edge(2,2,2);
    }

    @Test
    void getKey() {
        assertEquals(0,n0.getKey());
        assertEquals(1,nd1.getKey());
        assertEquals(0,nd2.getKey());
    }

    @Test
    void getLocation() {
        assertEquals(0,n0.getLocation().x());
        assertEquals(0,n0.getLocation().y());
        assertEquals(0,n0.getLocation().z());
        assertEquals(0,n1.getLocation().x());
        assertEquals(1,n1.getLocation().y());
        assertEquals(2,n1.getLocation().z());
    }

    @Test
    void setLocation() {
        assertEquals(0, n1.getLocation().x());
        assertEquals(1, n1.getLocation().y());
        assertEquals(2, n1.getLocation().z());
        n1.setLocation(new Location(21,22,23));
        assertEquals(21, n1.getLocation().x());
        assertEquals(22, n1.getLocation().y());
        assertEquals(23, n1.getLocation().z());
    }

    @Test
    void getWeight() {
        assertEquals(0, nd0.getWeight());
        assertEquals(0, n0.getWeight());
        assertEquals(0, nd1.getWeight());
        assertEquals(0, n2.getWeight());
    }

    @Test
    void getTag() {
        assertEquals(0,n0.getTag());
        assertEquals(0,nd0.getTag());
        assertEquals(0,n1.getTag());
        assertEquals(0,nd2.getTag());
    }

    @Test
    void getInfo() {
        assertEquals("0.0,0.0",n0.getInfo());
        assertEquals("0.0,1.0",n1.getInfo());
        assertEquals("0.0,0.0",n2.getInfo());
    }

    @Test
    void setWeight() {
        assertEquals(0, nd0.getWeight());
        assertEquals(0, n0.getWeight());
        nd0.setWeight(30);
        n0.setWeight(40);
        assertEquals(30, nd0.getWeight());
        assertEquals(40, n0.getWeight());
    }

    @Test
    void setInfo() {
        assertNotSame("aaa", n1.getInfo());
        n1.setInfo("aaa");
        assertSame("aaa", n1.getInfo());
    }

    @Test
    void setTag() {
        assertEquals(0,n1.getTag());
        assertEquals(0,nd2.getTag());
        n1.setTag(12);
        nd2.setTag(14);
        assertEquals(12,n1.getTag());
        assertEquals(14,nd2.getTag());
    }
}