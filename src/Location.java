import api.GeoLocation;

public class Location implements GeoLocation {
    private double x;
    private double y;
    private double z;

    public Location(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double max_x = Math.max(this.x, g.x());
        double min_x = Math.min(this.x, g.x());
        double max_y = Math.max(this.y, g.y());
        double min_y = Math.min(this.y, g.y());
        return Math.sqrt(Math.pow((max_x - min_x),2) + Math.pow((max_y - min_y),2));
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
