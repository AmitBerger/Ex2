import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.util.HashMap;

public class Node implements NodeData {
    private static int id = 0;
    private int key;
    private double weight;
    private int tag;
    private String info;
    private Location geo;
    public HashMap<Integer, EdgeData> outGoingEdges;
    public HashMap<Integer, EdgeData> inComingEdges;

    public Node(int key, Location geo) {
        id++;
        this.key = key;
        this.weight = 0;
        this.tag = 0;
        this.info = "";
        this.geo = geo;
        outGoingEdges = new HashMap<>();
        inComingEdges = new HashMap<>();


    }

    public EdgeData getNodeEdge(int key) {
        EdgeData e = inComingEdges.get(key);
        EdgeData e1 = outGoingEdges.get(key);
        EdgeData ans = null;
        if (e != null) {
            ans = e;
        }
        if (e1 != null) {
            ans = e1;
        }
        return ans;
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
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;

    }
}
