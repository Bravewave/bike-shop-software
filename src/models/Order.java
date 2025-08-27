package src.models;

import java.util.Date;

public class Order {
    public enum Status {
        PENDING,
        CONFIRMED,
        FULFILLED
    }
    private int orderID;
    private int staffID;
    private Date orderDate;
    private Status status;
    private Bike bike;
    private Customer customer;

    public Order() {
        
    }

    public Order(Bike bike, Customer customer) {
        this.staffID = -1; // if staffID is -1, then it is a new order
        this.orderDate = new java.sql.Date(new Date().getTime()); // sets the date to the current date
        this.status = Status.PENDING;
        this.orderID = -1; // if orderID is -1, then it is a new order
        this.bike = bike;
        this.customer = customer;
    }

    public Order(int staffID, Date orderDate, Status status, Bike bike, Customer customer) {
        this.staffID = staffID;
        this.orderDate = orderDate;
        this.status = status;
        this.orderID = -1; // if orderID is -1, then it is a new order
        this.bike = bike;
        this.customer = customer;
    }

    public Order(int orderID, int staffID, Date orderDate, Status status, Bike bike, Customer customer) {
        this.orderID = orderID;
        this.staffID = staffID;
        this.orderDate = orderDate;
        this.status = status;
        this.bike = bike;
        this.customer = customer;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String[] getFields() {
        return new String[] {
            String.valueOf(orderID),
            String.valueOf(staffID),
            orderDate.toString(),
            status.toString(),
            String.valueOf(bike.getBikeID()),
            String.valueOf(customer.getCustomerID())
        };
    }

    public String[] getFieldNames() {
        return new String[] {
            "Order ID",
            "Staff ID",
            "Order Date",
            "Status",
            "Bike ID",
            "Customer ID"
        };
    }

    @Override
    public String toString() {
        return "Order [bike=" + bike + ", customer=" + customer + ", orderDate=" + orderDate + ", orderID=" + orderID
                + ", staffID=" + staffID + ", status=" + status + "]";
    }
}
