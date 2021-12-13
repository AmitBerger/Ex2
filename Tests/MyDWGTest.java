
import Implementation.Edge;
import Implementation.Location;
import Implementation.MyDWG;
import Implementation.Node;
import org.junit.jupiter.api.Test;
import api.*;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyDWGTest {

    Location p1= new Location(1,2,0);;
    Location p2= new Location(2,1,0);;
    NodeData n1= new Node(0,p1);;
    NodeData n2= new Node(1,p2);;
    EdgeData e1= new Edge(0,2,1);;


    Location p = new Location(35.19589389346247, 32.10152879327731, 0.0);;
    Location p0 = new Location(35.20319591121872, 32.10318254621849, 0.0);;
    Location p3 = new Location(35.20752617756255, 32.1025646605042, 0.0);;
    Location p4 = new Location(35.21007339305892, 32.10107446554622, 0.0);;
    Location p5 = new Location(35.21310882485876, 32.104636394957986, 0.0);;

    NodeData a = new Node(0, p);;
    NodeData b = new Node(1, p0);;
    NodeData c = new Node(2, p3);;
    NodeData d = new Node(3, p4);;
    NodeData e = new Node(4, p5);;
    NodeData[] nodes = {a, b, c, d, e};

    MyDWG g = new MyDWG();

    @org.junit.jupiter.api.BeforeEach
    void initial(){
        p1 = new Location(1,2,0);
        p2 = new Location(2,1,0);
        n1 = new Node(0,p1);
        n2 = new Node(1,p2);
        e1 = new Edge(0,2,1);

        Location p = new Location(35.19589389346247, 32.10152879327731, 0.0);
        Location p0 = new Location(35.20319591121872, 32.10318254621849, 0.0);
        Location p3 = new Location(35.20752617756255, 32.1025646605042, 0.0);
        Location p4 = new Location(35.21007339305892, 32.10107446554622, 0.0);
        Location p5 = new Location(35.21310882485876, 32.104636394957986, 0.0);

        NodeData a = new Node(0, p);
        NodeData b = new Node(1, p0);
        NodeData c = new Node(2, p3);
        NodeData d = new Node(3, p4);
        NodeData e = new Node(4, p5);
        NodeData[] nodes = {a, b, c, d, e};

        g= new MyDWG();
    }

    @Test
    void getNode() {

        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        assertEquals(g.getNode(0),n1);
    }


    @Test
    void addNode() {
        g.addNode(n1);
        g.addNode(n2);
        assertNotNull(g.getNode(n1.getKey()));
    }

    @Test
    void connect() {
        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        EdgeData e1 = new Edge(n1.getKey(),n2.getKey(),2);
        assertEquals(g.getEdgeList().get(e1.getSrc()).get(e1.getDest()).toString(),e1.toString());
    }

    @Test
    void removeNode() {

        g.addNode(n1);
        g.addNode(n2);
        g.connect(n1.getKey(),n2.getKey(),2);
        g.connect(n2.getKey(),n1.getKey(),1);
        g.removeNode(n1.getKey());
        g.removeNode(n2.getKey());
        assertNull(g.getNode(n1.getKey()));
        assertEquals(0, g.getEdgeList().size());

        initValues(g);
        Iterator<NodeData> nodeIter = g.nodeIter();
        while (nodeIter.hasNext()){
            nodeIter.next();
            nodeIter.remove();
        }
        assertEquals(0, g.nodeSize());

        g = new MyDWG();
        initValues(g);
        Iterator<EdgeData> edgeIter = g.edgeIter(a.getKey());
        while (edgeIter.hasNext()){
            edgeIter.next();
            edgeIter.remove();
        }
        assertEquals(3, g.edgeSize());

        g = new MyDWG();
        initValues(g);
        Iterator<EdgeData> edgeIter2 = g.edgeIter();
        while (edgeIter2.hasNext()){
            edgeIter2.next();
            edgeIter2.remove();
        }
        assertEquals(0, g.edgeSize());

    }

    public void initValues(MyDWG g){
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.connect(a.getKey(),b.getKey(),1);
        g.connect(a.getKey(),c.getKey(),2);
        g.connect(a.getKey(),d.getKey(),3);
        g.connect(a.getKey(),e.getKey(),4);
        g.connect(c.getKey(),b.getKey(),5);
        g.connect(c.getKey(),d.getKey(),6);
        g.connect(c.getKey(),e.getKey(),7);
    }


    @Test
    void nodeSize() {

        assertEquals(0,g.getNodeList().size());
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        assertEquals(3,g.getNodeList().size());
        g.addNode(d);
        g.addNode(e);
        assertEquals(5,g.getNodeList().size());
    }

    @Test
    void edgeSize() {

        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        assertEquals(0,g.edgeSize());
        g.connect(a.getKey(),b.getKey(),4);
        g.connect(a.getKey(),c.getKey(),7.5);
        g.connect(a.getKey(),d.getKey(),2);
        g.connect(a.getKey(),e.getKey(),1);
        g.connect(b.getKey(),a.getKey(),3);
        assertEquals(5,g.edgeSize());
        g.connect(b.getKey(),c.getKey(),2.8);
        g.connect(b.getKey(),d.getKey(),2.8);
        g.connect(b.getKey(),e.getKey(),2.8);
        g.connect(c.getKey(),a.getKey(),2.8);
        assertEquals(9,g.edgeSize());
        g.connect(c.getKey(),b.getKey(),2.8);
        assertEquals(10,g.edgeSize());
    }

    @Test
    void getMC() {

        assertEquals(0,g.getMC());
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        assertEquals(5,g.getMC());
        g.connect(a.getKey(),b.getKey(),4);
        assertEquals(6,g.getMC());
        g.connect(a.getKey(),c.getKey(),7.5);
        g.connect(a.getKey(),d.getKey(),2);
        g.connect(a.getKey(),e.getKey(),1);
        g.connect(b.getKey(),a.getKey(),3);
        g.connect(b.getKey(),c.getKey(),2.8);
        g.connect(b.getKey(),d.getKey(),2.8);
        g.connect(b.getKey(),e.getKey(),2.8);
        assertEquals(13,g.getMC());
        g.connect(c.getKey(),a.getKey(),2.8);
        assertEquals(14,g.getMC());
        g.connect(c.getKey(),b.getKey(),2.8);
        assertEquals(15,g.getMC());
    }
}
