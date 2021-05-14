import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
    }

    public static void startGUI() {
        WorkerModel workerModel = new WorkerModel();
        workerModel.getWorkerListFromFile();

        showGUI(workerModel);
    }

    static void showGUI(WorkerModel workerModel) {
        JFrame mainFrame = new JFrame("Menu");
        // Button showing another JFrame with list of all Employees
        JButton showListButton = new JButton("Show Employee List");
        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIWorkerList(workerModel);
                mainFrame.dispose();
            }
        });

        JButton addEmpButton = new JButton("Add Employee");
        addEmpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIAdd(workerModel);
            }
        });

        JButton saveButton = new JButton("Save Employee List");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workerModel.saveWorkerListToFile();
                JOptionPane.showMessageDialog(mainFrame,"Successfully saved !");
            }
        });

        JButton loadButton = new JButton("Load Employee List");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workerModel.removeAll();
                workerModel.getWorkerListFromFile(WorkerModel.getSelectedFile());
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(loadButton);
        mainPanel.add(showListButton);
        mainPanel.add(addEmpButton);
        mainPanel.add(saveButton);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(screenDim.width / 3, screenDim.height / 4);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
