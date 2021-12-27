package model;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int division;

    /**
     * Constructs the customer object with the customer id, the customer's name, their address, postal code, phone number, and division.
     * @param id The customer id integer
     * @param name The customer name string
     * @param address The address string
     * @param postalCode The postal code string
     * @param phone The phone number string
     * @param division The customer's division
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int division)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }

    /**
     * Returns the customer's id.
     * @return The customer id integer
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the customer's name.
     * @return The customer name string
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer's address.
     * @return The address string
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the customer's postal code.
     * @return The postal code string
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Returns the customer's phone number.
     * @return The phone number string
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the customer's first level division.
     * @return first level division integer
     */
    public int getDivision() {
        return division;
    }

    /**
     * Sets the customer's id.
     * @param id the customer id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the customer's name.
     * @param name the customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the customer's address.
     * @param address the customer address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the customer's postalCode.
     * @param postalCode the customer postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Sets the customer's phone.
     * @param phone the customer phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the customer's first level division.
     * @param division the customer first level division
     */
    public void setDivision(int division) {
        this.division = division;
    }

    /**
     * Overrides to string method to print customer id number - customer name in the combo boxes
     * @return a string formatted "id - name"
     */
    @Override
    public String toString()
    {
        return (id + " - " + name);
    }
}
