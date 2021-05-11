import java.util.ArrayList;
import java.util.List;

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

    public Object[][] getWorkerArray() {
        Object[][] tmp = new Object[this.list.size()][list.get(0).getArrayFromWorker().length];
        for (int i = 0; i < this.list.size(); i++) {
            tmp[i] = list.get(i).getArrayFromWorker();
        }
        return tmp;
    }
}
