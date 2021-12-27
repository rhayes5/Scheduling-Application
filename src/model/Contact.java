package model;

public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * The contact object constructor.
     * @param id the contact id
     * @param name the contact's name
     * @param email the contact's email
     */
    public Contact(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Sets the contact id
     * @param id the contact id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the contact's id
     * @return the contact's id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the contact name
     * @param name the contact name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the contact's name
     * @return the contact's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the contact's email
     * @param email the contact's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the contact's email
     * @return the contact's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Overrides to string method to print contact id number - contact name in the combo boxes
     * @return a string formatted "id - name"
     */
    @Override
    public String toString()
    {
        return (id + " - " + name);
    }
}
