import api.NodeData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
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
    TSPPainterPanel traveler;
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
        traveler = new TSPPainterPanel(this);
        mouse = new MouseListenerPanel(this);
        fileName = canvas.fileName;
        this.setResizable(true);
        SetFrame(true);
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public void Load() {
        j_Menu_Bar = new JMenuBar();
        file_tab = new JMenu("File");
        load_tab = new JMenuItem("Load");
        file_tab.add(load_tab);
        load_tab.addActionListener(new LoadListener());
        j_Menu_Bar.add(file_tab);
        setJMenuBar(j_Menu_Bar);
    }

    public void Menu() {
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
            // Filling a container(pane).
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

        pane = this.getContentPane();
        pane.setLayout(new FlowLayout());
        // Adding the graph panel
        pane.add(mouse);
        // Adding functions buttons panel
        pane.add(buttons);
        // Adding the drawing panel for tsp answer.
        pane.add(traveler);
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
        ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, REFRESH, SP, IS_CONNECTED, CENTER, SP_SRC, SP_DST, TSP_CITIES, MENU
    }

    /**
     * Repaint every thing to the default color
     */
    public void refresh() {
//        new MyGUI(fileName);
//        this.getContentPane().removeAll();
        canvas.refresh();
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

}
