import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
    }

    public static void startGUI() {
        WorkerList workerList = WorkerList.getWorkerListFromFile();
        Object[][] tmp = workerList.getWorkerArray();
        JFrame mainFrame = new JFrame();

        // Button showing another JFrame with list of all Employees
        JButton showListButton = new JButton("Show Employee List");
        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIWorkerList(tmp);
                mainFrame.dispose();
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
