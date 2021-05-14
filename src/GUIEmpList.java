import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIEmpList extends JFrame {
    private JTable table;
    private JPanel panel;

    public GUIEmpList(EmpModel empModel, int mode) { // MODE 0 - Show List / MODE 1 - Edit Employee / MODE 2 - Remove Employee
        super("Employee List");
        table = new JTable(empModel);

        JButton backButton = new JButton("Back to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.showGUI(empModel);
                dispose();
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel = new JPanel();
        panel.add(scroll);
        panel.add(backButton);
        panel.setLayout(new FlowLayout());

        setContentPane(panel);
        setVisible(true);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 3, screenDim.height / 4);
        pack();

        // On Frame close - Back to menu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GUI.showGUI(empModel);
                dispose();
            }
        });
    }
}
