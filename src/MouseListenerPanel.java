import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class MouseListenerPanel extends JPanel {

    MyGUI GUI;

    public MouseListenerPanel(MyGUI g){
        GUI = g;
        this.setLayout(new BorderLayout());
        MouseListener listener = new MouseListener();
        GUI.canvas.addMouseListener(listener);
        GUI.canvas.addMouseMotionListener(listener);
        this.add(GUI.canvas);
        GUI.Console = new JLabel(("Click to add new nodes"));
        this.add(GUI.Console, BorderLayout.NORTH);
    }

    public class MouseListener extends MouseAdapter implements MouseMotionListener {

        /**
         * Finds the nearby node(mouse location relation)
         */
        public NodeData findNearbyNode(GeoLocation g) {
            NodeData nearbyNode = null;
            Iterator<NodeData> nodeIter = GUI.canvas.graph.nodeIter();
            while (nodeIter.hasNext()) {
                NodeData node = nodeIter.next();
                GeoLocation h = node.getLocation();
                if (g.distance(h) <= 15) {
                    nearbyNode = node;
                }
            }
            return nearbyNode;
        }

        public void mouseClicked(MouseEvent e) {
            NodeData nearbyNode = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
            boolean worked = false;
            switch (GUI.mode) {
                case ADD_NODES:
                    if (nearbyNode == null) {
                        int num = GUI.canvas.graph.nodeSize();
                        GUI.canvas.graph.addNode(new Node(num, new Location(e.getX(), e.getY(), 0.0)));
                        GUI.refresh();
                        worked = true;
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                case RMV_NODES:
                    if (nearbyNode != null) {
                        GUI.canvas.graph.removeNode(nearbyNode.getKey());
                        GUI.refresh();
                        worked = true;
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
            }
        }

        /**
         * Records point under mousedown event
         */
        public void mousePressed(MouseEvent e) {
            GUI.nodeUnderMouse = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
        }

        /**
         * Responds to mouseup event
         */
        @SuppressWarnings("unchecked")
        public void mouseReleased(MouseEvent e) {
            NodeData nearbyNode = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
            boolean worked = false;
            switch (GUI.mode) {
                case ADD_EDGES:
                    if (GUI.nodeUnderMouse != null && nearbyNode != null && nearbyNode != GUI.nodeUnderMouse) {
                        // the user don't have to enter the distance now, the program will calculate the pixel distance
//                        canvas.graph.addEdge((new EdgeData(-1.0)),nodeUnderMouse,nearbyNode);
                        GUI.canvas.graph.connect(GUI.nodeUnderMouse.getKey(), nearbyNode.getKey(),
                                nearbyNode.getLocation().distance(nearbyNode.getLocation()));
                        GUI.refresh();
                        worked = true;
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;

                case RMV_EDGES:
                    if (GUI.nodeUnderMouse != null) {
                        if (GUI.canvas.graph.getEdge(GUI.nodeUnderMouse.getKey(), nearbyNode.getKey()) != null) {
                            GUI.canvas.graph.removeEdge(GUI.nodeUnderMouse.getKey(), nearbyNode.getKey());
                            GUI.refresh();
                            worked = true;
                        }
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
            }
        }

        @SuppressWarnings("unchecked")
        public void mouseDragged(MouseEvent e) {
            // test if the mouse is dragging something
            if (GUI.mode == MyGUI.InputMode.ADD_EDGES && GUI.nodeUnderMouse != null
                    && e.getX() >= 15 && e.getY() >= 15
                    && e.getX() <= (GUI.canvas.width - 15) && e.getY() <= (GUI.canvas.height - 15)) {
                GUI.nodeUnderMouse.setLocation(new Location(e.getX(), e.getY(), 0.0));
                GUI.refresh();
            }
        }

        public void mouseMoved(MouseEvent e) {
            GUI.nodeUnderMouse = null;
        }

    }
}