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

    public Worker(int id, String firstName, String lastName, Position position, int experience, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.experience = experience;
        this.salary = salary;
        this.id = id;
    }

    public Object[] getArrayFromWorker() {
        return new Object[]{id, firstName, lastName, position, experience, salary};
    }

    public static Worker getWorkerFromStringArray(String[] arr) {
        return new Worker(Integer.parseInt(arr[0]), arr[1], arr[2], Position.valueOf(arr[3]), Integer.parseInt(arr[4]),Integer.parseInt(arr[5]));
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
        return id + "," + firstName + "," + lastName + "," + position + "," + experience + "," + salary;
    }
}
