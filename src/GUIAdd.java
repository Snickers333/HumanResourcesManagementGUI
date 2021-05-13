import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;

public class GUIAdd {
    private JFrame frame;
    public GUIAdd(WorkerModel workerModel) {
        frame = new JFrame();

        String name = JOptionPane.showInputDialog(frame, "Enter First Name");
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name");
        Position position = Position.valueOf(JOptionPane.showInputDialog(frame, "Enter Position" + '\n' + "MANAGER, ASSISTANT, DESIGNER, ACCOUNTANT, PR, CEO"));
        int exp = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Experience"));
        int salary = Integer.parseInt(JOptionPane.showInputDialog(frame, " Enter Salary"));

        Worker worker = new Worker(name, lastName, position, exp, salary);
        workerModel.addWorker(worker);
    }
}
