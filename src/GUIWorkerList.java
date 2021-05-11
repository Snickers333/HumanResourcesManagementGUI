import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIWorkerList extends JFrame {
    private static String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};
    private JTable table;
    private JPanel panel;

    public GUIWorkerList(Object[][] workerList) {
        table = new JTable(workerList ,headers);

        // Back to menu Button
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

        // On Frame close - Back to menu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GUI.startGUI();
                dispose();
            }
        });
    }
}
