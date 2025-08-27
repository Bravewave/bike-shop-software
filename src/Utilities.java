package src;

import java.awt.*;
import javax.swing.*;

import java.sql.*;
import java.util.Base64;
import java.util.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import src.db.QueryHandler;
import src.models.*;
import src.models.FrameSet.FrameType;
import src.models.Handlebars.HandlebarType;
import src.models.Order.Status;
import src.models.Wheels.WheelType;
import src.models.Wheels.BrakeType;


public class Utilities {
    // TODO: move common methods here
    // TODO: database add methods need to be tested
    // to ensure there's no duplicates

    // this started off as general functions, but it's now almost entirely sql related
    // DO NOT ask me to refactor this

    // code goes weeeeeeeeeeeeeeeee


    // returns an icon with the given path and size
    public static ImageIcon parseImage(String path, int res) {
        return new ImageIcon(new ImageIcon("src/assets/" + path).getImage().getScaledInstance(res, res, Image.SCALE_DEFAULT));
    }

    public static String generateRandomSerialNo() {
        return ((int) (Math.random() * 1000000)) + "";
    }

    public static int generateRandomID() {
        return (int) (Math.random() * 1000000);
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9]", "");
    }

    private static String generateRandomName(String type) {
        // uses 2 lists of bike-related words to generate a random name
        // with a random number of words

        if (type == "postcode") {
            // generate a 5-7 digit postcode with letters and numbers
            String postcode = "";
            for (int i = 0; i < (int) (Math.random() * 3) + 5; i++) {
                if (Math.random() > 0.5) {
                    postcode += (int) (Math.random() * 10);
                } else {
                    postcode += (char) ((int) (Math.random() * 26) + 65);
                }
            }
            System.out.println("postcode: " + postcode);
            return postcode;
        }

        String[] descriptors = {"super", "speedy", "fast", "cheap", "exotic", "red", "blue", "green", "mega", "ultra", "ultimate", "bike", "proCyclist", "fun", "two", "one", "AAA", "trek", "expedition"};
        // switch statement to select the correct list of words based on the type

        String[] nouns;
        switch (type) {
            case "bike":
                nouns = new String[] {"bike", "cycle", "tandem", "velocipede", "speed", "bicycle", "cycling"};
                break;
            case "frame":
                nouns = new String[] {"frame", "dynamo", "sport"};
                break;
            case "wheels":
                nouns = new String[] {"wheels", "rollers", "tires", "rims", "tubes"};
                break;
            case "handlebar":
                nouns = new String[] {"handlebar", "bar", "stem", "grips"};
                break;
            default:
                nouns = new String[] {"bikeCo", "bikes", "accessories", "rides", "cyclists", "tires"};
        }

        String name = "";

        int numWords = (int) (Math.random() * 3);

        for (int i = 0; i < numWords; i++) {
            name += capitalize(descriptors[(int) (Math.random() * descriptors.length)]) + " ";
        }

        name += capitalize(nouns[(int) (Math.random() * nouns.length)]);

        // re-generates the name if it doesn't fit in the database schema
        if (name.length() > 30) {
            return generateRandomName(type);
        }

        return name;
    }

    // public static void insertOrder(Order order) throws SQLException {
    //     // inserts the new order into the database
    //     new QueryHandler().updateDB(
    //         "INSERT INTO orders VALUES (" +
    //         order.getOrderNo() + ", " +
    //         order.getCustomerID() + ", " +
    //         order.getBikeSerialNo() + ", " +
    //         order.getConfirmedBy() + ", " +
    //         order.getDate() + ", " +
    //         order.getStatus() + ", " +
    //         ")");
    //     System.out.println("Inserted order");
    // }

    public static String encodePassword(String password) {
        // returns an encoded password using base64
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static boolean checkPassword(String password, String encodedPassword) {
        // returns true if the password matches the encoded password
        return encodePassword(password).equals(encodedPassword);
    }

    public static void addFrame(FrameSet frame, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the frame already exists in the database
        List<String[]> rs = qh.queryDB("SELECT * FROM frame_sets WHERE serial_number = '" + frame.getSerialNumber() + "' AND brand = '" + frame.getBrand() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Frame already exists");
            // update stock
            qh.updateDB("UPDATE frame_sets SET stock_count = stock_count + 1 WHERE serial_number = '" + frame.getSerialNumber() + "' AND brand = '" + frame.getBrand() + "'");
            return;
        }
        else {
            // inserts the new frame into the database
            qh.updateDB(
                "INSERT INTO frame_sets VALUES (DEFAULT, '" +
                frame.getName() + "', '" +
                frame.getBrand() + "', '" +
                frame.getSerialNumber() + "', '" +
                frame.getFrameType().toString() + "', " +
                frame.getSize() + ", " +
                frame.getGears() + ", " +
                frame.getHasShockAbsorbers() + ", " +
                frame.getUnitCost() + ", " +
                frame.getStockCount() +
                ")");
            System.out.println("Inserted frame");
        }
    }

    public static void addWheels(Wheels wheels, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the wheels already exists in the database
        List<String[]> rs = qh.queryDB("SELECT * FROM wheels WHERE serial_number = '" + wheels.getSerialNumber() + "' AND brand = '" + wheels.getBrand() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Wheels already exists");
            // update stock
            qh.updateDB("UPDATE wheels SET stock_count = stock_count + 1 WHERE serial_number = '" + wheels.getSerialNumber() + "' AND brand = '" + wheels.getBrand() + "'");
            return;
        }
        else {
            // inserts the new wheels into the database
            qh.updateDB(
                "INSERT INTO wheels VALUES (DEFAULT, '" +
                wheels.getName() + "', '" +
                wheels.getBrand() + "', '" +
                wheels.getSerialNumber() + "', " +
                wheels.getDiameter() + ", '" +
                wheels.getBrakeType().toString() + "', '" +
                wheels.getWheelType().toString() + "', " +
                wheels.getUnitCost() + ", " +
                wheels.getStockCount() +
                ")");
            System.out.println("Inserted wheels");
        }
    }

    public static void addHandlebar(Handlebars handlebar, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the handlebar already exists in the database
        List<String[]> rs = qh.queryDB("SELECT * FROM handlebars WHERE serial_number = '" + handlebar.getSerialNumber() + "' AND brand = '" + handlebar.getBrand() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Handlebar already exists");
            // update stock
            qh.updateDB("UPDATE handlebars SET stock_count = stock_count + 1 WHERE serial_number = '" + handlebar.getSerialNumber() + "' AND brand = '" + handlebar.getBrand() + "'");
            return;
        }
        else {
            // inserts the new handlebar into the database
            qh.updateDB(
                "INSERT INTO handlebars VALUES (DEFAULT, '" +
                handlebar.getName() + "', '" +
                handlebar.getBrand() + "', '" +
                handlebar.getSerialNumber() + "', '" +
                handlebar.getHandlebarType().toString() + "', " +
                handlebar.getUnitCost() + ", " +
                handlebar.getStockCount() +
                ")");
            System.out.println("Inserted handlebar");
        }
    }

    public static boolean addBike(Bike bike, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the bike already exists in the database
        List<String[]> rs = qh.queryDB("SELECT * FROM bikes WHERE serial_number = '" + bike.getSerialNumber() + "' AND brand = '" + bike.getBrand() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Bike already exists");
            // update stock
            qh.updateDB("UPDATE bikes SET stock_count = stock_count + 1 WHERE serial_number = '" + bike.getSerialNumber() + "' AND brand = '" + bike.getBrand() + "'");
            return true;
        }
        else {
            int frameID = 0;
            int wheelsID = 0;
            int handlebarID = 0;

            // get the id of the components where the serial number matches the component's serial number
            List<String[]> frameRS = qh.queryDB("SELECT frameset_id FROM frame_sets WHERE serial_number = '" + bike.getFrameSet().getSerialNumber() + "' AND brand = '" + bike.getFrameSet().getBrand() + "' AND stock_count > 0");
            List<String[]> wheelsRS = qh.queryDB("SELECT wheels_id FROM wheels WHERE serial_number = '" + bike.getWheels().getSerialNumber() + "' AND brand = '" + bike.getWheels().getBrand() + "' AND stock_count > 0");
            List<String[]> handlebarRS = qh.queryDB("SELECT handlebars_id FROM handlebars WHERE serial_number = '" + bike.getHandlebars().getSerialNumber() + "' AND brand = '" + bike.getHandlebars().getBrand() + "' AND stock_count > 0");

            System.out.println("frameRS: " + Arrays.deepToString(frameRS.toArray()));
            System.out.println("wheelsRS: " + Arrays.deepToString(wheelsRS.toArray()));
            System.out.println("handlebarRS: " + Arrays.deepToString(handlebarRS.toArray()));

            // if any of the components don't exist, add them to the database
            // if (frameRS.size() == 0) {
            //     addFrame(bike.getFrameSet(), qh);
            //     frameRS = qh.queryDB("SELECT frameset_id FROM frame_sets WHERE serial_number = '" + bike.getFrameSet().getSerialNumber() + "'");
            // }
            // if (wheelsRS.size() == 0) {
            //     addWheels(bike.getWheels(), qh);
            //     wheelsRS = qh.queryDB("SELECT wheels_id FROM wheels WHERE serial_number = '" + bike.getWheels().getSerialNumber() + "'");
            // }
            // if (handlebarRS.size() == 0) {
            //     addHandlebar(bike.getHandlebars(), qh);
            //     handlebarRS = qh.queryDB("SELECT handlebars_id FROM handlebars WHERE serial_number = '" + bike.getHandlebars().getSerialNumber() + "'");
            // }

            // if any of the components don't exist or are out of stock, return
            if (frameRS.size() == 0 || wheelsRS.size() == 0 || handlebarRS.size() == 0) {
                System.out.println("One of the components is out of stock");
                return false;
            }

            // get the id from the result set
            for (String[] row : frameRS) {
                frameID = Integer.parseInt(row[0]);
            }
            for (String[] row : wheelsRS) {
                wheelsID = Integer.parseInt(row[0]);
            }
            for (String[] row : handlebarRS) {
                handlebarID = Integer.parseInt(row[0]);
            }

            // inserts the new bike into the database
            int key = qh.updateDB(
                "INSERT INTO bikes VALUES (DEFAULT, '" +
                bike.getName() + "', '" +
                bike.getBrand() + "', '" +
                bike.getSerialNumber() + "', " +
                frameID + ", " +
                wheelsID + ", " +
                handlebarID + ", " +
                bike.getUnitCost() + ", " +
                bike.getStockCount() + ", " +
                bike.isCustom() +
                ")");
            bike.setBikeID(key);
            System.out.println("Inserted bike");
            return true;
        }
    }

    public static void addCustomer(Customer customer, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the customer already exists in the database
        // if customerID is -1, then the customer doesn't exist
        System.out.println("adding customer");
        List<String[]> rs = qh.queryDB("SELECT * FROM customers WHERE address_id = " + customer.getAddress().getAddressID());
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Customer already exists");
            return;
        }
        else {
            int addressID = 0;

            // get the id of the address where the address id matches the address's address id
            List<String[]> addressRS = qh.queryDB("SELECT address_id FROM addresses WHERE address_id = " + customer.getAddress().getAddressID());
            System.out.println("addressRS: " + Arrays.deepToString(addressRS.toArray()));

            // if the address doesn't exist, add it to the database
            if (addressRS.size() == 0) {
                System.out.println("adding address beep boop");
                addAddress(customer.getAddress(), qh);
                System.out.println("added address beep boop");
                addressRS = qh.queryDB("SELECT address_id FROM addresses WHERE address_id = " + customer.getAddress().getAddressID());
            }

            // get the id from the result set
            for (String[] row : addressRS) {
                addressID = Integer.parseInt(row[0]);
            }

            System.out.println("addressID: " + addressID);

            customer.getAddress().setAddressID(addressID);

            // inserts the new customer into the database
            int key = qh.updateDB(
                "INSERT INTO customers VALUES (DEFAULT, '" +
                customer.getForename() + "', '" +
                customer.getSurname() + "', " +
                customer.getAddress().getAddressID() +
                ")");
            customer.setCustomerID(key);
            System.out.println("Inserted customer");
        }
    }

    public static void addAddress(Address address, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the address already exists in the database
        // if addressID is -1, then the address doesn't exist
        System.out.println("adding address");
        List<String[]> rs = qh.queryDB("SELECT * FROM addresses WHERE address_id = '" + address.getAddressID() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Address already exists");
            return;
        }
        else {
            // inserts the new address into the database
            int key = qh.updateDB(
                "INSERT INTO addresses VALUES (DEFAULT, '" +
                address.getHouseName() + "', '" +
                address.getRoadName() + "', '" +
                address.getCityName() + "', '" +
                address.getPostcode() +
                "')");
            address.setAddressID(key);
            System.out.println("Inserted address");
        }
    }

    public static boolean addOrder(Order order, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // checks if the order already exists in the database
        // if orderID is -1, then the order doesn't exist
        System.out.println("adding order");
        List<String[]> rs = qh.queryDB("SELECT * FROM orders WHERE order_id = '" + order.getOrderID() + "'");
        if (rs.size() > 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Order already exists");
            return false;
        }
        else {
            int customerID = 0;
            int bikeID = 0;

            // get the id of the customer and bike where the id matches the order's customer and bike id
            List<String[]> customerRS = qh.queryDB("SELECT customer_id FROM customers WHERE customer_id = '" + order.getCustomer().getCustomerID() + "'");
            List<String[]> bikeRS = qh.queryDB("SELECT bike_id FROM bikes WHERE bike_id = '" + order.getBike().getBikeID() + "'");

            System.out.println("customerRS: " + Arrays.deepToString(customerRS.toArray()));
            System.out.println("bikeRS: " + Arrays.deepToString(bikeRS.toArray()));

            // if any of the components don't exist, add them to the database
            if (customerRS.size() == 0) {
                System.out.println("customer doesn't exist");
                addCustomer(order.getCustomer(), qh);
                System.out.println("customer added");
                customerRS = qh.queryDB("SELECT customer_id FROM customers WHERE customer_id = '" + order.getCustomer().getCustomerID() + "'");
            }
            // either the bike doesn't exist or the bike's components don't exist but will allow building the bike from components
            if (bikeRS.size() == 0) {
                // if this is false, the bike was a custom bike and the components don't exist/are out of stock
                order.getBike().setCustom(true);
                if (addBike(order.getBike(), qh) == false) { 
                    return false;
                }
                bikeRS = qh.queryDB("SELECT bike_id FROM bikes WHERE bike_id = '" + order.getBike().getBikeID() + "'");
            }

            // get the id from the result set
            for (String[] row : customerRS) {
                customerID = Integer.parseInt(row[0]);
            }
            for (String[] row : bikeRS) {
                bikeID = Integer.parseInt(row[0]);
            }

            order.getBike().setBikeID(bikeID);
            order.getCustomer().setCustomerID(customerID);

            System.out.println("orderDate: " + order.getOrderDate().toString());

            // inserts the new order into the database
            int key = qh.updateDB(
                "INSERT INTO orders VALUES (DEFAULT, " +
                order.getCustomer().getCustomerID() + ", " +
                "DEFAULT" + ", " + // sets the assigned staff to null
                order.getBike().getBikeID() + ", '" +
                order.getOrderDate().toString() + "', '" +
                order.getStatus() + "'" +
                ")");
            order.setOrderID(key);
            System.out.println("Inserted order");
            return true;
        }
        
    }

    public static void decreaseStock(Bike bike, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets the stock of each part of the bike
        List<String[]> frameRS = qh.queryDB("SELECT stock_count FROM frame_sets WHERE serial_number = '" + bike.getFrameSet().getSerialNumber() + "' AND brand = '" + bike.getFrameSet().getBrand() + "'");
        List<String[]> wheelsRS = qh.queryDB("SELECT stock_count FROM wheels WHERE serial_number = '" + bike.getWheels().getSerialNumber() + "' AND brand = '" + bike.getWheels().getBrand() + "'");
        List<String[]> handlebarRS = qh.queryDB("SELECT stock_count FROM handlebars WHERE serial_number = '" + bike.getHandlebars().getSerialNumber() + "' AND brand = '" + bike.getHandlebars().getBrand() + "'");

        // gets the stock from the result set
        int frameStock = 0;
        int handlebarStock = 0;
        int wheelStock = 0;
        for (String[] row : frameRS) {
            frameStock = Integer.parseInt(row[0]);
            System.out.println("frameStock: " + frameStock);
        }
        for (String[] row : handlebarRS) {
            handlebarStock = Integer.parseInt(row[0]);
        }
        for (String[] row : wheelsRS) {
            wheelStock = Integer.parseInt(row[0]);
        }

        // decreases the stock of each part of the bike
        qh.updateDB("UPDATE frame_sets SET stock_count = " + (frameStock - 1) + " WHERE serial_number = '" + bike.getFrameSet().getSerialNumber() + "' AND brand = '" + bike.getFrameSet().getBrand() + "'");
        qh.updateDB("UPDATE wheels SET stock_count = " + (wheelStock - 1) + " WHERE serial_number = '" + bike.getWheels().getSerialNumber() + "' AND brand = '" + bike.getWheels().getBrand() + "'");
        qh.updateDB("UPDATE handlebars SET stock_count = " + (handlebarStock - 1) + " WHERE serial_number = '" + bike.getHandlebars().getSerialNumber() + "' AND brand = '" + bike.getHandlebars().getBrand() + "'");

    }

    public static void updateBikeStock(QueryHandler qh) throws SQLException {
        // horrifically inefficient way for big bike databases
        // there's probably a better way to do this but i don't care at this point since there's 11 hours left
        if (qh == null) {
            qh = new QueryHandler();
        }
        // for each bike in the database, set the stock to the lowest stock of the parts
        List<String[]> results = qh.queryDB("SELECT bike_id, frameset_id, wheels_id, handlebars_id, brand FROM bikes");
        for (String[] row : results) {
            int bikeID = Integer.parseInt(row[0]);
            int frameID = Integer.parseInt(row[1]);
            int wheelID = Integer.parseInt(row[2]);
            int handlebarID = Integer.parseInt(row[3]);
            String brand = row[4];

            List<String[]> frameRS = qh.queryDB("SELECT stock_count FROM frame_sets WHERE frameset_id = " + frameID);
            List<String[]> wheelRS = qh.queryDB("SELECT stock_count FROM wheels WHERE wheels_id = " + wheelID);
            List<String[]> handlebarRS = qh.queryDB("SELECT stock_count FROM handlebars WHERE handlebars_id = " + handlebarID);

            int frameStock = 0;
            int wheelStock = 0;
            int handlebarStock = 0;
            for (String[] row2 : frameRS) {
                frameStock = Integer.parseInt(row2[0]);
            }
            for (String[] row2 : wheelRS) {
                wheelStock = Integer.parseInt(row2[0]);
            }
            for (String[] row2 : handlebarRS) {
                handlebarStock = Integer.parseInt(row2[0]);
            }

            int minStock = Math.min(frameStock, Math.min(wheelStock, handlebarStock));
            System.out.println("minStock: " + minStock);
            System.out.println("brand: " + brand);

            // if bike is custom and stock is 0, delete the bike
            // some bikes are added with the custom flag but aren't actually custom
            // this is a hacky way to fix that
            // nevermind this isn't worth it with 7 hours left they can be manually removed
            if (minStock < 1 && brand.equals("Custom Built")) {
                System.out.println("deleting bike");
                // qh.updateDB("UPDATE bikes SET hidden = true WHERE bike_id = " + bikeID);
            } else {
                qh.updateDB("UPDATE bikes SET stock_count = " + minStock + " WHERE bike_id = " + bikeID);
            }
            
        }

    }

    public static Order progressOrderStatus(Order order, QueryHandler qh, int staffID) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        if (order.getStatus().equals(Status.PENDING)) {
            order.setStatus(Status.CONFIRMED);
            order.setStaffID(staffID);
        } else if (order.getStatus().equals(Status.CONFIRMED)) {
            order.setStatus(Status.FULFILLED);
        }
        // checks if the order exists in the database
        // if orderID is -1, then the order doesn't exist
        System.out.println("progressing order");
        List<String[]> rs = qh.queryDB("SELECT * FROM orders WHERE order_id = '" + order.getOrderID() + "'");
        if (rs.size() == 0) {
            System.out.println("rs: " + Arrays.deepToString(rs.toArray()));
            System.out.println("Order doesn't exist");
            return null;
        }
        else {
            // updates the order's status in the database
            qh.updateDB(
                "UPDATE orders SET order_status = '" +
                order.getStatus() +
                "' WHERE order_id = " + order.getOrderID()
            );
            // updates the assigned staff member in the database
            qh.updateDB(
                "UPDATE orders SET staff_id = " +
                order.getStaffID() +
                " WHERE order_id = " + order.getOrderID()
            );
            System.out.println("Updated order");
            if (order.getStatus().equals(Status.FULFILLED)) {
                decreaseStock(order.getBike(), qh);
                updateBikeStock(qh);
            }
        }
        return order;
    }

    public static Bike parseBike(String[] result, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        System.out.println("result: " + Arrays.toString(result));
        return new Bike(
            Integer.parseInt(result[0]),
            result[3],
            result[2],
            "Bike",
            result[1],
            getWheels(Integer.parseInt(result[5]), qh),
            getFrameSet(Integer.parseInt(result[4]), qh),
            getHandlebars(Integer.parseInt(result[6]), qh),
            Boolean.valueOf(result[9])
        );
    }

    public static Bike getBikes(int bikeID, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        List<String[]> results = qh.queryDB("SELECT * FROM bikes WHERE bike_id = " + bikeID);
        if (results.size() == 0) {
            return null;
        }
        return parseBike(results.get(0), qh);
    }

    public static Bike[] getBikes(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the bikes from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM bikes");
        Bike[] bikes = new Bike[rs.size()];
        int i = 0;

        // creates a bike object for each row in the result set

        for (String[] row : rs) {
            System.out.println("row: " + Arrays.toString(row));
            bikes[i] = parseBike(row, qh);
            i++;
        }
        System.out.println("bikes: " + Arrays.deepToString(bikes));
        return bikes;
    }

    public static Wheels parseWheels(String[] result) {
        return new Wheels(
            result[3],
            result[2],
            "Wheels",
            result[1],
            Double.parseDouble(result[7]),
            Integer.parseInt(result[8]),
            Integer.parseInt(result[4]),
            WheelType.valueOf(result[6]),
            BrakeType.valueOf(result[5])
        );
    }

    public static Wheels getWheels(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a wheel from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM wheels WHERE wheels_id = " + id).get(0);
        return parseWheels(rs);
    }

    public static FrameSet parseFrameSet(String[] result) {
        return new FrameSet(
            result[3],
            result[2],
            "FrameSet",
            result[1],
            Double.parseDouble(result[8]),
            Integer.parseInt(result[9]),
            Integer.parseInt(result[5]),
            Boolean.valueOf(result[7]),
            Integer.parseInt(result[6]),
            FrameType.valueOf(result[4])
        );
    }

    public static FrameSet getFrameSet(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a frameset from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM frame_sets WHERE frameset_id = " + id).get(0);
        return parseFrameSet(rs);
    }

    public static Handlebars parseHandlebars(String[] result) {
        return new Handlebars(
            result[3],
            result[2],
            "Handlebars",
            result[1],
            Double.parseDouble(result[5]),
            Integer.parseInt(result[6]),
            HandlebarType.valueOf(result[4])
        );
    }

    public static Handlebars getHandlebars(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a handlebar from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM handlebars WHERE handlebars_id = " + id).get(0);
        return parseHandlebars(rs);
    }

    public static Wheels[] getWheels(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the wheels from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM wheels");
        Wheels[] wheels = new Wheels[rs.size()];
        int i = 0;

        // creates a wheel object for each row in the result set
        for (String[] row : rs) {
            wheels[i] = parseWheels(row);
            i++;
        }

        return wheels;
    }

    public static FrameSet[] getFrameSets(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the framesets from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM frame_sets");
        FrameSet[] frameSets = new FrameSet[rs.size()];
        int i = 0;

        // creates a frameset object for each row in the result set
        for (String[] row : rs) {
            frameSets[i] = parseFrameSet(row);
            i++;
        }

        return frameSets;
    }

    public static Handlebars[] getHandlebars(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the handlebars from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM handlebars");
        Handlebars[] handlebars = new Handlebars[rs.size()];
        int i = 0;

        // creates a handlebar object for each row in the result set
        for (String[] row : rs) {
            handlebars[i] = parseHandlebars(row);
            i++;
        }

        return handlebars;
    }

    public static Order parseOrder(String[] result, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        int staff_id;
        if (result[2] == null) {
            staff_id = -1;
        } else {
            staff_id = Integer.parseInt(result[2]);
        }
        return new Order(
            Integer.parseInt(result[0]), // order_id
            staff_id, // 
            java.sql.Date.valueOf(result[4]), 
            Order.Status.valueOf(result[5]), 
            getBikes(Integer.parseInt(result[3]), qh), 
            getCustomers(Integer.parseInt(result[1]), qh));
    }

    public static Order[] getOrders(QueryHandler qh, int customerID) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the orders from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM orders WHERE customer_id = " + customerID);
        Order[] orders = new Order[rs.size()];
        int i = 0;

        // creates an order object for each row in the result set
        for (String[] row : rs) {
            orders[i] = parseOrder(row, qh);
            i++;
        }

        return orders;
    }

    public static Order[] getOrders(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets all the orders from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM orders");
        Order[] orders = new Order[rs.size()];
        int i = 0;

        // creates an order object for each row in the result set
        for (String[] row : rs) {
            orders[i] = parseOrder(row, qh);
            i++;
        }

        return orders;
    }

    public static Customer parseCustomer(String[] result, QueryHandler qh) throws SQLException {
        return new Customer(
            Integer.parseInt(result[0]),
            result[1],
            result[2],
            getAddress(Integer.parseInt(result[3]), qh)
        );
    }

    public static Customer getCustomers(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a customer from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM customers WHERE customer_id = " + id).get(0);
        return parseCustomer(rs, qh);
    }

    public static Address parseAddress(String[] result) {

        return new Address(
            Integer.parseInt(result[0]),
            result[1],
            result[2],
            result[3],
            result[4]
        );
    }

    public static Address getAddress(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get an address from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM addresses WHERE address_id = " + id).get(0);
        return parseAddress(rs);
    }

    public static Staff parseStaff(String[] result) throws SQLException {
        return new Staff(Integer.parseInt(result[0]), result[3], result[1], result[2]);
    }

    public static Staff getStaff(int id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a staff member from the database with the given id
        String[] rs = qh.queryDB("SELECT * FROM staff WHERE staff_id = " + id).get(0);
        return parseStaff(rs);
    }

    public static int getFrameID(FrameSet frameSet, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get the id of a frameset from the database with the given name
        String[] rs = qh.queryDB("SELECT frameset_id FROM frame_sets WHERE brand = '" + frameSet.getBrand() + "' AND serial_number = '" + frameSet.getSerialNumber() + "'").get(0);
        return Integer.parseInt(rs[0]);
    }

    public static int getWheelsID(Wheels wheels, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get the id of a wheel from the database with the given name
        String[] rs = qh.queryDB("SELECT wheels_id FROM wheels WHERE brand = '" + wheels.getBrand() + "' AND serial_number = '" + wheels.getSerialNumber() + "'").get(0);
        return Integer.parseInt(rs[0]);
    }

    public static int getHandlebarsID(Handlebars handlebars, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get the id of a handlebar from the database with the given name
        String[] rs = qh.queryDB("SELECT handlebars_id FROM handlebars WHERE brand = '" + handlebars.getBrand() + "' AND serial_number = '" + handlebars.getSerialNumber() + "'").get(0);
        return Integer.parseInt(rs[0]);
    }

    public static void updateBike(Bike bike, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // updates a bike in the database
        qh.updateDB(
            "UPDATE bikes SET name = '" + bike.getName() + 
            "', brand = '" + bike.getBrand() +
            "', serial_number = '" + bike.getSerialNumber() +
            "', frameset_id = " + getFrameID(bike.getFrameSet(), qh) +
            ", wheels_id = " + getWheelsID(bike.getWheels(), qh) +
            ", handlebars_id = " + getHandlebarsID(bike.getHandlebars(), qh) +
            ", custom = " + bike.isCustom() +
            " WHERE bike_id = " + bike.getBikeID()
            );
        System.out.println("Bike updated");
    }

    public static void updateWheels(Wheels wheels, Wheels prev, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // updates a wheel in the database
        qh.updateDB(
            "UPDATE wheels SET brand = '" + wheels.getBrand() +
            "', serial_number = '" + wheels.getSerialNumber() +
            "', diameter = " + wheels.getDiameter() +
            ", name = '" + wheels.getName() +
            "', brake_type = '" + wheels.getBrakeType() +
            "', wheel_type = '" + wheels.getWheelType() +
            "', unit_cost = " + wheels.getUnitCost() +
            ", stock_count = " + wheels.getStockCount() +
            " WHERE wheels_id = " + getWheelsID(prev, qh)
            );
        System.out.println("Wheels updated");
    }

    public static void updateHandlebars(Handlebars handlebars, Handlebars prev, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // updates a handlebar in the database
        qh.updateDB(
            "UPDATE handlebars SET brand = '" + handlebars.getBrand() +
            "', serial_number = '" + handlebars.getSerialNumber() +
            "', name = '" + handlebars.getName() +
            "', unit_cost = " + handlebars.getUnitCost() +
            ", stock_count = " + handlebars.getStockCount() +
            ", handlebar_type = '" + handlebars.getHandlebarType() +
            "' WHERE handlebars_id = " + getHandlebarsID(prev, qh)
            );
        System.out.println("Handlebars updated");
    }

    public static void updateFrameSet(FrameSet frameSet, FrameSet prev, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // updates a frameset in the database
        qh.updateDB(
            "UPDATE frame_sets SET brand = '" + frameSet.getBrand() +
            "', serial_number = '" + frameSet.getSerialNumber() +
            "', name = '" + frameSet.getName() +
            "', unit_cost = " + frameSet.getUnitCost() +
            ", stock_count = " + frameSet.getStockCount() +
            ", type = '" + frameSet.getFrameType() +
            "', size = " + frameSet.getSize() +
            ", has_shock_absorbers = " + frameSet.getHasShockAbsorbers() +
            " WHERE frameset_id = " + getFrameID(prev, qh)
            );
        System.out.println("FrameSet updated");
    }

    public static Staff[] getStaff(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get all staff members from the database
        List<String[]> rs = qh.queryDB("SELECT staff_id, first_name, last_name, username FROM staff");
        Staff[] staff = new Staff[rs.size()];
        for (int i = 0; i < rs.size(); i++) {
            staff[i] = parseStaff(rs.get(i));
        }
        return staff;
    }

    public static Staff getStaffFromID(int staff_id, QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get a staff member from the database with the given id
        List<String[]> rs = qh.queryDB("SELECT staff_id, first_name, last_name, username FROM staff WHERE staff_id = " + staff_id);
        return parseStaff(rs.get(0));
    }

    public static Customer[] getCustomers(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get all customers from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM customers");
        Customer[] customers = new Customer[rs.size()];
        for (int i = 0; i < rs.size(); i++) {
            customers[i] = parseCustomer(rs.get(i), qh);
        }
        return customers;
    }

    public static Address[] getAddresses(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // get all addresses from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM addresses");
        Address[] addresses = new Address[rs.size()];
        for (int i = 0; i < rs.size(); i++) {
            addresses[i] = parseAddress(rs.get(i));
        }
        return addresses;
    }

    public static boolean deleteOrder(int orderID, QueryHandler qh) throws SQLException{
        if (qh == null) qh = new QueryHandler();

        List<String[]> rs = qh.queryDB("SELECT * FROM orders WHERE order_id = " + orderID);
        Order order = parseOrder(rs.get(0), qh);

        // If the order is pending, and it cannot be deleted, return false
        if (order.getStatus() != Status.PENDING) return false;

        qh.updateDB("DELETE FROM orders WHERE order_id = " + orderID);
        return true;
    }

    public static void amendAddress(int addressID, String house, String street, String city, String postcode, QueryHandler qh) throws SQLException {
        if (qh == null) qh = new QueryHandler();

        Address address = getAddress(addressID, qh);

        qh.updateDB(String.format("UPDATE addresses SET " +
                "house = '%s'," +
                "street = '%s'," +
                "city = '%s'," +
                "postcode = '%s'," +
                "WHERE address_id = %d",
                house, street, city, postcode, addressID));

        System.out.printf("Address for address_id %d successfully updated.%n", addressID);
    }

    public static void amendCustomerDetails(int customerID, String firstName, String lastName, String house, String street, String city, String postcode, QueryHandler qh) throws SQLException {
        if (qh == null) qh = new QueryHandler();

        Customer customer = getCustomers(customerID, qh);

        qh.updateDB(String.format("UPDATE customers SET " +
                "first_name = '%s'," +
                "last_name = '%s'," +
                "address = '%d'," +
                "WHERE customer_id = %d",
                firstName, lastName, customer.getAddress().getAddressID(), customerID));

        amendAddress(customer.getAddress().getAddressID(), house, street, city, postcode, qh);

        System.out.printf("Details for customer_id %d successfully updated.%n", customerID);
    }


    // ****************************************************
    // *                                                  *
    // *                  DEBUG FUNCTIONS                 *
    // *                                                  *
    // ****************************************************

    public static Order getLastOrder(QueryHandler qh) throws SQLException {
        if (qh == null) {
            qh = new QueryHandler();
        }
        // gets the last order from the database
        List<String[]> rs = qh.queryDB("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");
        return parseOrder(rs.get(0), qh);
    }


    public static double generateRandomPrice(int min, int max, boolean even) {
        // generate a random price within the given range
        // if even is true, then the second decimal place should be an even number
        // (to give wheels a price that is divisible by 2 at checkout)
        Random rand = new Random();
        double price = rand.nextInt((max - min) + 1) + min;
        if (even) {
            price += rand.nextInt(5) * 0.1;
        }
        else {
            price += rand.nextInt(10) * 0.1;
        }
        return price;
    }

    // picks a random enum value from the given enum class
    // stolen from https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = new Random().nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    // generates a randon bike
    public static Bike generateRandomBike() {
        Wheels wheels = generateRandomWheels();
        FrameSet frameSet = generateRandomFrameSet();
        Handlebars handlebars = generateRandomHandlebars();
        Bike bike = new Bike(
            generateRandomSerialNo(), // serial number
            generateRandomName("brand"), // brand
            "Bike", // type
            generateRandomName("bike"), // name
            wheels, frameSet, handlebars, // components
            true // assembled
        );
        return bike;
    }

    public static Wheels generateRandomWheels() {
        return new Wheels
        (
            generateRandomSerialNo(), // serial number
            generateRandomName("brand"), // brand
            "Wheels", // type
            generateRandomName("wheels"), // name
            generateRandomPrice(50, 200, true), // unit cost
            1, // stock count
            (int) (20 + Math.random() * 20), // diameter
            randomEnum(WheelType.class), // wheel type
            randomEnum(BrakeType.class) // brake type
        );
    }

    public static FrameSet generateRandomFrameSet() {
        return new FrameSet
        (
            generateRandomSerialNo(), // serial number
            generateRandomName("brand"), // brand
            "Frame", // type
            generateRandomName("frame"), // name
            generateRandomPrice(100, 400, false), // unit cost
            1, // stock count
            (int) (20 + Math.random() * 80), // frame size
            (Math.random() > 0.5), // has suspension
            (int) (Math.random() * 100), // gears
            randomEnum(FrameType.class) // frame type
        );
    }

    public static Handlebars generateRandomHandlebars() {
        return new Handlebars
        (
            generateRandomSerialNo(), // serial number
            generateRandomName("brand"), // brand
            "Handlebars", // type
            generateRandomName("handlebar"), // name
            generateRandomPrice(50, 200, false), // unit cost
            1, // stock count
            randomEnum(HandlebarType.class) // handlebar type
        );
    }

    public static Order generateRandomOrder() {
        return new Order(
            1, // staff id
            new java.sql.Date(new Date().getTime()),
            randomEnum(Status.class), // status
            generateRandomBike(), // bike
            generateRandomCustomer() // customer
        );
    }

    public static Customer generateRandomCustomer() {
        return new Customer(
            generateRandomName("forename"), // forename
            generateRandomName("surname"), // surname
            generateRandomAddress() // address
        );
    }

    public static Address generateRandomAddress() {
        return new Address(
            generateRandomName("house"), // house name
            generateRandomName("road"), // road name
            generateRandomName("city"), // city name
            generateRandomName("postcode") // postcode
        );
    }

    public static void insertRandomWheels() throws SQLException {
        Wheels wheels = generateRandomWheels();
        new QueryHandler().updateDB(
            "INSERT INTO wheels VALUES ('" +
            wheels.getBrand() + "', '" +
            wheels.getSerialNumber() + "', '" +
            wheels.getDiameter() + "', '" +
            wheels.getBrakeType().toString() + "', '" +
            wheels.getBrakeLevers() + "', '" +
            wheels.getWheelType().toString() + "', '" +
            wheels.getName() + "', '" +
            wheels.getUnitCost() + "', '" +
            wheels.getStockCount() + "');");
        System.out.println("Inserted wheels");
    }

    public static void getWheelsCount() throws SQLException {
        int count = new QueryHandler().queryDB("SELECT * FROM wheels").size();
        System.out.println("rs: " + count);
    }

    public static void insertRandomHandlebars() throws SQLException {
        Handlebars handlebars = generateRandomHandlebars();
        new QueryHandler().updateDB(
            "INSERT INTO handlebars VALUES ('" +
            handlebars.getBrand() + "', '" +
            handlebars.getSerialNumber() + "', '" +
            handlebars.getHandlebarType().toString() + "', '" +
            handlebars.getName() + "', '" +
            handlebars.getUnitCost() + "', '" +
            handlebars.getStockCount() + "');");
        System.out.println("Inserted handlebars");
    }

    public static void getHandlebarsCount() throws SQLException {
        int count = new QueryHandler().queryDB("SELECT * FROM handlebars").size();
        System.out.println("rs: " + count);
    }

    public static void insertRandomFrameSet() throws SQLException {
        FrameSet frameSet = generateRandomFrameSet();
        new QueryHandler().updateDB(
                "INSERT INTO frame_sets VALUES (DEFAULT, '" +
                frameSet.getName() + "', '" +
                frameSet.getBrand() + "', '" +
                frameSet.getSerialNumber() + "', '" +
                frameSet.getFrameType() + "', " +
                frameSet.getSize() + ", " +
                frameSet.getGears() + ", " +
                frameSet.getHasShockAbsorbers() + ", " +
                frameSet.getUnitCost() + ", " +
                frameSet.getStockCount() +
                ")");
        System.out.println("Inserted frameset");
    }

    public static void getFrameSetsCount() throws SQLException {
        int count = new QueryHandler().queryDB("SELECT * FROM frame_sets").size();
        System.out.println("count: " + count);
    }

    public static void insertRandomBike() throws SQLException {
        QueryHandler qh = new QueryHandler();
        Bike bike = generateRandomBike();
        addFrame(bike.getFrameSet(), qh);
        addHandlebar(bike.getHandlebars(), qh);
        addWheels(bike.getWheels(), qh);
        addBike(bike, qh);
        System.out.println("Inserted bike");
    }

    public static void getBikesCount() throws SQLException {
        int count = new QueryHandler().queryDB("SELECT * FROM bikes").size();
        System.out.println("rs: " + count);
    }

    

    public static void rebuildTable() throws SQLException {


        // resets the database to the schema in db_creation.txt
        
        System.out.println("Rebuilding database...");

        new QueryHandler().updateDB("DROP TABLE IF EXISTS orders;");
        System.out.println("Dropped orders table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS bikes;");
        System.out.println("Dropped bike table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS handlebars;");
        System.out.println("Dropped handlebars table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS wheels;");
        System.out.println("Dropped wheels table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS frame_sets;");
        System.out.println("Dropped frame_set table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS staff;");
        System.out.println("Dropped staff table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS customers;");
        System.out.println("Dropped customers table");
        new QueryHandler().updateDB("DROP TABLE IF EXISTS addresses;");
        System.out.println("Dropped addresses table");
        
        
        new QueryHandler().updateDB(
            "CREATE TABLE addresses (" +
                "address_id SERIAL," +
                "house VARCHAR(255)," +
                "street VARCHAR(255)," +
                "city VARCHAR(255)," +
                "postcode VARCHAR(7)," +
                "PRIMARY KEY (address_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE customers (" +
                "customer_id SERIAL," +
                "first_name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "address_id BIGINT UNSIGNED," +
                "PRIMARY KEY (customer_id)," +
                "FOREIGN KEY (address_id) REFERENCES addresses(address_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE staff (" +
                "staff_id SERIAL," +
                "first_name VARCHAR(255)," +
                "last_name VARCHAR(255)," +
                "username VARCHAR(255)," +
                "password VARCHAR(255)," +
                "PRIMARY KEY (staff_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE frame_sets (" +
                "frameset_id SERIAL," +
                "name VARCHAR(255)," +
                "brand VARCHAR(255), " +
                "serial_number VARCHAR(255)," +
                "type VARCHAR(8)," +
                "size INT," +
                "gears INT," +
                "has_shock_absorbers BOOLEAN," +
                "unit_cost DOUBLE," +
                "stock_count INT," +
                "PRIMARY KEY (frameset_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE wheels (" +
                "wheels_id SERIAL," +
                "name VARCHAR(255)," +
                "brand VARCHAR(255)," +
                "serial_number VARCHAR(255)," +
                "diameter INT," +
                "brake_type VARCHAR(4)," +
                "wheel_type VARCHAR(8)," +
                "unit_cost DOUBLE," +
                "stock_count INT," +
                "PRIMARY KEY (wheels_id)" +
            ");"
        );
        
        new QueryHandler().updateDB(
            "CREATE TABLE handlebars (" +
                "handlebars_id SERIAL," +
                "name VARCHAR(255)," +
                "brand VARCHAR(255)," +
                "serial_number VARCHAR(255)," +
                "handlebar_type VARCHAR(7)," +
                "unit_cost DOUBLE," +
                "stock_count INT," +
                "PRIMARY KEY (handlebars_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE bikes (" +
                "bike_id SERIAL," +
                "name VARCHAR(255)," +
                "brand VARCHAR(255)," +
                "serial_number VARCHAR(255)," +
                "frameset_id BIGINT UNSIGNED," +
                "wheels_id BIGINT UNSIGNED," +
                "handlebars_id BIGINT UNSIGNED," +
                "unit_cost DOUBLE," +
                "stock_count INT," +
                "custom BOOLEAN," +
                "PRIMARY KEY (bike_id)," +
                "FOREIGN KEY (frameset_id) REFERENCES frame_sets(frameset_id)," +
                "FOREIGN KEY (wheels_id) REFERENCES wheels(wheels_id)," +
                "FOREIGN KEY (handlebars_id) REFERENCES handlebars(handlebars_id)" +
            ");"
        );

        new QueryHandler().updateDB(
            "CREATE TABLE orders (" +
                "order_id SERIAL," +
                "customer_id BIGINT UNSIGNED," +
                "staff_id BIGINT UNSIGNED," +
                "bike_id BIGINT UNSIGNED, " +
                "order_date DATE," +
                "order_status VARCHAR(10)," +
                "PRIMARY KEY (order_id)," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)," +
                "FOREIGN KEY (staff_id) REFERENCES staff(staff_id)," +
                "FOREIGN KEY (bike_id) REFERENCES bikes(bike_id)" +
            ");"
        );

        // add staff accounts

        new QueryHandler().updateDB(
            "INSERT INTO staff VALUES (" +
                "DEFAULT," +
                "'Theo'," +
                "'Cruddace'," +
                "'tcruddace'," +
                "'" + encodePassword("password1") + "');"
        );

        new QueryHandler().updateDB(
            "INSERT INTO staff VALUES (" +
                "DEFAULT," +
                "'Kiran'," +
                "'Da Costa'," +
                "'kdacosta'," +
                "'" + encodePassword("password2") + "');"
        );

        new QueryHandler().updateDB(
            "INSERT INTO staff VALUES (" +
                "DEFAULT," +
                "'Jack'," +
                "'Barker'," +
                "'jbarker'," +
                "'" + encodePassword("password3") + "');"
        );

        new QueryHandler().updateDB(
            "INSERT INTO staff VALUES (" +
                "DEFAULT," +
                "'Michael'," +
                "'Brown'," +
                "'mbrown'," +
                "'" + encodePassword("password4") + "');"
        );

        System.out.println("Rebuilt tables successfully");
    }
}
