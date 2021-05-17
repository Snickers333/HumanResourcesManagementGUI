import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIEmpList extends JFrame {
    private JPanel panel;

    public GUIEmpList(EmpModel empModel, int mode) { // MODE 0 - Show List / MODE 1 - Edit Employee / MODE 2 - Remove Employee
        switch (mode) {
            case 0 -> {
                setTitle("Employee List Mode");
                drawTableModel(empModel);
            }
            case 1 -> {
                setTitle("Employee Edit Mode");
                drawTableModel(empModel);

                JTextField[] textFields = new JTextField[6];
                for (int i = 0; i < textFields.length; i++) {
                    textFields[i] = new JTextField(10);
                    panel.add(textFields[i]);
                }
                textFields[0].setText("Enter ID");
                textFields[1].setText("Enter Name");
                textFields[2].setText("Enter Family Name");
                textFields[3].setText("Enter Position");
                textFields[4].setText("Enter Experience");
                textFields[5].setText("Enter Salary");
                // TODO !!!!!!
            }
            case 2 -> {
                setTitle("Employee Remove Mode");
                drawTableModel(empModel);

                JTextField idField = new JTextField("Here enter emp ID");

                JButton removeButton = new JButton("Remove");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        empModel.removeEmp(empModel.findEmpIndex(Integer.parseInt(idField.getText())));
                        empModel.fireTableStructureChanged();
                    }
                });
                panel.add(idField);
                panel.add(removeButton);
            }
        }

        setContentPane(panel);
        setVisible(true);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenDim.width / 3, screenDim.height / 4);
        setSize(500,500);

        // On Frame close - Back to menu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GUI.showGUI(empModel);
                dispose();
            }
        });
    }

    private void drawTableModel(EmpModel empModel) {
        JTable table = new JTable(empModel);

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
    }
}
