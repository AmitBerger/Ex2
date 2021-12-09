import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GraphCanvas extends JPanel {
    /** The Graph */
    public MyDWGAlgorithm graphAlgo;
    public DirectedWeightedGraph graph;
    final int width = 1000;
    final int height = 800;
    private Dimension size = new Dimension(width,height);
    public final Dimension PreferredButtonSize = new Dimension(50,25);
    public double minX = Double.MAX_VALUE;
    public double minY = Double.MAX_VALUE;
    public double maxX = Double.MIN_VALUE;
    public double maxY = Double.MIN_VALUE;

    /** Constructor */
    public GraphCanvas() {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load("data/MyJsonForGUI.json");
        graph = graphAlgo.getGraph();
    }
    public GraphCanvas(String file) {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load(file);
        graph = graphAlgo.getGraph();
    }

    /**
     *  Paints a blue circle thirty pixels in diameter at each node
     *  and a blue line for each edge.
     *  @param g The graphics object to draw with
     */
    public void paintComponent(Graphics g){
        // paint edges
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()){
            EdgeData edge = edgeIter.next();
            g.setColor(Color.BLACK);
            GeoLocation h = graph.getNode(edge.getSrc()).getLocation();
            GeoLocation t = graph.getNode(edge.getDest()).getLocation();
            // paint the arrow, paint and calculate the distance between two points
            double dist = paintArrowLine(g,(int)h.x(),(int)h.y(),(int)t.x(),(int)t.y(),10,5,edge.getWeight());
        }
        // paint nodes
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()){
            NodeData node = nodeIter.next();
            GeoLocation l = node.getLocation();
            if (minX > l.x()){
                minX = l.x();
            }
            if (minY > l.y()){
                minY = l.y();
            }
            if (maxY < l.y()){
                maxY = l.y();
            }
            if (maxX < l.x()){
                maxX =  l.x();
            }
            g.setColor(Color.BLUE);
            GeoLocation pos = node.getLocation();
            g.fillOval((int) pos.x()-10, (int) pos.y()-10, 20, 20);
            //paint text
            g.setColor(Color.white);
            g.drawString(node.getKey()+"",(int)pos.x()-5,(int)pos.y()+5);
        }
    }

    /**
     * Draw an arrow line between two points, revised from the code of @phibao37
     * http://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
     * @param x1 x-position of first point
     * @param y1 y-position of first point
     * @param x2 x-position of second point
     * @param y2 y-position of second point
     * @param d  the width of the arrow
     * @param h  the height of the arrow
     * @return the distance between two points
     */
    private double paintArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h,Double dist){
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double sin = dy/D, cos = dx/D;

        //change the end point of the line, r = 40
        x2 = (int)(x2 - 10 * cos);
        y2 = (int)(y2 - 10 * sin);
        D = D - 10; // change the length of the line
        double xm = D - d, xn = xm, ym = h, yn = -h, x;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
        // paint the distance
        if (dist == -1.0) {
            // the actual distance(D+40) between two points next to the tail (D=the length of the arrow)
            dist = Math.round((D + 10) * 100) / 100.00;
        }
        g.drawString(Double.toString(dist),(int) (xm - 30 * cos), (int) (ym - 30 * sin));
        return dist;
    }

    /*Repaint every thing to the default color*/
    public void refresh(){
        repaint();
    }

    public Dimension getPreferredSize() {
        this.size = new Dimension(width,height);
        return size;
    }

    public Dimension getSize() {
        return size;
    }

}
