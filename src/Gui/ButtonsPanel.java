package Gui;
import api.NodeData;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonsPanel extends JPanel {

    MyGUI GUI;

    /**
     * Buttons for the user:
     */

    JButton centerButton;
    JButton isConnectedButton;
    JButton tsp;
    JButton SP;
    JTextField src;
    JTextField dst;
    JTextField cities;

    public ButtonsPanel(MyGUI g) {
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

        isConnectedButton = new JButton("IsConnected");
        this.add(isConnectedButton);
        isConnectedButton.addActionListener(new IsConnectedListener());

        centerButton = new JButton("Center");
        this.add(centerButton);
        centerButton.addActionListener(new CenterListener());

        tsp = new JButton("tsp");
        this.add(tsp);
        tsp.addActionListener(new TSPListener());

        SP = new JButton("SP");
        this.add(SP);
        SP.addActionListener(new SPListener());

        src = new JTextField("Src node");
        this.add(src);

        dst = new JTextField("Dst node");
        this.add(dst);

        cities = new JTextField("tsp, Enter nodes in this format: 2,3,4... ");
        this.add(cities);

    }

    /**
     * Listener for IsConnected button
     */
    private class IsConnectedListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.IS_CONNECTED;
            String text = "" + GUI.canvas.graphAlgo.isConnected();
            GUI.Console.setText("IsConnected? == " + text);
        }
    }

    /**
     * Listener for Center button
     */
    private class CenterListener extends JFrame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.CENTER;
            String text = "" + GUI.canvas.graphAlgo.center();
            GUI.Console.setText("The center is: " + text);
        }
    }

    /**
     * Listener for SP button
     */
    private class SPListener extends JFrame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = "";
            if (src.getText().length() == 0 || dst.getText().length() == 0) {
                text += "Null";
            } else {
                int source = Integer.parseInt(src.getText());
                int destination = Integer.parseInt(dst.getText());
                text += GUI.canvas.graphAlgo.shortestPath(source, destination).toString();
                text += " and the dist is " + GUI.canvas.graphAlgo.shortestPathDist(source, destination);
            }
            GUI.Console.setText("Shortest path is == " + text);
        }
    }
    /**
     * Listener for TSP button
     */
    private class TSPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = "";
            List<NodeData> city = new LinkedList<>();
            if (cities.getText().length() == 0) {
                text += "Null";
            } else {
                for (String s : cities.getText().split(",")) {
                    city.add(GUI.canvas.graph.getNode(Integer.parseInt(s)));
                }
                text += (GUI.canvas.graphAlgo.tsp(city).toString());
            }

            GUI.Console.setText("tsp: " + text);
        }
    }
}
