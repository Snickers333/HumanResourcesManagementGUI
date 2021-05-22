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
        EmployeesData employeesData = new EmployeesData();
        employeesData.getEmpListFromFile(new File("Data.txt"));

        showGUI(employeesData);
    }

    static void showGUI(EmployeesData employeesData) {
        JFrame mainFrame = new JFrame("Menu");
        // Button showing another JFrame with list of all Employees
        JButton showListButton = new JButton("Show model.Employee List");

        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(employeesData, 0);
                mainFrame.dispose();
            }
        });

        JButton addEmpButton = new JButton("Add model.Employee");
        addEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(employeesData, 3);
            }
        });

        JButton saveButton = new JButton("Save model.Employee List");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeesData.saveEmpListToFile();
            }
        });

        JButton loadButton = new JButton("Load model.Employee List");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file;
                try {
                    file = EmployeesData.getSelectedFile();
                    if (file != null)
                        employeesData.removeAll();
                    employeesData.getEmpListFromFile(file);
                } catch (NullPointerException ignored) {
                }
            }
        });

        JButton editEmpButton = new JButton("Edit model.Employee");
        editEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(employeesData, 1);
                mainFrame.dispose();
            }
        });

        JButton removeEmpButton = new JButton("Remove model.Employee");
        removeEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIEmpList(employeesData, 2);
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
