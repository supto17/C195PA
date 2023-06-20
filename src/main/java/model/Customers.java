package model;

public class Customers {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    public int divisionID;

    public Customers(int customerID, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionID = divisionID;
    }

    /**
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @return customerPostalCode
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * @return customerPhoneNumber
     */
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    /**
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }
}
