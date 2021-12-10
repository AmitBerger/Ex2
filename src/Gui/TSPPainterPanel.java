package Gui;

import api.EdgeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

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
            GUI.Console.setText("The shortest path is:");
        }
    }

    /**
     * Listener for Refresh Path button
     */
    private class RFListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.REFRESH;
            GUI.refresh();
            GUI.buttons.addNodeButton.setEnabled(true);
            GUI.buttons.removeNodeButton.setEnabled(true);
            GUI.buttons.addEdgeButton.setEnabled(true);
            GUI.buttons.removeEdgeButton.setEnabled(true);
            refreshButton.setEnabled(true);
            shortestPathButton.setEnabled(true);
            GUI.buttons.isConnectedButton.setEnabled(true);
            GUI.buttons.centerButton.setEnabled(true);
            GUI.Console.setText("Refreshed!");
        }
    }

    /**
     * Worker class for doing traversals
     */
    private class TraversalThread extends SwingWorker<Boolean, Object> {
        /**
         * The path that needs to paint
         */
        private final LinkedList<EdgeData> path;

        /**
         * The Constructor of TraversalThread
         */
        public TraversalThread(LinkedList<EdgeData> path) {
            this.path = path;
        }

        @Override
        public Boolean doInBackground() {
            GUI.buttons.addNodeButton.setEnabled(false);
            GUI.buttons.removeNodeButton.setEnabled(false);
            GUI.buttons.addEdgeButton.setEnabled(false);
            GUI.buttons.removeEdgeButton.setEnabled(false);
            refreshButton.setEnabled(false);
            shortestPathButton.setEnabled(false);
            GUI.buttons.isConnectedButton.setEnabled(false);
            GUI.buttons.centerButton.setEnabled(false);
            GUI.Console.setText("Refreshed");
            return GUI.canvas.paintTraversal(path);
        }

        @Override
        protected void done() {
            try {
                if (path.isEmpty() && path != null) {  // test the result of doInBackground()
                    GUI.Console.setText("There is no path. Please refresh.");
                }
                refreshButton.setEnabled(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
