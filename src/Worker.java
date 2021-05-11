public class Worker {
    private static int counter = 1;

    private int id;
    private String firstName;
    private String lastName;
    private Position position;
    private int experience;
    private int salary;

    public Worker(String firstName, String lastName, Position position, int experience, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.experience = experience;
        this.salary = salary;
        this.id = counter;
        counter++;
    }

    public Object[] getArrayFromWorker() {
        return new Object[]{id, firstName, lastName, position, experience, salary};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID=" + id +
                ", FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", Position=" + position +
                ", Experience=" + experience +
                ", Salary=" + salary;
    }
}
