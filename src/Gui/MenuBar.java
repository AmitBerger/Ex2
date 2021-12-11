package Gui;

import Implementation.MyDWGAlgorithm;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JFrame implements ActionListener {

    private MyGUI GUI;

    //    JFrame frame;
    private JMenuBar j_Menu_Bar;
    private JMenu file_tab;
    private JMenuItem save_tab, load_tab;

    public MenuBar(MyGUI g){
        GUI =g;
        Menu();
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
        GUI.setJMenuBar(j_Menu_Bar);
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


    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem) e.getSource();

        if (source == load_tab) {
            JFileChooser j = new JFileChooser("data/");
            j.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Json files", "json");
            j.addChoosableFileFilter(filter);
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                String file_path = j.getSelectedFile().getAbsolutePath();
                MyDWGAlgorithm GA = new MyDWGAlgorithm();
                GA.load(file_path);

            }
        }

    }

}
