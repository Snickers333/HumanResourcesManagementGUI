import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WorkerList {
    private List<Worker> list;

    public WorkerList() {
        this.list = new ArrayList<>();
    }

    public void addWorker(Worker p) {
        list.add(p);
    }

    public List<Worker> getWorkerList() {
        return new ArrayList<>(this.list);
    }

    public static WorkerList getWorkerListFromFile() {
        WorkerList workerList = new WorkerList();
        File file = new File("Data.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                workerList.addWorker(Worker.getWorkerFromStringArray(scanner.nextLine().split(",")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return workerList;
    }

    public void saveWorkerListToFile() {
        try {
            FileWriter fw = new FileWriter("Data.txt");
            for (int i = 0; i < list.size(); i++) {
                fw.write(list.get(i).toString() + "\n");
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
}
