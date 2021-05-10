import GUIComponents.GUIWorkerList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
    }

    public static void startGUI() {
        JFrame mainFrame = new JFrame();

        JButton showListButton = new JButton("Show Employee List");
        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIWorkerList();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.add(showListButton);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
