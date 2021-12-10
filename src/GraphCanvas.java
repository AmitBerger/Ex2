import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GraphCanvas extends JComponent implements ActionListener {
    /**
     * The Graph
     */
    public MyDWGAlgorithm graphAlgo;
    public DirectedWeightedGraph graph;
    final int width = 900;
    final int height = 750;
    public final Dimension PreferredButtonSize = new Dimension(50, 25);
    private Dimension size = new Dimension(width, height);
    public double NodeMinX = Double.MAX_VALUE;
    public double NodeMinY = Double.MAX_VALUE;
    public double NodeMaxX = Double.MIN_VALUE;
    public double NodeMaxY = Double.MIN_VALUE;
    final int BLACK = 0;
    final int GRAY = 1;
    final int BLUE = 2;
    final int ORANGE = 3;
    MyGUI gui;

    String fileName = "data/G3.json";


    /**
     * Constructor
     */
    public GraphCanvas() {
        graphAlgo = new MyDWGAlgorithm();
        graphAlgo.load(fileName);
        graph = graphAlgo.getGraph();
        getMinMaxValues();

    }

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
            if (node.getTag() == BLACK) {
                g.setColor(Color.BLACK);
            } else if (node.getTag() == GRAY) {
                g.setColor(Color.lightGray);
            } else if (node.getTag() == BLUE){
                g.setColor(Color.BLUE);
            }else {
                g.setColor(Color.ORANGE);
            }
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

    /**
     * Paint the traversal path to red
     *
     * @param path the list of edges in the traversal path
     * @return whether there is a traversal to paint or not
     */
    public Boolean paintTraversal(List<EdgeData> path) {
        if (path.isEmpty()) {
            return false;
        }
        // set every thing to black, and set the start point to orange
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()) {
            edgeIter.next().setTag(GRAY);
        }
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()) {
            nodeIter.next().setTag(GRAY);
        }
        path.get(0).setTag(ORANGE);
        repaint();

        for (EdgeData edge : path) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
            // Color the current edge
            edge.setTag(BLUE);
            graph.getNode(edge.getDest()).setTag(ORANGE);
            repaint();
            try {
                Thread.sleep(800);
            } catch (InterruptedException ignore) {
            }
            graph.getNode(edge.getDest()).setTag(BLUE);
            repaint();
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();

        if (source == gui.load_tab) {
            JFileChooser j = new JFileChooser("data/");
            j.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
            j.addChoosableFileFilter(filter);
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                String file_path = j.getSelectedFile().getAbsolutePath();

                MyDWGAlgorithm GA = new MyDWGAlgorithm();
                GA.load(file_path);

            }
        }

    }

    public void refresh() {
        Iterator<EdgeData> edgeIter = graph.edgeIter();
        while (edgeIter.hasNext()){
            edgeIter.next().setTag(BLACK);
        }
        Iterator<NodeData> nodeIter = graph.nodeIter();
        while (nodeIter.hasNext()){
            nodeIter.next().setTag(BLUE);
        }
        repaint();
    }

    public Dimension getMinimumSize() {
        return new Dimension(width,height);
    }
    public Dimension getPreferredSize() {
        this.size = new Dimension(width, height);
        return size;
    }

}
