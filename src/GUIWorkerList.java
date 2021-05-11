import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIWorkerList extends JFrame {
    private static String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};
    private JTable table;
    private JPanel panel;

    public GUIWorkerList(Object[][] workerList) {
        table = new JTable(workerList ,headers);

        JButton backButton = new JButton("Back to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.startGUI();
                dispose();
            }
        });


        panel = new JPanel();
        panel.add(table);
        panel.add(backButton);

        setContentPane(panel);
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
