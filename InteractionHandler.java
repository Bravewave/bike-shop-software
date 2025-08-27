package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import src.models.*;
import src.models.Wheels.WheelType;
import src.models.Wheels.BrakeType;
import src.db.QueryHandler;

public class InteractionHandler {
    // handles interactions between the GUI and the database

    // stores the customer's basket
    private List<Bike> basket = new ArrayList<Bike>();
    // private int numBikes = (int) (Math.random() * 20);
    private Bike[] bikes;
    private Wheels[] wheels;
    private FrameSet[] frames;
    private Handlebars[] handlebars;
    private Order[] orders;
    private Customer customer;
    private Customer[] customers;
    private Address[] addresses;
    public enum LoginType { 
        NONE, 
        CUSTOMER, 
        STAFF 
    };
    private LoginType loginType = LoginType.NONE;
    private Staff staff;

    public InteractionHandler() {
        updateStock();
    }

    public void updateStock() {
        try {
            bikes = Utilities.getBikes(null);
            System.out.println("Bikes:" + bikes.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching bikes from database");
            e.printStackTrace();
        }
        try {
            wheels = Utilities.getWheels(null);
            System.out.println("Wheels:" + wheels.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching wheels from database");
            e.printStackTrace();
        }
        try {
            frames = Utilities.getFrameSets(null);
            System.out.println("Frames:" + frames.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching frames from database");
            e.printStackTrace();
        }
        try {
            handlebars = Utilities.getHandlebars(null);
            System.out.println("Handlebars:" + handlebars.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching handlebars from database");
            e.printStackTrace();
        }
        try {
            orders = Utilities.getOrders(null);
            System.out.println("Orders:" + orders.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching orders from database");
            e.printStackTrace();
        }
        try {
            customers = Utilities.getCustomers(null);
            System.out.println("Customers:" + customers.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching customers from database");
            e.printStackTrace();
        }
        try {
            addresses = Utilities.getAddresses(null);
            System.out.println("Addresses:" + addresses.length);
        }
        catch (SQLException e) {
            System.out.println("Error fetching addresses from database");
            e.printStackTrace();
        }

    }

    public boolean checkCredentials(String username, char[] password) throws SQLException {
        // TODO: connect to database and check credentials
        // wow look how secure this is
        List<String[]> staff = new QueryHandler().queryDB(String.format("SELECT staff_id, first_name, last_name, username FROM staff WHERE username='%s' AND password='%s'", Utilities.sanitize(username), Utilities.encodePassword(String.valueOf(password))));
        if (!staff.isEmpty()) {
            System.out.println("rs: " + Arrays.deepToString(staff.get(0)));
            loginType = LoginType.STAFF;
            this.staff = Utilities.parseStaff(staff.get(0));
            return true;
        }
        return false;
    }

    private Address getAddress(String houseName, String postcode) throws SQLException {
        List<String[]> addresses = new QueryHandler().queryDB(String.format("SELECT * FROM addresses WHERE house='%s' AND postcode='%s'", Utilities.sanitize(houseName), Utilities.sanitize(postcode)));
        if (!addresses.isEmpty()) {
            return Utilities.parseAddress(addresses.get(0));
        }
        return null;
    }

    public boolean checkCustomerCredentials(String firstName, String lastName, String houseName, String postcode) throws SQLException {
        List<String[]> customers = new QueryHandler().queryDB(String.format("SELECT customer_id, first_name, last_name, address_id FROM customers WHERE first_name='%s' AND last_name='%s' AND address_id='%s'", Utilities.sanitize(firstName), Utilities.sanitize(lastName), getAddress(houseName, postcode).getAddressID()));
        if (!customers.isEmpty()) {
            System.out.println("rs: " + Arrays.deepToString(customers.get(0)));
            loginType = LoginType.CUSTOMER;
            this.customer = Utilities.parseCustomer(customers.get(0), null);
            return true;
        }
        return false;
    }

    public void logout() {
        loginType = LoginType.NONE;
        staff = null;
        customer = null;
    }

    // deprecated
    private Bike decodeSerial(String serial) {
        // TODO: add logic to decode the serialised bike
        return new Bike();
    }

    public boolean createOrder() {
        if (customer == null) {
            System.out.println("No customer assigned");
            return false; // no customer assigned
        }
        for (Bike bike : basket) {
            System.out.println("bike: " + bike.getName());
            Order order = new Order(
                bike,
                customer
            );
            System.out.println("order: " + order);
            try {
                Utilities.addOrder(order, null);
                basket.clear();
                return true;
            }
            catch (SQLException e) {
                System.out.println("Error adding order to database");
                e.printStackTrace();
                return false;
            }
        }
        

        // TODO: update stock, create order, create customer account, etc.



        // wait for 2 seconds
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        return false; // order failed
    }

    public void handleConfiguration(Bike bike) {
        // TODO: sort out order processing
        // Bike bike = decodeSerial(bikeSerial);
        System.out.println("Bike serial: " + bike.getSerialNumber());
        if (!basket.contains(bike)) {
            basket.add(bike);
            System.out.println("Added to basket");
        } else {
            System.out.println("Bike already in basket");
        }
    }

    

    public Bike[] getProducts() {
        // returns all products
        // TODO: replace with bike object/serialisation
        // return an array of anywhere between 0 and 20 random bikes
        
        return bikes;
        
    }

    public Bike[] getProducts(String filter, String search) {
        // returns the first 4 products from start index that match the filter and search
        // TODO: filter products based on filter/search
        // TODO: improve search algorithm because it's dogshit
        // BUG: error if only the filter is specified but cba to fix it rn
        // since the filter needs to be properly implemented anyway


        // return all bikes that contain the search string in toString
        Bike[] bikeStrings = new Bike[bikes.length];
        int j = 0;
        for (int i = 0; i < bikes.length; i++) {
            if (bikes[i] != null && bikes[i].toString().toLowerCase().contains(search.toLowerCase())) {
                bikeStrings[j] = bikes[i];
                j++;
            }
        }
        for (int i = j; i < bikes.length; i++) {
            bikeStrings[i] = null;
        }

        return bikeStrings;
    }
    //test

    public List<Bike> getBasket() {
        // returns the customer's basket
        return basket;
    }

    public int getBasketSize() {
        // returns the number of items in the customer's basket
        return basket.size();
    }

    public void removeFromBasket(int index) {
        // removes the bike with the specified serial from the customer's basket
        basket.remove(index);
    }

    // deprecated?
    public void removeFromBasket(String serial) {
        // removes the bike with the specified serial from the customer's basket
        basket.remove(serial);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Wheels[] getWheels() {
        return wheels;
    }

    public FrameSet[] getFrames() {
        return frames;
    }

    public Handlebars[] getHandlebars() {
        return handlebars;
    }

    public Staff getStaff() {
        return staff;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public Order[] getOrders() {
        return orders;
    }

    public Order[] getOrders(Customer customer) {
        // returns all orders for the specified customer
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().getCustomerID() == customer.getCustomerID()) {
                customerOrders.add(order);
            }
        }
        return customerOrders.toArray(new Order[0]);
    }

    public Order getOrder(int orderID) {
        // returns the order with the specified orderID
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public Address[] getAddresses() {
        return addresses;
    }
}
