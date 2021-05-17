import javax.swing.*;

public class GUIAdd {
    private JFrame frame;
    public GUIAdd(EmpModel empModel) {
        frame = new JFrame();

        String name = (JOptionPane.showInputDialog(frame, "Enter First Name"));
        String lastName = JOptionPane.showInputDialog(frame, "Enter Last Name");
        Position position = Position.valueOf(JOptionPane.showInputDialog(frame, "Enter Position" + '\n' + "MANAGER, ASSISTANT, DESIGNER, ACCOUNTANT, PR, CEO"));
        int exp = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Experience"));
        int salary = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary()));

        while (!(salary >= position.getMinSalary() && salary <= position.getMaxSalary())) {
            JOptionPane.showMessageDialog(frame, "Incorrect salary amount, try again", "Alert", JOptionPane.WARNING_MESSAGE);
            salary = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Salary between " + position.getMinSalary() + " and " + position.getMaxSalary()));
        }

        empModel.addEmp(new Employee(name, lastName, position, exp, salary));

        JOptionPane.showMessageDialog(frame, "Employee Successfully Added !");
    }
}
