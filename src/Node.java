import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.HashMap;

public class Node implements NodeData {

    private int key;
    private double weight;
    private int tag;
    private String info;
    private Location geo;
    HashMap<Integer, EdgeData> Edges;
    HashMap<Integer, EdgeData> towardsMeEdges;
    HashMap<Integer, EdgeData> fromMeEdges;

    public Node(int key, Location geo) {

        this.key = key;
        this.weight = 0;
        this.tag = 0;
        this.info = geo.x()+ "," + geo.y();
        this.geo = geo;
        Edges = new HashMap<>();
        fromMeEdges = new HashMap<>();
        towardsMeEdges = new HashMap<>();
    }
    public Node(NodeData n) {

        this.key = n.getKey();
        this.weight = n.getWeight();
        this.tag = n.getTag();
        this.info = n.getLocation().x()+ "," + n.getLocation().y();
        this.geo = (Location) n.getLocation();
        Edges = new HashMap<>();
        fromMeEdges = new HashMap<>();
        towardsMeEdges = new HashMap<>();
    }

    public EdgeData getNodeEdge(int key) {
        EdgeData desiredNode = Edges.get(key);
        return desiredNode;
    }

    public Node(int id, String pos) {
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geo = (Location) p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public void addEdge(Edge e, Boolean fromMe){
        if (fromMe){
            this.fromMeEdges.put(this.fromMeEdges.size(),e);
        }
        else{
            this.towardsMeEdges.put(this.towardsMeEdges.size(),e);
        }
        this.Edges.put(this.Edges.size(),e);
    }

    @Override
    public String toString() {
        return "Node{" +
                "key=" + key +
                '}';
    }
}