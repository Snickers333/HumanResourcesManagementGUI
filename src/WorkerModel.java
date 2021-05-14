import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkerModel extends AbstractTableModel {
    private String[] headers = {"ID", "First Name", "Last Name", "Position", "Experience", "Salary"};

    private List<Worker> list;

    public WorkerModel() {
        this.list = new ArrayList<>();
    }

    public void addWorker(Worker p) {
        list.add(p);
    }

    public List<Worker> getWorkerList() {
        return new ArrayList<>(this.list);
    }

    public void getWorkerListFromFile() {
        JFrame frame = new JFrame();
        File file = new File("Data.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.addWorker(Worker.getWorkerFromStringArray(scanner.nextLine().split(",")));
            }
        } catch (FileNotFoundException e) {
            int decision = JOptionPane.showConfirmDialog(frame, "Data file has not been found !" + '\n' + "Do you want to point to another file ?");
            if (decision == JOptionPane.YES_OPTION) {
                    getWorkerListFromFile(getSelectedFile());
                }
        };
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

    public void getWorkerListFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.addWorker(Worker.getWorkerFromStringArray(scanner.nextLine().split(",")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
    }

    public void saveWorkerListToFile() {
        try {
            FileWriter fw = new FileWriter("Data.txt");
            for (Worker worker : list) {
                fw.write(worker.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("IO Error - File not saved");
        }
    }

    public Object[][] getWorkerArray() {
        Object[][] tmp = new Object[this.list.size()][list.get(0).getArrayFromWorker().length];
        for (int i = 0; i < this.list.size(); i++) {
            tmp[i] = list.get(i).getArrayFromWorker();
        }
        return tmp;
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
        Worker worker = list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return worker.getId();
            }
            case 1 -> {
                return worker.getFirstName();
            }
            case 2 -> {
                return worker.getLastName();
            }
            case 3 -> {
                return worker.getPosition();
            }
            case 4 -> {
                return worker.getExperience();
            }
            case 5 -> {
                return worker.getSalary();
            }
        }
        return null;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }
}
