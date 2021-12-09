import api.EdgeData;
import api.GeoLocation;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MyGUI extends JFrame {

    private GraphCanvas canvas;

//    JFrame frame;

    /**
     * Label for the instructions
     */
    private JLabel Console;
    /**
     * Buttons for the user:
     */
    private JButton addNodeButton;
    private JButton removeNodeButton;
    private JButton addEdgeButton;
    private JButton removeEdgeButton;
    private JButton refreshButton;
    private JButton shortestPathButton;
    private JButton centerButton;
    private JButton isConnectedButton;
    private JButton tspButton;

    /**
     * Text fields to get input from user
     */
    private JTextField SP_SRC;
    private JTextField SP_DST;
    private JTextField TSP_CITIES;

    /**
     * The input mode - default: Add nodes button
     */
    private InputMode mode = InputMode.ADD_NODES;

    /**
     * Stores last mousedown event position
     */
    private NodeData nodeUnderMouse;

    List<NodeData> tsp_cities;

    String fileName;

    Container pane;

    /**
     * Constructors
     */
    public MyGUI() {
        canvas = new GraphCanvas();
        fileName = canvas.fileName;
        this.setResizable(true);
        SetFrame();
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public MyGUI(String file) {
        canvas = new GraphCanvas(file);
        fileName = canvas.fileName;
        this.setResizable(true);
        SetFrame();
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    /**
     * Creat the GUI window
     */
    private void SetFrame() {
        // Adding a nice window decorations.           works?
//        setDefaultLookAndFeelDecorated(true);

        // Create and set the window.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components.
        SetPanels();

        // Displays the window.
        this.pack();
        this.setVisible(true);
    }

    /**
     * Creat & displays the buttons & graph
     */
    private void SetPanels() {
        // Filling a container(pane)
        pane = this.getContentPane();
        pane.setLayout(new FlowLayout());
        addPanel1();
        addPanel2();
        addPanel3();
    }

    private void addPanel1() {
        // Adding the graph
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        GraphMouseListener listener = new GraphMouseListener();
        canvas.addMouseListener(listener);
        canvas.addMouseMotionListener(listener);
        panel1.add(canvas);
        Console = new JLabel(("Click to add new nodes"));
        panel1.add(Console, BorderLayout.NORTH);
        pane.add(panel1);
    }

    private void addPanel2() {
        // build graph buttons
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GRAY);
        // Sets the table for the buttons
        panel2.setLayout(new GridLayout(4, 1));

        addNodeButton = new JButton("Add Nodes");
        panel2.add(addNodeButton);
        addNodeButton.addActionListener(new AddNodeListener());

        removeNodeButton = new JButton("Remove Nodes");
        panel2.add(removeNodeButton);
        removeNodeButton.addActionListener(new RmvNodeListener());

        addEdgeButton = new JButton("Add Edges");
        panel2.add(addEdgeButton);
        addEdgeButton.addActionListener(new AddEdgeListener());

        removeEdgeButton = new JButton("Remove Edges");
        panel2.add(removeEdgeButton);
        removeEdgeButton.addActionListener(new RmvEdgeListener());
        pane.add(panel2);
    }

    private void addPanel3() {
        // traversal buttons
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(2,1));

        refreshButton = new JButton("Refresh");
        panel3.add(refreshButton);
        refreshButton.addActionListener(new RFListener());
        pane.add(panel3);

        shortestPathButton = new JButton("Topological Sort");
        panel3.add(shortestPathButton);
        shortestPathButton.addActionListener(new SPListener());
    }

    /**
     * Main - Scheduler
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyGUI();
            }
        });
    }

    /**
     * Constants for recording the input mode
     */
    enum InputMode {
        ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, REFRESH, SP, IS_CONNECTED, CENTER, SP_SRC, SP_DST, TSP_CITIES
    }

    /**
     * Listener for Add Node button
     */
    private class AddNodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_NODES;
            Console.setText("Click to add new nodes or change their location.");
        }
    }

    /**
     * Listener for Remove Node button
     */
    private class RmvNodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.RMV_NODES;
            Console.setText("Click on a node to remove.");
        }
    }

    /**
     * Listener for Add Edge button
     */
    private class AddEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_EDGES;
            Console.setText("Drag from one node to another to add an edge.");
        }
    }

    /**
     * Listener for Remove Edge button
     */
    private class RmvEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.RMV_EDGES;
            Console.setText("Drag from one node to another to remove an edge.");
        }
    }

    /**
     * Listener for Refresh button
     */
    private class SPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.SP;
            Console.setText("The shortest path is:");
        }
    }

    /**
     * Listener for IsConnected button
     */
    private class IsConnectedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.IS_CONNECTED;
            Console.setText("The graph is connected ==");
        }
    }

    /**
     * Listener for Center button
     */
    private class CenterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.CENTER;
            Console.setText("The center is:");
        }
    }

    /**
     * Listener for shortest path src text field
     */
    private class SP_SRCListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.SP_SRC;
            Console.setText("Enter the source node key");
        }
    }

    /**
     * Listener for shortest path dst text field
     */
    private class SP_DSTListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.SP_DST;
            Console.setText("Enter the destination node key");
        }
    }

    /**
     * Listener for Refresh Path button
     */
    private class RFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refresh();
            addNodeButton.setEnabled(true);
            removeNodeButton.setEnabled(true);
            addEdgeButton.setEnabled(true);
            removeEdgeButton.setEnabled(true);
            refreshButton.setEnabled(true);
