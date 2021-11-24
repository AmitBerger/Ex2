import api.GeoLocation;
import api.NodeData;

public class Node implements NodeData {
    private int key;
    private double weight;
    private int tag;
    private String info;
    private GeoLocation geo;

    public Node(int key, double weight, int tag, String info, GeoLocation geo){
        this.key = key;
        this.weight = weight;
        this.tag = tag;
        this.info = info;
        this.geo = geo;
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
        this.geo = p;

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
    this.info =s;
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
