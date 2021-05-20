public enum Position {
    MANAGER(3800, 2500),
    ASSISTANT(2400, 1400),
    DESIGNER(8000, 5000),
    ACCOUNTANT(4500, 3700),
    PR(7400, 4300),
    CEO(15900, 9800);

    private final int maxSalary;
    private final int minSalary;

    Position(int maxSalary, int minSalary) {
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public int getMinSalary() {
        return minSalary;
    }
}
