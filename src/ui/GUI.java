package ui;

import model.EmpModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
    }

    public static void startGUI() {
        EmpModel empModel = new EmpModel();
        empModel.getEmpListFromFile(new File("Data.txt"));

        showGUI(empModel);
    }

    static void showGUI(EmpModel empModel) {
        JFrame mainFrame = new JFrame("Menu");
        // Button showing another JFrame with list of all Employees
        JButton showListButton = new JButton("Show Employee List");

        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(empModel, 0);
                mainFrame.dispose();
            }
        });

        JButton addEmpButton = new JButton("Add Employee");
        addEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(empModel, 3);
            }
        });

        JButton saveButton = new JButton("Save Employee List");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empModel.saveEmpListToFile();
            }
        });

        JButton loadButton = new JButton("Load Employee List");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file;
                try {
                    file = EmpModel.getSelectedFile();
                    if (file != null)
                        empModel.removeAll();
                    empModel.getEmpListFromFile(file);
                } catch (NullPointerException ignored) {
                }
            }
        });

        JButton editEmpButton = new JButton("Edit Employee");
        editEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(empModel, 1);
                mainFrame.dispose();
            }
        });

        JButton removeEmpButton = new JButton("Remove Employee");
        removeEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(empModel, 2);
                mainFrame.dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        ImageIcon homePicture = new ImageIcon("HRMGUI.png");
        JLabel picLabel = new JLabel(homePicture);
        picLabel.setPreferredSize(new Dimension(475, 385));

        mainPanel.add(picLabel);
        mainPanel.add(showListButton);
        mainPanel.add(addEmpButton);
        mainPanel.add(editEmpButton);
        mainPanel.add(removeEmpButton);
        mainPanel.add(saveButton);
        mainPanel.add(loadButton);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(screenDim.width / 3, screenDim.height / 4);
        mainFrame.setSize(500, 600);
        mainFrame.setVisible(true);
    }
}
