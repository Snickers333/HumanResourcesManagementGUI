import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;

public class GUIEmpList extends JFrame {
    private JPanel panel;
    private final JTable table;

    public GUIEmpList(EmpModel empModel, int mode) { // MODE 0 - Show List / MODE 1 - Edit Employee / MODE 2 - Remove Employee / MODE 3 - Add Employee
        table = new JTable(empModel);
        switch (mode) {
            case 0 -> {
                setTitle("Employee List Mode");
                drawTableModel(empModel);
                showMode(empModel);
            }
            case 1 -> {
                setTitle("Employee Edit Mode");
                drawTableModel(empModel);
                editMode(empModel);
            }
            case 2 -> {
                setTitle("Employee Remove Mode");
                drawTableModel(empModel);
                removeMode(empModel);
            }
            case 3 -> addMode(empModel);
        }
    }

    public void showMode(EmpModel empModel) {
        setTitle("Employee List Mode");
        drawTableModel(empModel);

        //Sorting
        TableRowSorter<EmpModel> sorter = new TableRowSorter<>(empModel);
        sorter.setComparator(3, (Comparator<Position>) (o1, o2) -> o1.toString().compareTo(o2.toString()));
        table.setRowSorter(sorter);

        //Filtering
        String[] filterStrings = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};
        JComboBox<String> filterBox = new JComboBox<>(filterStrings);

        JTextField filterField = new JTextField("Enter filter here", 10);

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = filterField.getText();
                if (text.equals("Enter filter here") || text.equals("")) {
                    sorter.setRowFilter(null);
                } else {
                    String regex = String.format("^%s$", text);
                    RowFilter<EmpModel, Object> rf = RowFilter.regexFilter("(?i)" + regex, filterBox.getSelectedIndex());
                    sorter.setRowFilter(rf);
                }
            }
        });
        panel.add(filterBox);
        panel.add(filterField);
        panel.add(filterButton);

        //Searching
        String[] searchStrings = {"Salary greater than", "Salary less than"};
        JComboBox<String> searchType = new JComboBox<>(searchStrings);
        JTextField searchField = new JTextField("Enter Value", 10);
        JButton applySearch = new JButton("Apply");

        //Greater than
        RowFilter<EmpModel, Integer> greaterThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmpModel, ? extends Integer> entry) {
                if (((int) table.getValueAt(entry.getIdentifier(), 5) > Integer.parseInt(searchField.getText()))) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        //Less than
        RowFilter<EmpModel, Integer> lessThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmpModel, ? extends Integer> entry) {
                if ((int) table.getValueAt(entry.getIdentifier(), 5) < Integer.parseInt(searchField.getText())) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        //Greater or Less depending on ComboBox selected
        applySearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(null);
                if (!(searchField.getText().equals("") || searchField.getText().equals("Enter Value"))) {
                    int type = searchType.getSelectedIndex();
                    if (type == 0) {
                        sorter.setRowFilter(greaterThan);
                    } else {
                        sorter.setRowFilter(lessThan);
                    }
                }
            }
        });

        panel.add(searchType);
        panel.add(searchField);
        panel.add(applySearch);
    }

    public void editMode(EmpModel empModel) {
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

        JButton editButton = new JButton("Apply changes");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Position position = Position.valueOf(textFields[3].getText());

                if (textFields[5].getText().equals("") || textFields[5].getText().equals("Enter Salary"))
                    textFields[5].setText("0");

                if (Integer.parseInt(textFields[5].getText()) >= position.getMinSalary() && Integer.parseInt(textFields[5].getText()) <= position.getMaxSalary()) {
                    String[] empInStr = new String[6];
                    for (int i = 0; i < textFields.length; i++) {
                        empInStr[i] = textFields[i].getText();
                    }
                    Employee emp = Employee.getEmpFromStringArray(empInStr);
                    empModel.editEmp(empModel.findEmpIndex(Integer.parseInt(textFields[0].getText())), emp);
                    empModel.fireTableStructureChanged();
                } else {
                    JOptionPane.showMessageDialog(null, "Valid Salary for this Position : From " + position.getMinSalary() + " To " + position.getMaxSalary());
                }
            }
        });
        panel.add(editButton);
    }

    public void removeMode(EmpModel empModel) {
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

    public void addMode(EmpModel empModel) {
        String name = (JOptionPane.showInputDialog(this, "Enter First Name"));
        String lastName = JOptionPane.showInputDialog(this, "Enter Last Name");
        Position position = Position.valueOf(JOptionPane.showInputDialog(this, "Enter Position" + '\n' + "MANAGER, ASSISTANT, DESIGNER, ACCOUNTANT, PR, CEO"));
        int exp = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Experience"));
        int salary = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary()));

        while (!(salary >= position.getMinSalary() && salary <= position.getMaxSalary())) {
            JOptionPane.showMessageDialog(this, "Incorrect salary amount, try again", "Alert", JOptionPane.WARNING_MESSAGE);
            salary = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary()));
        }

        empModel.addEmp(new Employee(name, lastName, position, exp, salary));
        JOptionPane.showMessageDialog(this, "Employee Successfully Added !");
    }

    private void drawTableModel(EmpModel empModel) {
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
        setSize(500, 500);

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
