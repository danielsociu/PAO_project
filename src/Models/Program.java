package Models;

public class Program {
    private String name;
    private int numberYears;

    public Program(String name, int numberYears) {
        this.name = name;
        this.numberYears = numberYears;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumberYears() {
        return numberYears;
    }
    public void setNumberYears(int numberYears) {
        this.numberYears = numberYears;
    }
}
