package src.models;

import java.util.List;

public class Customer extends Shopper {
    private int customerID;
    private String forename;
    private String surname;
    private Address address; // TODO: double check this works instead of having to use houseName and postcode

    public Customer() {
        
    }
    
    public Customer(String forename, String surname, Address address) {
        this.forename = forename;
        this.surname = surname;
        this.address = address;
        this.customerID = -1; // if customerID is -1, then it is a new customer
    }

    public Customer(int customerID, String forename, String surname, Address address) {
        this.customerID = customerID;
        this.forename = forename;
        this.surname = surname;
        this.address = address;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    // get fields
    public String[] getFields() {
        String[] fields = new String[4];
        fields[0] = Integer.toString(this.customerID);
        fields[1] = this.forename;
        fields[2] = this.surname;
        fields[3] = String.valueOf(this.address.getAddressID());
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[4];
        fieldNames[0] = "Customer ID";
        fieldNames[1] = "Forename";
        fieldNames[2] = "Surname";
        fieldNames[3] = "Address ID";
        return fieldNames;
    }

    @Override
    public String toString() {
        return "Customer{" + "forename=" + forename + ", surname=" + surname + ", address=" + address + '}';
    }
}
