package Implementation;

import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData, Comparable<Node>{

    private int key;
    private double weight;
    private int tag;
    private String info;
    private Location geo;

    public Node(int key, Location geo) {

        this.key = key;
        this.weight = 0;
        this.tag = 0;
        this.info = geo.x() + "," + geo.y();
        this.geo = geo;
    }

    public Node(NodeData n) {

        this.key = n.getKey();
        this.weight = n.getWeight();
        this.tag = n.getTag();
        this.info = n.getLocation().x() + "," + n.getLocation().y();
        this.geo = (Location) n.getLocation();
    }

    public Node(int id) {
        this.key = id;
        this.weight = 0;
        this.tag = 0;
        this.info = "0.0,0.0";
        this.geo = new Location(0, 0, 0);
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
    public int getTag() {return this.tag;}

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

    @Override
    public String toString() {
        return "Node{"+ key+"}";
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.getWeight(), o.getWeight());
    }
}