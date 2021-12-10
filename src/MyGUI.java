import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class MyGUI extends JFrame {

    GraphCanvas canvas;

//    JFrame frame;
    JMenuBar j_Menu_Bar;
    JMenu file_tab;
    JMenuItem save_tab, load_tab;


    /**
     * Label for the instructions
     */
    JLabel Console;
    /**
     * Buttons for the user:
     */

    JButton refreshButton;
    JButton shortestPathButton;
    /**
     * The input mode - default: Add nodes button
     */
    InputMode mode = InputMode.ADD_NODES;

    /**
     * Stores last mousedown event position
     */
    NodeData nodeUnderMouse;

    List<NodeData> tsp_cities;

    String fileName;

    Container pane;

    ButtonsPanel buttons;
    MouseListenerPanel mouse;
    /**
     * Constructors
     */
    public MyGUI() {
        this.setResizable(true);
        SetFrame(false);
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public void Init(String file) {
        canvas = new GraphCanvas(file);
        buttons = new ButtonsPanel(this);
        mouse = new MouseListenerPanel(this);
        fileName = canvas.fileName;
        this.setResizable(true);
        SetFrame(true);
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public void Load(){
        j_Menu_Bar = new JMenuBar();
        file_tab = new JMenu("File");
        load_tab = new JMenuItem("Load");
        file_tab.add(load_tab);
        load_tab.addActionListener(new LoadListener());
        j_Menu_Bar.add(file_tab);
        setJMenuBar(j_Menu_Bar);
    }

    public void Menu(){
        j_Menu_Bar = new JMenuBar();
        file_tab = new JMenu("File");
        save_tab = new JMenuItem("Save");
        load_tab = new JMenuItem("Load");
        file_tab.add(save_tab);
        file_tab.add(load_tab);
        save_tab.addActionListener(new SaveListener());
        load_tab.addActionListener(new LoadListener());
        j_Menu_Bar.add(file_tab);
        setJMenuBar(j_Menu_Bar);
    }
    /**
     * Creat the GUI window
     */
    private void SetFrame(boolean flag) {
        // Adding a nice window decorations.           works?
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create and set the window.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add components.
        if (flag) {
            SetPanels();
        }
        Menu();

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
        pane.add(mouse);
    }

    private void addPanel2() {
        // build graph buttons
        pane.add(buttons);
    }

    private void addPanel3() {
        // traversal buttons
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(2,1));

        refreshButton = new JButton("Refresh");
        panel3.add(refreshButton);
        refreshButton.addActionListener(new RFListener());

        shortestPathButton = new JButton("Shortest Path");
        panel3.add(shortestPathButton);
        shortestPathButton.addActionListener(new SPListener());
        pane.add(panel3);
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
        ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, REFRESH, SP, IS_CONNECTED, CENTER, SP_SRC, SP_DST, TSP_CITIES,MENU
    }
    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.MENU;
            canvas.graphAlgo.save("SavedGraph.json");
            Console.setText("saved as SavedGraph.json!!!");
        }
    }
    private class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.MENU;
            JFileChooser j = new JFileChooser("data/");
            j.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
            j.addChoosableFileFilter(filter);
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                String file_path = j.getSelectedFile().getAbsolutePath();
                  Init(file_path);

            }

        }
    }

    /**
     * Listener for SP button
     */
    private class SPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.SP;
            Console.setText("The shortest path is:");
        }
    }

    /**
     * Listener for Refresh Path button
     */
    private class RFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refresh();
            buttons.addNodeButton.setEnabled(true);
            buttons.removeNodeButton.setEnabled(true);
            buttons.addEdgeButton.setEnabled(true);
            buttons.removeEdgeButton.setEnabled(true);
            refreshButton.setEnabled(true);
            shortestPathButton.setEnabled(true);
            buttons.isConnectedButton.setEnabled(true);
            buttons.centerButton.setEnabled(true);
            Console.setText("Refreshed");
        }
    }

    /*Repaint every thing to the default color*/
    public void refresh() {
//        new MyGUI(fileName);
//        this.getContentPane().removeAll();
        canvas.repaint();
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
            buttons.addNodeButton.setEnabled(false);
            buttons.removeNodeButton.setEnabled(false);
            buttons.addEdgeButton.setEnabled(false);
            buttons.removeEdgeButton.setEnabled(false);
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
