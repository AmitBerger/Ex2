package Gui;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import Implementation.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.*;

public class GraphCanvas extends JComponent {
    /**
     * The Graph
     */
    public MyDWGAlgorithm graphAlgo;
    public DirectedWeightedGraph graph;
    final int width = 900;
    final int height = 750;
    private Dimension size = new Dimension(width, height);
    public double NodeMinX = Double.MAX_VALUE;
    public double NodeMinY = Double.MAX_VALUE;
    public double NodeMaxX = Double.MIN_VALUE;
    public double NodeMaxY = Double.MIN_VALUE;
    final int BLACK = 0;
    final int GRAY = 1;
    final int BLUE = 2;
    String fileName;

    /**
     * Constructor
     */
    public GraphCanvas(String file) {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load(file);
        fileName = file;
        graph = graphAlgo.getGraph();
        getMinMaxValues();
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
        double dx = NodeMaxX - NodeMinX;
        double dy = NodeMaxY - NodeMinY;
        double xScaled = (width / dx) * 0.75;
        double yScaled = (width / dy) * 0.75;

        g2d.setPaint(Color.BLACK);
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()) {
            EdgeData edge = edgeIter.next();
            if (edge.getTag() == GRAY) {
                g.setColor(Color.lightGray);
            }
            GeoLocation geo1 = this.graph.getNode(edge.getSrc()).getLocation();
            GeoLocation geo2 = this.graph.getNode(edge.getDest()).getLocation();
            double x1 = (int) ((geo1.x() - NodeMinX) * xScaled * 0.97 + 30);
            double y1 = (int) ((geo1.y() - NodeMinY) * yScaled * 0.97 + 30);
            double x2 = (int) (((geo2).x() - NodeMinX) * xScaled * 0.97 + 30);
            double y2 = (int) ((geo2.y() - NodeMinY) * yScaled * 0.97 + 30);
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(new Line2D.Double(x1 + 15, y1 + 15, x2 + 15, y2 + 15));
            drawArrowHead(g2d, Math.atan2(y2 - y1 + 15, x2 - x1 + 15), x2 + 15, y2 + 15);
        }

        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData node = nodeIter.next();
            // Linearly map the point
            double x = (node.getLocation().x() - NodeMinX) * xScaled * 0.97 + 30;
            double y = (node.getLocation().y() - NodeMinY) * yScaled * 0.97 + 30;
            g.setColor(Color.BLUE);
            g.drawOval((int) x, (int) y, 30, 30);
            Font f = new Font("ariel", Font.BOLD, 16);
            g.setFont(f);
            g.setColor(Color.BLACK);
            g.drawString(node.getKey() + "", (int) x, (int) y - 5);
        }
    }

    // taken from https://coderanch.com/t/339505/java/drawing-arrows
    private void drawArrowHead(Graphics2D g2, double theta, double x0, double y0) {
        double barb = 7;
        double phi = Math.PI / 6;
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.setStroke(new BasicStroke(2));
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }

    public Dimension getMinimumSize() {
        return new Dimension(width, height);
    }

    public Dimension getPreferredSize() {
        this.size = new Dimension(width, height);
        return size;
    }

}
