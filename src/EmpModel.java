import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmpModel extends AbstractTableModel {
    private String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};

    private List<Employee> list;

    public EmpModel() {
        this.list = new ArrayList<>();
    }

    public void addEmp(Employee e) {
        list.add(e);
    }

    public List<Employee> getEmpList() {
        return new ArrayList<>(this.list);
    }

    public void getEmpListFromFile() {
        JFrame frame = new JFrame();
        File file = new File("Data.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.addEmp(Employee.getEmpFromStringArray(scanner.nextLine().split(",")));
            }
        } catch (FileNotFoundException e) {
            int decision = JOptionPane.showConfirmDialog(frame, "Data file has not been found !" + '\n' + "Do you want to point to another file ?");
            if (decision == JOptionPane.YES_OPTION) {
                getEmpListFromFile(getSelectedFile());
            }
        }
        ;
    }

    // Method to choose a specific file
    public static File getSelectedFile() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

        int value = fileChooser.showOpenDialog(null);
        if (value == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void getEmpListFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.addEmp(Employee.getEmpFromStringArray(scanner.nextLine().split(",")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
        JOptionPane.showMessageDialog(null, "Data Loaded !");
    }

    public void saveEmpListToFile() {
        try {
            FileWriter fw = new FileWriter("Data.txt");
            for (Employee emp : list) {
                fw.write(emp.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("IO Error - File not saved");
        }
    }

    public Object[][] getEmpArray() {
        Object[][] tmp = new Object[this.list.size()][list.get(0).getArrayFromWorker().length];
        for (int i = 0; i < this.list.size(); i++) {
            tmp[i] = list.get(i).getArrayFromWorker();
        }
        return tmp;
    }

    public void editEmp(int index, Employee emp) {
        this.list.set(index, emp);
    }

    public void removeEmp(int i) {
        this.list.remove(i);
    }

    public void removeAll() {
        this.list = new ArrayList<>();
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
