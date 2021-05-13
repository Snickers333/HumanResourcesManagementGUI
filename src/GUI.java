import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
    }

    public static void startGUI() {
        WorkerModel workerModel = WorkerModel.getWorkerListFromFile();

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

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(showListButton);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(screenDim.width / 2, screenDim.height / 4);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
