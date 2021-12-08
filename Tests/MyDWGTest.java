
import org.junit.jupiter.api.Test;
import api.*;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGTest {

    @Test
    void getNode() {
        Location p1 = new Location(1,2,0);
        Location p2 = new Location(2,1,0);
        MyDWG g= new MyDWG();
        NodeData n1 = new Node(0,p1);
        NodeData n2 = new Node(2,p1);
        Edge e1 = new Edge(0,2,1);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        assertEquals(g.getNode(0),n1);
    }


    @Test
    void addNode() {
        Location p1 = new Location(1,2,0);
        Location p2 = new Location(2,1,0);
        MyDWG g= new MyDWG();
        NodeData n1 = new Node(0,p1);
        NodeData n2 = new Node(1,p2);
        EdgeData e1 = new Edge(0,2,1);
        g.addNode(n1);
        g.addNode(n2);
        assertTrue(g.getNode(n1.getKey())!=null);
    }

    @Test
    void connect() {
        Location p1 = new Location(1,2,0);
        Location p2 = new Location(2,1,0);
        MyDWG g= new MyDWG();
        NodeData n1 = new Node(0,p1);
        NodeData n2 = new Node(1,p2);
        EdgeData e1 = new Edge(0,1,2);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        assertEquals(g.edgeList.get(e1.getSrc()).get(e1.getDest()).toString(),e1.toString());

    }




    @Test
    void removeNode() {
        Location p1 = new Location(1,2,0);
        Location p2 = new Location(2,1,0);
        MyDWG g= new MyDWG();
        NodeData n1 = new Node(0,p1);
        NodeData n2 = new Node(1,p2);
        EdgeData e1 = new Edge(0,2,1);
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        g.connect(n2.getKey(),n1.getKey(),1);
        g.removeNode(n1.getKey());
        g.removeNode(n2.getKey());
        assertNull(g.getNode(n1.getKey()));
        assertEquals(0, g.edgeList.size());



    }


    @Test
    void nodeSize() {
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
        NodeData[] nodes = {a, b, c, d, e};

        EdgeData e1 = new Edge(0, 4, 1.3);
        EdgeData e2 = new Edge(1, 0, 1.2);
        EdgeData e3 = new Edge(1, 4, 1.8);
        EdgeData e4 = new Edge(2, 1, 1.7);
        EdgeData e5 = new Edge(3, 2, 1.5);
        EdgeData e6 = new Edge(3, 0, 1);
        EdgeData e7 = new Edge(4, 2, 4);
        EdgeData e8 = new Edge(4, 3, 1.4);
        MyDWG g= new MyDWG();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        assertEquals(g.nodeList.size(),5);
    }

    @Test
    void edgeSize() {
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
        NodeData[] nodes = {a, b, c, d, e};

        EdgeData e1 = new Edge(0, 4, 1.3);
        EdgeData e2 = new Edge(1, 0, 1.2);
        EdgeData e3 = new Edge(1, 4, 1.8);
        EdgeData e4 = new Edge(2, 1, 1.7);
        EdgeData e5 = new Edge(3, 2, 1.5);
        EdgeData e6 = new Edge(3, 0, 1);
        EdgeData e7 = new Edge(4, 2, 4);
        EdgeData e8 = new Edge(4, 3, 1.4);
        MyDWG g= new MyDWG();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.connect(a.getKey(),b.getKey(),4);
        g.connect(a.getKey(),c.getKey(),7.5);
        g.connect(a.getKey(),d.getKey(),2);
        g.connect(a.getKey(),e.getKey(),1);
        g.connect(b.getKey(),a.getKey(),3);
        g.connect(b.getKey(),c.getKey(),2.8);
        g.connect(b.getKey(),d.getKey(),2.8);
        g.connect(b.getKey(),e.getKey(),2.8);
        g.connect(c.getKey(),a.getKey(),2.8);
        g.connect(c.getKey(),b.getKey(),2.8);
        assertEquals(10,g.edgeSize());
    }

    @Test
    void getMC() {
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
        NodeData[] nodes = {a, b, c, d, e};

        EdgeData e1 = new Edge(0, 4, 1.3);
        EdgeData e2 = new Edge(1, 0, 1.2);
        EdgeData e3 = new Edge(1, 4, 1.8);
        EdgeData e4 = new Edge(2, 1, 1.7);
        EdgeData e5 = new Edge(3, 2, 1.5);
        EdgeData e6 = new Edge(3, 0, 1);
        EdgeData e7 = new Edge(4, 2, 4);
        EdgeData e8 = new Edge(4, 3, 1.4);
        MyDWG g= new MyDWG();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.connect(a.getKey(),b.getKey(),4);
        g.connect(a.getKey(),c.getKey(),7.5);
        g.connect(a.getKey(),d.getKey(),2);
        g.connect(a.getKey(),e.getKey(),1);
        g.connect(b.getKey(),a.getKey(),3);
        g.connect(b.getKey(),c.getKey(),2.8);
        g.connect(b.getKey(),d.getKey(),2.8);
        g.connect(b.getKey(),e.getKey(),2.8);
        g.connect(c.getKey(),a.getKey(),2.8);
        g.connect(c.getKey(),b.getKey(),2.8);
        assertEquals(15,g.getMC());

    }


}