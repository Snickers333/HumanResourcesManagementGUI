import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> startGUI());
//        Worker worker = new Worker("afsd", "afdf", Position.CEO, 123,31231);
//        Worker worker1 = new Worker("ahhlhglh", "afasdf", Position.CEO, 12,3131);
//        Worker worker2 = new Worker("aklljhlh", "afbbcxbdf", Position.CEO, 1,331);
//        WorkerList workerList = new WorkerList();
//        workerList.addWorker(worker);
//        workerList.addWorker(worker1);
//        workerList.addWorker(worker2);
//        Object[][] tmp = workerList.getWorkerArray();
//        for (int i = 0; i < tmp.length; i++){
//            System.out.println(Arrays.toString(tmp[i]));
//        }
    }

    public static void startGUI() {
        JFrame mainFrame = new JFrame();

        Worker worker = new Worker("afsd", "afdf", Position.CEO, 123,31231);
        Worker worker1 = new Worker("ahhlhglh", "afasdf", Position.CEO, 12,3131);
        Worker worker2 = new Worker("aklljhlh", "afbbcxbdf", Position.CEO, 1,331);
        WorkerList workerList = new WorkerList();
        workerList.addWorker(worker);
        workerList.addWorker(worker1);
        workerList.addWorker(worker2);
        Object[][] tmp = workerList.getWorkerArray();

        // Button showing another JFrame with list of all Employees
        JButton showListButton = new JButton("Show Employee List");
        showListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUIWorkerList(tmp);
                mainFrame.dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.add(showListButton);

        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }
}
