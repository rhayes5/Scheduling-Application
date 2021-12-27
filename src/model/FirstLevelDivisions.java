package model;

public class FirstLevelDivisions {
    private int id;
    private String division;
    private int countryId;

    /**
     * The first level division constructor.
     * @param id The first level division id
     * @param division The first level division name
     * @param countryId The first level division country id
     */
    public FirstLevelDivisions(int id, String division, int countryId)
    {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Gets the first level division id
     * @return The first level division id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the first level division id
     * @param id The first level division id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the first level division name
     * @return The first level division name
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the first level division name
     * @param division The first level division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the first level division country id
     * @return The first level division country id
     */
    public int getCountryId()
    {
        return countryId;
    }

    /**
     * Sets the first level division country id
     * @param countryId The first level division country id
     */
    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }

    /**
     * Overrides to string method to print the first level division name
     * @return the first level division name
     */
    @Override
    public String toString()
    {
        return division;
    }
}
