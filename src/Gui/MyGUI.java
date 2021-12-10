package Gui;

import api.NodeData;

import javax.swing.*;
import java.awt.*;

public class MyGUI extends JFrame {

    /** The input file name. */
    String fileName;

    /**
     * Label for the instructions and results.
     */
    JLabel Console;

    /**
     * The input mode - default: Add nodes button.
     */
    InputMode mode = InputMode.ADD_NODES;

    /**
     * Stores last mousedown event position.
     */
    NodeData nodeUnderMouse;

    /** Jars functions: */
    MenuBar menu;
    GraphCanvas canvas;
    ButtonsPanel buttons;
    MouseListenerPanel mouse;
    TSPPainterPanel traveler;

    /** Stores all the panels. */
    Container pane;

    /** Constructor: */
    public MyGUI() {
        this.setResizable(true);
        menu = new MenuBar(this);
        SetFrame(false);
        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public void Init(String file) {
        canvas = new GraphCanvas(file);
        // Creating all the panels/frames
        buttons = new ButtonsPanel(this);
        mouse = new MouseListenerPanel(this);
        traveler = new TSPPainterPanel(this);

        // Initialize the jar
        fileName = canvas.fileName;
        this.setResizable(true);
        SetFrame(true);
        this.setTitle("MY GUI");
        this.setResizable(false);
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
        // If !flag only the menu will appear
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
     * Constants for recording the input mode
     */
    enum InputMode {
        ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, REFRESH, SP, IS_CONNECTED, CENTER, SP_SRC, SP_DST, TSP_CITIES, MENU
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
     * Repaint everything to the default color
     */
    public void refresh() {
        canvas.refresh();
    }

}
