import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import java.awt.*;
import java.awt.geom.Line2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;

public class GraphCanvas extends JPanel {
    /**
     * The Graph
     */
    public MyDWGAlgorithm graphAlgo;
    public DirectedWeightedGraph graph;
    final int width = 900;
    final int height = 750;
    private Dimension size = new Dimension(width, height);
    public final Dimension PreferredButtonSize = new Dimension(50, 25);
    public double NodeMinX = Double.MAX_VALUE;
    public double NodeMinY = Double.MAX_VALUE;
    public double NodeMaxX = Double.MIN_VALUE;
    public double NodeMaxY = Double.MIN_VALUE;

    /** Constructor */
    public GraphCanvas() {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load("data/G1.json");
        graph = graphAlgo.getGraph();
        getMinMaxValues();
    }

    public GraphCanvas(String file) {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load(file);
        graph = graphAlgo.getGraph();
    }

    public void getMinMaxValues() {
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData node = nodeIter.next();
            GeoLocation l = node.getLocation();
            if (NodeMinX > l.x()) {
                NodeMinX = l.x();
            }
            if (NodeMinY > l.y()) {
                NodeMinY = l.y();
            }
            if (NodeMaxY < l.y()) {
                NodeMaxY = l.y();
            }
            if (NodeMaxX < l.x()) {
                NodeMaxX = l.x();
            }
        }
    }

    /**
     * Paints a blue circle thirty pixels in diameter at each node
     * and a blue line for each edge.
     *
     * @param g The graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        // paint edges
        Graphics2D g2d = (Graphics2D) g;
        double deltaX = NodeMaxX - NodeMinX;
        double deltaY = NodeMaxY - NodeMinY;
        double xScaled = (width / deltaX)*0.75;
        double yScaled = (width / deltaY) *0.75;

        g2d.setPaint(Color.BLACK);
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData edge = edgeIter.next();
            GeoLocation geo1 = this.graph.getNode(edge.getSrc()).getLocation();
            GeoLocation geo2 = this.graph.getNode(edge.getDest()).getLocation();
            double x1 = (int)((geo1.x() - NodeMinX) * xScaled*0.97 + 30);
            double y1 = (int)((geo1.y() - NodeMinY) * yScaled*0.97 + 30);
            double x2 = (int)(((geo2).x() - NodeMinX) * xScaled*0.97 + 30);
            double y2 = (int)((geo2.y() - NodeMinY) * yScaled*0.97 + 30);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(new Line2D.Double(x1+15, y1+15, x2+15, y2+15));
            drawArrowHead(g2d, Math.atan2(y2 - y1+15, x2 - x1+15), x2+15, y2+15);
        }

        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData node = nodeIter.next();
            // Linearly map the point
            double x = (node.getLocation().x() - NodeMinX)*xScaled*0.97+30;
            double y = (node.getLocation().y() - NodeMinY)*yScaled*0.97+30;
            g.setColor(Color.BLUE);
            g.drawOval((int) x, (int) y, 30, 30);
            Font f = new Font("ariel", Font.BOLD, 16);
            g.setFont(f);
            g.setColor(Color.BLACK);
            g.drawString(node.getKey() + "", (int) x, (int) y-5);
        }
    }

    /**
     * Draw an arrow line between two points, revised from the code of @phibao37
     * http://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
     *
     * @param x1 x-position of first point
     * @param y1 y-position of first point
     * @param x2 x-position of second point
     * @param y2 y-position of second point
     * @param d  the width of the arrow
     * @param h  the height of the arrow
     * @return the distance between two points
     */
    private double paintArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h, Double dist) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / D, cos = dx / D;

        //change the end point of the line, r = 40
        x2 = (int) (x2 - 10 * cos);
        y2 = (int) (y2 - 10 * sin);
        D = D - 10; // change the length of the line
        double xm = D - d, xn = xm, ym = h, yn = -h, x;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
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
        g.drawString(Double.toString(dist), (int) (xm - 30 * cos), (int) (ym - 30 * sin));
        return dist;
    }

    // taken from https://coderanch.com/t/339505/java/drawing-arrows
    private void drawArrowHead(Graphics2D g2, double theta, double x0, double y0) {
        double barb = 7;
        double phi = Math.PI / 6;
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.setPaint(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }


    /*Repaint every thing to the default color*/
    public void refresh() {
        repaint();
    }

    public Dimension getPreferredSize() {
        this.size = new Dimension(width, height);
        return size;
    }
    private void UpdateSize() {
        this.size = new Dimension(this.getWidth(),this.getHeight());
        this.setSize(size);
    }

    public Dimension getSize() {
        return size;
    }
}
