package ui;

import model.EmpModel;
import model.Employee;
import model.Position;
import utils.HintTextField;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;

public class GUIEmpList extends JFrame {
    private final JTable table;
    private JButton backButton;

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
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));

        //Sorting
        TableRowSorter<EmpModel> sorter = new TableRowSorter<>(empModel);
        sorter.setComparator(3, (Comparator<Position>) (o1, o2) -> o1.toString().compareTo(o2.toString()));
        table.setRowSorter(sorter);

        //Filtering
        String[] filterStrings = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};
        JComboBox<String> filterBox = new JComboBox<>(filterStrings);

        JTextField filterField = new HintTextField("Enter filter here");

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
        activePanel.add(filterBox);
        activePanel.add(filterField);
        activePanel.add(filterButton);

        //Searching
        String[] searchStrings = {"Salary greater than", "Salary less than"};
        JComboBox<String> searchType = new JComboBox<>(searchStrings);
        JTextField searchField = new HintTextField("Enter Value");
        JButton applySearch = new JButton("Apply");


        //Greater than
        RowFilter<EmpModel, Integer> greaterThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmpModel, ? extends Integer> entry) {
                return (int) table.getValueAt(entry.getIdentifier(), 5) > Integer.parseInt(searchField.getText());
            }
        };

        //Less than
        RowFilter<EmpModel, Integer> lessThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmpModel, ? extends Integer> entry) {
                return (int) table.getValueAt(entry.getIdentifier(), 5) < Integer.parseInt(searchField.getText());
            }
        };

        //Greater or Less depending on ComboBox selected
        applySearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(null);
                if (!(searchField.getText().equals("") || searchField.getText().equals("Enter Value"))) {
                    int type = searchType.getSelectedIndex();
                    try {
                        if (type == 0) {
                            sorter.setRowFilter(greaterThan);
                        } else {
                            sorter.setRowFilter(lessThan);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter number !", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        activePanel.add(searchType);
        activePanel.add(searchField);
        activePanel.add(applySearch);
        activePanel.add(backButton);

        add(activePanel, BorderLayout.AFTER_LAST_LINE);
        setSize(455, 735);
    }

    private void editMode(EmpModel empModel) {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));
        JTextField[] textFields = new JTextField[6];

        textFields[0] = new HintTextField("Enter ID");
        textFields[1] = new HintTextField("Enter Name");
        textFields[2] = new HintTextField("Enter Family Name");
        textFields[3] = new HintTextField("Enter Position");
        textFields[4] = new HintTextField("Enter Experience");
        textFields[5] = new HintTextField("Enter Salary");

        for (int i = 0; i < textFields.length; i++) {
            activePanel.add(textFields[i]);
        }

        JButton editButton = new JButton("Apply changes");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] empInStr = new String[6];
                for (int i = 0; i < textFields.length; i++) {
                    empInStr[i] = textFields[i].getText();
                }
                empInStr[3] = empInStr[3].toUpperCase();

                try {
                    Integer.parseInt(empInStr[0]);
                    Position position = Position.valueOf(empInStr[3]);
                    int salary = Integer.parseInt(empInStr[5]);
                    if (!(salary >= position.getMinSalary() && salary <= position.getMaxSalary())) {
                        JOptionPane.showMessageDialog(null, "Valid Salary for this Position : From " + position.getMinSalary() + " To " + position.getMaxSalary());
                    } else {
                        Employee emp = Employee.getEmpFromStringArray(empInStr);

                        empModel.editEmp(empModel.findEmpIndex(Integer.parseInt(textFields[0].getText())), emp);
                        empModel.fireTableStructureChanged();
                    }
                } catch (IllegalArgumentException | NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect Data", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        activePanel.add(editButton);
        activePanel.add(backButton);
        add(activePanel, BorderLayout.AFTER_LAST_LINE);
        setSize(455, 775);
    }

    private void removeMode(EmpModel empModel) {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));
        JTextField idField = new HintTextField("Here enter emp ID");

        JButton removeButton = new JButton("Remove Employee");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(idField.getText());
                    empModel.removeEmp(empModel.findEmpIndex(id));
                    empModel.fireTableStructureChanged();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect number", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        activePanel.add(idField);
        activePanel.add(removeButton);
        activePanel.add(backButton);
        add(activePanel, BorderLayout.AFTER_LAST_LINE);
        setSize(455, 570);
    }

    private void addMode(EmpModel empModel) {
        String name = JOptionPane.showInputDialog(this, "Enter First Name");
        String lastName = JOptionPane.showInputDialog(this, "Enter Last Name");
        Position position = parseValidPosition();
        int exp = parseValidInt("Enter Experience", null);
        int salary = parseValidInt("Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary(), position);

        empModel.addEmp(new Employee(name, lastName, position, exp, salary));
        JOptionPane.showMessageDialog(this, "Employee Successfully Added !");
    }

    private static int parseValidInt(String message, Position position) {
        int errorCounter = 0;
        int output = 0;
        while (errorCounter != 1)
            try {
                output = Integer.parseInt(JOptionPane.showInputDialog(null, message));
                errorCounter++;
                if (position != null && !(output >= position.getMinSalary() && output <= position.getMaxSalary())) {
                    JOptionPane.showMessageDialog(null, "Incorrect value! \nPlease try again.", "Alert", JOptionPane.WARNING_MESSAGE);
                    errorCounter--;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value! \nPlease try again.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        return output;
    }

    private static Position parseValidPosition() {
        int errorCounter = 0;
        Position position = null;
        while (errorCounter != 1) {
            try {
                position = Position.valueOf(JOptionPane.showInputDialog(null, "Enter Position" + '\n' + "MANAGER, ASSISTANT, DESIGNER, ACCOUNTANT, PR, CEO").toUpperCase());
                errorCounter++;
            } catch (IllegalArgumentException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value! \nPlease try again.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        return position;
    }

    private void drawTableModel(EmpModel empModel) {
        JPanel panel = new JPanel();
        backButton = new JButton("Back to menu");
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

        panel.add(scroll);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.NORTH);
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
