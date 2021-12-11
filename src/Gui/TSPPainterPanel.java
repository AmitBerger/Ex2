package Gui;

import api.EdgeData;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TSPPainterPanel extends JPanel{

    /**
     * Buttons for the user:
     */
    MyGUI GUI;
    JButton refreshButton;
    JButton shortestPathButton;

    public TSPPainterPanel(MyGUI g){
        GUI = g;
        BuildPanel();
    }

    private void BuildPanel() {
        // traversal buttons
        this.setLayout(new GridLayout(2, 1));

        refreshButton = new JButton("Refresh");
        this.add(refreshButton);
        refreshButton.addActionListener(new RFListener());

        shortestPathButton = new JButton("Shortest Path");
        this.add(shortestPathButton);
        shortestPathButton.addActionListener(new SPListener());
    }



    /**
     * Listener for SP button
     */
    private class SPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.SP;
        }
    }

    /**
     * Listener for Refresh Path button
     */
    private class RFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.REFRESH;
            GUI.canvas.graphAlgo.load(GUI.fileName);
            GUI.canvas.graph = GUI.canvas.graphAlgo.getGraph();
            GUI.refresh();
            GUI.Console.setText("Refreshed!");
        }
    }

}
