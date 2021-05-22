package ui;

import data.EmployeesData;
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

    public GUIEmpList(EmployeesData employeesData, int mode) { // MODE 0 - Show List / MODE 1 - Edit model.Employee / MODE 2 - Remove model.Employee / MODE 3 - Add model.Employee
        table = new JTable(employeesData);
        switch (mode) {
            case 0 -> {
                setTitle("model.Employee List Mode");
                drawTableModel(employeesData);
                showMode(employeesData);
            }
            case 1 -> {
                setTitle("model.Employee Edit Mode");
                drawTableModel(employeesData);
                editMode(employeesData);
            }
            case 2 -> {
                setTitle("model.Employee Remove Mode");
                drawTableModel(employeesData);
                removeMode(employeesData);
            }
            case 3 -> addMode(employeesData);
        }
    }

    public void showMode(EmployeesData employeesData) {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));

        //Sorting
        TableRowSorter<EmployeesData> sorter = new TableRowSorter<>(employeesData);
        sorter.setComparator(3, (Comparator<Position>) (o1, o2) -> o1.toString().compareTo(o2.toString()));
        table.setRowSorter(sorter);

        //Filtering
        String[] filterStrings = {"ID", "First Name", "Last Name", "model.Position", "Experience", "Salary"};
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
                    RowFilter<EmployeesData, Object> rf = RowFilter.regexFilter("(?i)" + regex, filterBox.getSelectedIndex());
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
        RowFilter<EmployeesData, Integer> greaterThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmployeesData, ? extends Integer> entry) {
                return (int) table.getValueAt(entry.getIdentifier(), 5) > Integer.parseInt(searchField.getText());
            }
        };

        //Less than
        RowFilter<EmployeesData, Integer> lessThan = new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends EmployeesData, ? extends Integer> entry) {
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

    private void editMode(EmployeesData employeesData) {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));
        JTextField[] textFields = new JTextField[6];

        textFields[0] = new HintTextField("Enter ID");
        textFields[1] = new HintTextField("Enter Name");
        textFields[2] = new HintTextField("Enter Family Name");
        textFields[3] = new HintTextField("Enter model.Position");
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
                        JOptionPane.showMessageDialog(null, "Valid Salary for this model.Position : From " + position.getMinSalary() + " To " + position.getMaxSalary());
                    } else {
                        Employee emp = Employee.getEmpFromStringArray(empInStr);

                        employeesData.editEmp(employeesData.findEmpIndex(Integer.parseInt(textFields[0].getText())), emp);
                        employeesData.fireTableStructureChanged();
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

    private void removeMode(EmployeesData employeesData) {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridLayout(0, 1, 15, 15));
        JTextField idField = new HintTextField("Here enter emp ID");

        JButton removeButton = new JButton("Remove model.Employee");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                try {
                    id = Integer.parseInt(idField.getText());
                    employeesData.removeEmp(employeesData.findEmpIndex(id));
                    employeesData.fireTableStructureChanged();
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

    private void addMode(EmployeesData employeesData) {
        String name = JOptionPane.showInputDialog(this, "Enter First Name");
        String lastName = JOptionPane.showInputDialog(this, "Enter Last Name");
        Position position = parseValidPosition();
        int exp = parseValidInt("Enter Experience");
        int salary = parseValidInt("Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary(), position);

        employeesData.addEmp(new Employee(name, lastName, position, exp, salary));
        JOptionPane.showMessageDialog(this, "model.Employee Successfully Added !");
    }

    private static int parseValidInt(String message) {
        int errorCounter = 0;
        int output = 0;
        while (errorCounter != 1)
            try {
                output = Integer.parseInt(JOptionPane.showInputDialog(null, message));
                errorCounter++;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value! \nPlease try again.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        return output;
    }

    private static int parseValidInt(String message, Position position) {
        int errorCounter = 0;
        int output = 0;
        while (errorCounter != 1)
            try {
                output = Integer.parseInt(JOptionPane.showInputDialog(null, message));
                errorCounter++;
                if (!(output >= position.getMinSalary() && output <= position.getMaxSalary())) {
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
                position = Position.valueOf(JOptionPane.showInputDialog(null, "Enter model.Position" + '\n' + "MANAGER, ASSISTANT, DESIGNER, ACCOUNTANT, PR, CEO").toUpperCase());
                errorCounter++;
            } catch (IllegalArgumentException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value! \nPlease try again.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        return position;
    }

    private void drawTableModel(EmployeesData employeesData) {
        JPanel panel = new JPanel();
        backButton = new JButton("Back to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUI.showGUI(employeesData);
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
                GUI.showGUI(employeesData);
                dispose();
            }
        });
    }
}
