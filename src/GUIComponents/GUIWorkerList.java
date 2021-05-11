package GUIComponents;

import javax.swing.*;

public class GUIWorkerList extends JFrame {
    private static String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};
    private JTable table;
    private JPanel panel;

    public GUIWorkerList(Object[][] workerList) {
        table = new JTable(workerList ,headers);
        panel = new JPanel();
        panel.add(table);
        this.setContentPane(panel);
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
