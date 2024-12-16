package dk.svoldgaard.mealplan.BE;

public class Meal {
    private int id;       // Add ID field
    private String name;

    public Meal(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Meal(String name) { // Constructor without ID for creating new meals
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
