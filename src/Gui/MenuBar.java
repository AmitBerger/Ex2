package Gui;

import Implementation.MyDWGAlgorithm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JFrame  {

    MyGUI GUI;
    JMenuBar MenuBar;
    JMenu FileTab;
    JMenuItem SaveTab, LoadTab;

    public MenuBar(MyGUI g) {
        GUI = g;
        Menu();
    }

    public void Menu() {
        MenuBar = new JMenuBar();
        FileTab = new JMenu("File");
        SaveTab = new JMenuItem("Save");
        LoadTab = new JMenuItem("Load");
        FileTab.add(SaveTab);
        FileTab.add(LoadTab);
        SaveTab.addActionListener(new SaveListener());
        LoadTab.addActionListener(new LoadListener());
        MenuBar.add(FileTab);
        GUI.setJMenuBar(MenuBar);
    }


    private class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.MENU;
            GUI.canvas.graphAlgo.save("SavedGraph.json");
            GUI.Console.setText("saved as SavedGraph.json!!!");
        }
    }

    private class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GUI.mode = MyGUI.InputMode.MENU;
            JFileChooser j = new JFileChooser("data/");
            j.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
            j.addChoosableFileFilter(filter);
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                String file_path = j.getSelectedFile().getAbsolutePath();
                GUI.Init(file_path);
            }
        }
    }

}
