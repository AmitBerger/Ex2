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

    JButton centerButton;
    JButton isConnectedButton;
    public JButton SP;





    public ButtonsPanel(MyGUI g){
        this.setBackground(Color.GRAY);
        // Sets the table for the buttons
        this.setLayout(new GridLayout(3, 1));
        GUI = g;
        AddButtons();
    }


    private void AddButtons() {

        this.setBackground(Color.GRAY);
        // Sets the table for the buttons
        this.setLayout(new GridLayout(3, 1));

        isConnectedButton = new JButton("IsConnected");
        this.add(isConnectedButton);
        isConnectedButton.addActionListener(new IsConnectedListener());

        centerButton = new JButton("Center");
        this.add(centerButton);
        centerButton.addActionListener(new CenterListener());

        SP = new JButton("SP");
        this.add(SP);
        SP.addActionListener(new SPListener());

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
    private class CenterListener extends JFrame implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.CENTER;
            String text = "" + GUI.canvas.graphAlgo.center();
            GUI.Console.setText("The center is: "+text);
        }
    }
    private class SPListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = "" + GUI.canvas.graphAlgo.shortestPath(0,3);
            GUI.Console.setText("Shortest path is == "+text);
        }
    }


}