//            shortestPathButton.setEnabled(true);
//            isConnectedButton.setEnabled(true);
//            centerButton.setEnabled(true);
            Console.setText("Refreshed");
        }
    }

    /**
     * Finds the nearby node(mouse location relation)
     */
    public NodeData findNearbyNode(GeoLocation g) {
        NodeData nearbyNode = null;
        Iterator<NodeData> nodeIter = canvas.graph.nodeIter();
        while (nodeIter.hasNext()) {
            NodeData node = nodeIter.next();
            GeoLocation h = node.getLocation();
            if (g.distance(h) <= 15) {
                nearbyNode = node;
            }
        }
        return nearbyNode;
    }

    /**
     * Mouse listener for GraphCanvas element
     */
    private class GraphMouseListener extends MouseAdapter
            implements MouseMotionListener {

        public void mouseClicked(MouseEvent e) {
            NodeData nearbyNode = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
            boolean worked = false;
            switch (mode) {
                case ADD_NODES:
                    if (nearbyNode == null) {
                        int num = canvas.graph.nodeSize();
                        canvas.graph.addNode(new Node(num,new Location(e.getX(),e.getY(),0.0)));
                        refresh();
                        worked = true;
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
                case RMV_NODES:
                    if (nearbyNode != null) {
                        canvas.graph.removeNode(nearbyNode.getKey());
                        refresh();
                        worked = true;
                    }
                    if (!worked) {
                        Toolkit.getDefaultToolkit().beep();
                    }
                    break;
            }
        }
    }

    /**
     * Records point under mousedown event
     */
    public void mousePressed(MouseEvent e) {
        nodeUnderMouse = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
    }

    /**
     * Responds to mouseup event
     */
    @SuppressWarnings("unchecked")
    public void mouseReleased(MouseEvent e) {
        NodeData nearbyNode = findNearbyNode(new Location(e.getX(), e.getY(), 0.0));
        boolean worked = false;
        switch (mode) {
            case ADD_EDGES:
                if (nodeUnderMouse != null && nearbyNode != null && nearbyNode != nodeUnderMouse) {
                    // the user don't have to enter the distance now, the program will calculate the pixel distance
//                        canvas.graph.addEdge((new EdgeData(-1.0)),nodeUnderMouse,nearbyNode);
                    canvas.graph.connect(nodeUnderMouse.getKey(),nearbyNode.getKey() ,
                            nearbyNode.getLocation().distance(nearbyNode.getLocation()));
                    refresh();
                    worked = true;
                }
                if (!worked) {
                    Toolkit.getDefaultToolkit().beep();
                }
                break;

            case RMV_EDGES:
                if (nodeUnderMouse != null && nearbyNode != null && nearbyNode != nodeUnderMouse) {
                    if (canvas.graph.getEdge(nodeUnderMouse.getKey(),nearbyNode.getKey()) != null){
                        canvas.graph.removeEdge(nodeUnderMouse.getKey(),nearbyNode.getKey());
                    }
                    refresh();
                    worked = true;
                }
                if (!worked) {
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
        }
    }

    public void mouseDragged(MouseEvent e) {
        // test if the mouse is dragging something
        if (mode == InputMode.ADD_EDGES && nodeUnderMouse != null) {
            nodeUnderMouse.setLocation(new Location(e.getX(), e.getY(), 0.0));
            refresh();
        }
    }

    public void mouseMoved(MouseEvent e) {
        nodeUnderMouse = null;
    }

    /*Repaint every thing to the default color*/
    public void refresh() {
//        new MyGUI(fileName);
//        this.getContentPane().removeAll();
        this.repaint();
    }
    /** Worker class for doing traversals */
    private class TraversalThread extends SwingWorker<Boolean, Object> {
        /** The path that needs to paint */
        private final LinkedList<EdgeData> path;

        /** The Constructor of TraversalThread */
        public TraversalThread(LinkedList<EdgeData> path){
            this.path = path;
        }

        @Override
        public Boolean doInBackground() {
            addNodeButton.setEnabled(false);
            removeNodeButton.setEnabled(false);
            addEdgeButton.setEnabled(false);
            removeEdgeButton.setEnabled(false);
            refreshButton.setEnabled(false);
            return canvas.paintTraversal(path);
        }

        @Override
        protected void done() {
            try {
                if (path.isEmpty() && path != null) {  // test the result of doInBackground()
                    Console.setText("There is no path. Please refresh.");
                }
                refreshButton.setEnabled(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
