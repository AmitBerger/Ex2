package Gui;

import javax.swing.*;
import java.awt.*;

public class MyGUI extends JFrame {

    /**
     * The input file name.
     */
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

    /**
     * Jars functions:
     */
    MenuBar menu;
    GraphCanvas canvas;
    ButtonsPanel buttons;
    ConsoleControl control;


    /**
     * Stores all the panels.
     */
    Container pane;

    /**
     * Constructor:
     */
    public MyGUI() {
        this.setResizable(true);
        menu = new MenuBar(this);
        SetFrame(false);
        this.setTitle("MY GUI");
        this.setResizable(false);
    }
    public MyGUI(String file) {
        this.setResizable(true);
        menu = new MenuBar(this);
        canvas = new GraphCanvas(file);
        // Creating all the panels/frames
        buttons = new ButtonsPanel(this);
        control = new ConsoleControl(this);
        // Initialize the jar
        fileName = canvas.fileName;

        SetFrame(true);

        this.setTitle("MY GUI");
        this.setResizable(false);
    }

    public void Init(String file) {
        canvas = new GraphCanvas(file);
        // Creating all the panels/frames
        buttons = new ButtonsPanel(this);
        control = new ConsoleControl(this);
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
        // Adding a nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        // Create and set the window.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // If !flag only the menu will appear
        if (flag) {
            // Filling a container(pane).
            SetPanels();
        }

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
        pane.add(control);
        // Adding functions buttons panel
        pane.add(buttons);

    }

    /**
     * Constants for recording the input mode
     */
    enum InputMode {
        ADD_NODES, IS_CONNECTED, CENTER, MENU
    }

    private static class ConsoleControl extends JPanel {

        MyGUI GUI;

        public ConsoleControl(MyGUI g) {
            GUI = g;
            this.setLayout(new BorderLayout());
            this.add(GUI.canvas);
            GUI.Console = new JLabel((""));
            this.add(GUI.Console, BorderLayout.NORTH);
        }

    }

    /**
     * Main - Scheduler
     */
    public static void main(String[] args) {
        new MyGUI();
    }

    /**
     * Repaint everything to the default color
     */
    public void refresh() {
        canvas.refresh();
    }

}
