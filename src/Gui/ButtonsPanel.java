package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel{

    MyGUI GUI;

    /**
     * Buttons for the user:
     */
    JButton addNodeButton;
    JButton removeNodeButton;
    JButton addEdgeButton;
    JButton removeEdgeButton;
    JButton centerButton;
    JButton isConnectedButton;
    JButton tspButton;

    /**
     * Text fields to get input from user
     */
    private JTextField SP_SRC;
    private JTextField SP_DST;
    private JTextField TSP_CITIES;


    public ButtonsPanel(MyGUI g){
        this.setBackground(Color.GRAY);
        // Sets the table for the buttons
        this.setLayout(new GridLayout(4, 1));
        GUI = g;
        AddButtons();
    }


    private void AddButtons() {

        this.setBackground(Color.GRAY);
        // Sets the table for the buttons
        this.setLayout(new GridLayout(4, 1));

        addNodeButton = new JButton("Add Nodes");
        this.add(addNodeButton);
        addNodeButton.addActionListener(new AddNodeListener());

        removeNodeButton = new JButton("Remove Nodes");
        this.add(removeNodeButton);
        removeNodeButton.addActionListener(new RmvNodeListener());

        addEdgeButton = new JButton("Add Edges");
        this.add(addEdgeButton);
        addEdgeButton.addActionListener(new AddEdgeListener());

        removeEdgeButton = new JButton("Remove Edges");
        this.add(removeEdgeButton);
        removeEdgeButton.addActionListener(new RmvEdgeListener());

        isConnectedButton = new JButton("IsConnected");
        this.add(isConnectedButton);
        isConnectedButton.addActionListener(new IsConnectedListener());

        centerButton = new JButton("Center");
        this.add(centerButton);
        centerButton.addActionListener(new CenterListener());

    }

    /**
     * Listener for Add Implementation.Node button
     */
    private class AddNodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.ADD_NODES;
            GUI.Console.setText("Click on the jar screen to add a new node");
        }
    }

    /**
     * Listener for Remove Implementation.Node button
     */
    private class RmvNodeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.RMV_NODES;
            GUI.Console.setText("Click on a node to remove.");
        }
    }

    /**
     * Listener for Add Implementation.Edge button
     */
    private class AddEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.ADD_EDGES;
            GUI.Console.setText("Drag from one node to another to remove an edge.");
        }
    }

    /**
     * Listener for Remove Implementation.Edge button
     */
    private class RmvEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.RMV_EDGES;
            GUI.Console.setText("Drag from one node to another to remove an edge.");
        }
    }

    /**
     * Listener for IsConnected button
     */
    private class IsConnectedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.IS_CONNECTED;
            String text = "" + GUI.canvas.graphAlgo.isConnected();
            GUI.Console.setText("IsConnected? == "+text);
        }
    }

    /**
     * Listener for Center button
     */
    private class CenterListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.CENTER;
            String text = "" + GUI.canvas.graphAlgo.center();
            GUI.Console.setText("The center is: "+text);
        }
    }

    /**
     * Listener for shortest path src text field
     */
    private class SP_SRCListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.SP_SRC;
            GUI.Console.setText("Enter the source node key");
        }
    }

    /**
     * Listener for shortest path dst text field
     */
    private class SP_DSTListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.SP_DST;
            GUI.Console.setText("ShortestPath: ");
        }
    }
    /**
     * Listener for SP button
     */
    private class SPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.SP;
            GUI.Console.setText("The shortest path is:");
        }
    }

}
