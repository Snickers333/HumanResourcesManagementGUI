public class Employee {
    private static int counter = 1;

    private int id;
    private String firstName;
    private String lastName;
    private Position position;
    private int experience;
    private int salary;

    public Employee(String firstName, String lastName, Position position, int experience, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.experience = experience;
        this.salary = salary;
        this.id = counter;
        counter++;
    }

    public Employee(int id, String firstName, String lastName, Position position, int experience, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.experience = experience;
        this.salary = salary;
        this.id = id;
        counter++;
    }

    public static Employee getEmpFromStringArray(String[] arr) {
        return new Employee(Integer.parseInt(arr[0]), arr[1], arr[2], Position.valueOf(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Position getPosition() {
        return position;
    }

    public int getExperience() {
        return experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + "," + firstName + "," + lastName + "," + position + "," + experience + "," + salary;
    }
}
