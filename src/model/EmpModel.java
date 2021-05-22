package model;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpModel extends AbstractTableModel {
    private final String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};

    private List<Employee> list;

    public EmpModel() {
        this.list = new ArrayList<>();
    }

    public void addEmp(Employee e) {
        list.add(e);
    }

    public static File getSelectedFile() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

        int value = fileChooser.showOpenDialog(JOptionPane.getRootFrame());
        if (value == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void getEmpListFromFile(File file) {
        JFrame frame = new JFrame();
        // variable that checks if the employees file data was incorrect
        int errorCounter = 0;

        try {
            if (file == null) {
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Employee emp = Employee.getEmpFromStringArray(scanner.nextLine().split(","));
                if (!(emp == null)) {
                    if (!(emp.getSalary() >= emp.getPosition().getMinSalary() && emp.getSalary() <= emp.getPosition().getMaxSalary() || emp.getSalary() == 0)) {
                        errorCounter++;
                        emp.setSalary(0);
                    }
                    this.addEmp(emp);
                }
            }
        } catch (FileNotFoundException e) {
            int decision = JOptionPane.showConfirmDialog(frame, "Data file has not been found !" + '\n' + "Do you want to point to another file ?");
            if (decision == JOptionPane.YES_OPTION) {
                getEmpListFromFile(getSelectedFile());
            } else {
                return;
            }
        }

        if (this.list.size() == 0)
            JOptionPane.showMessageDialog(frame, "There has been incorrect data gathered from file", "Error", JOptionPane.ERROR_MESSAGE);
        if (errorCounter != 0) {
            JOptionPane.showMessageDialog(frame, "There has been incorrect data gathered from file" + '\n' + "Incorrect data has been changed to 0", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void saveEmpListToFile() {
        int decision = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Save to default folder ?");
        FileWriter fw;
        if (decision == JOptionPane.YES_OPTION) {
            try {
                fw = new FileWriter("Data.txt");
                for (Employee emp : list) {
                    fw.write(emp.toString() + "\n");
                }
                fw.close();
                JOptionPane.showMessageDialog(null, "Successfully saved !");
            } catch (IOException e) {
                System.out.println("IO Error - File not saved");
            }
        } else if (decision == JOptionPane.NO_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showSaveDialog(null);
            File selectedPath = fileChooser.getSelectedFile();
            try {
                fw = new FileWriter(selectedPath);
                for (Employee emp : list) {
                    fw.write(emp.toString() + "\n");
                }
                fw.close();
                JOptionPane.showMessageDialog(null, "Successfully saved !");
            } catch (IOException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, "IO Error - File not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editEmp(int index, Employee emp) {
        this.list.set(index, emp);
    }

    public void removeEmp(int index) {
        if (!(index == -1))
            this.list.remove(index);
    }

    public void removeAll() {
        this.list = new ArrayList<>();
    }

    public int findEmpIndex(int index) {
        for (int i = 0; i < this.list.size(); i++) {
            if (list.get(i).getId() == index)
                return i;
        }
        JOptionPane.showMessageDialog(null, "No employee matching this ID was found");
        return -1;
    }

    @Override
    public String getColumnName(int index) {
        return headers[index];
    }

    @Override
    public Class<?> getColumnClass(int index) {
        switch (index) {
            case 0, 4, 5 -> {
                return Integer.class;
            }
            case 1, 2 -> {
                return String.class;
            }
            case 3 -> {
                return Position.class;
            }
        }
        return null;
    }

    @Override
    public int getRowCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee emp = list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return emp.getId();
            }
            case 1 -> {
                return emp.getFirstName();
            }
            case 2 -> {
                return emp.getLastName();
            }
            case 3 -> {
                return emp.getPosition();
            }
            case 4 -> {
                return emp.getExperience();
            }
            case 5 -> {
                return emp.getSalary();
            }
        }
        return null;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
