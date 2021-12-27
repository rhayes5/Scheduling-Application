package model;

public class Countries {
    private int id;
    private String name;

    /**
     * The countries object constructor
     * @param id the country id
     * @param name the country name
     */
    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets and returns the country id.
     * @return the country id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets and returns the country name.
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /**
     * Overrides to string method to print the name of the country in the combo boxes.
     * @return The country name
     */
    //
    @Override
    public String toString() {
        return name;
    }
}