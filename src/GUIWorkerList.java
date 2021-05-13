import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIWorkerList extends JFrame {
    private JTable table;
    private JPanel panel;

    public GUIWorkerList(WorkerModel workerModel) {
        super("Worker List");
        table = new JTable(workerModel);
        // Back to menu Button
        JButton backButton = new JButton("Back to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.startGUI();
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
                GUI.startGUI();
                dispose();
            }
        });
    }
}
