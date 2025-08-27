package src.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.Utilities;
import src.gui.GUIFrame;
import src.gui.misc.StaffDashboardAddItem;
import src.gui.misc.StaffDashboardEditItem;
import src.gui.misc.StaffDashboardEditOrder;
import src.models.*;

public class StaffDashboardPanel extends JPanel {

    private GUIFrame guiFrame;
    private String filter = "---";

    private Object[][] parseData(Product[] product) {
        Object[][] data = new Object[product.length][product[0].getFields().length];
        for (int i = 0; i < product.length; i++) {
            for (int j = 0; j < product[i].getFields().length; j++) {
                data[i][j] = product[i].getFields()[j];
            }
        }
        return data;
    }

    private String[] parseColumnNames(Product product) {
        String[] columnNames = new String[product.getFieldNames().length];
        for (int i = 0; i < product.getFieldNames().length; i++) {
            columnNames[i] = product.getFieldNames()[i];
        }
        return columnNames;
    }

    private Object[][] parseData(Order[] order) {
        Object[][] data = new Object[order.length][order[0].getFields().length];
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < order[i].getFields().length; j++) {
                data[i][j] = order[i].getFields()[j];
            }
        }
        return data;
    }

    private String[] parseColumnNames(Order order) {
        String[] columnNames = new String[order.getFieldNames().length];
        for (int i = 0; i < order.getFieldNames().length; i++) {
            columnNames[i] = order.getFieldNames()[i];
        }
        return columnNames;
    }

    private Object[][] parseData(Customer[] customer) {
        Object[][] data = new Object[customer.length][customer[0].getFields().length];
        for (int i = 0; i < customer.length; i++) {
            for (int j = 0; j < customer[i].getFields().length; j++) {
                data[i][j] = customer[i].getFields()[j];
            }
        }
        return data;
    }

    private String[] parseColumnNames(Customer customer) {
        String[] columnNames = new String[customer.getFieldNames().length];
        for (int i = 0; i < customer.getFieldNames().length; i++) {
            columnNames[i] = customer.getFieldNames()[i];
        }
        return columnNames;
    }

    private Object[][] parseData(Address[] address) {
        Object[][] data = new Object[address.length][address[0].getFields().length];
        for (int i = 0; i < address.length; i++) {
            for (int j = 0; j < address[i].getFields().length; j++) {
                data[i][j] = address[i].getFields()[j];
            }
        }
        return data;
    }

    private String[] parseColumnNames(Address address) {
        String[] columnNames = new String[address.getFieldNames().length];
        for (int i = 0; i < address.getFieldNames().length; i++) {
            columnNames[i] = address.getFieldNames()[i];
        }
        return columnNames;
    }

    

    public StaffDashboardPanel(GUIFrame frame) {

        guiFrame = frame;
        setName("Staff Dashboard");
        
        // has all the staff controls (add/remove parts, process orders, etc)
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new BorderLayout());
        JComboBox<String> filter = new JComboBox<String>();
        filter.addItem("---");
        filter.addItem("Bikes");
        filter.addItem("Frames");
        filter.addItem("Wheels");
        filter.addItem("Handlebars");

        stockPanel.add(filter, BorderLayout.NORTH);

        JTable stockTable = new JTable();

        
        
        // String[] columnNames = {"ID", "Name", "Price", "Stock", "Description"};
        Bike[] bikes = guiFrame.getInteractionHandler().getProducts();
        FrameSet[] frames = guiFrame.getInteractionHandler().getFrames();
        Wheels[] wheels = guiFrame.getInteractionHandler().getWheels();
        Handlebars[] handlebars = guiFrame.getInteractionHandler().getHandlebars();

        TableModel bikeModel;
        TableModel frameModel;
        TableModel wheelModel;
        TableModel handlebarModel;

        if (bikes.length > 0) {
            bikeModel = new DefaultTableModel(parseData(bikes), parseColumnNames(bikes[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            bikeModel = new DefaultTableModel(new Object[][] {{"No bikes found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        if (frames.length > 0) {
            frameModel = new DefaultTableModel(parseData(frames), parseColumnNames(frames[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            frameModel = new DefaultTableModel(new Object[][] {{"No frames found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        if (wheels.length > 0) {
            wheelModel = new DefaultTableModel(parseData(wheels), parseColumnNames(wheels[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            wheelModel = new DefaultTableModel(new Object[][] {{"No wheels found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        if (handlebars.length > 0) {
            handlebarModel = new DefaultTableModel(parseData(handlebars), parseColumnNames(handlebars[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            handlebarModel = new DefaultTableModel(new Object[][] {{"No handlebars found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
  

        filter.addActionListener(e -> {
            switch (filter.getSelectedIndex()) {
                case 0:
                    this.filter = "---";
                    stockTable.setModel(null);
                    break;
                case 1:
                    this.filter = "Bike";
                    stockTable.setModel(bikeModel);
                    break;
                case 2:
                    this.filter = "Frame";
                    stockTable.setModel(frameModel);
                    break;
                case 3:
                    this.filter = "Wheel";
                    stockTable.setModel(wheelModel);
                    break;
                case 4:
                    this.filter = "Handlebar";
                    stockTable.setModel(handlebarModel);
                    break;
            }
        });

        stockTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = stockTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && evt.getClickCount() == 2) {
                    String id = stockTable.getValueAt(row, 0).toString();
                    switch (filter.getSelectedIndex()) {
                        case 1:
                            System.out.println("row: " + row);
                            StaffDashboardEditItem.editProduct(bikes[row], guiFrame);
                            break;
                        case 2:
                            System.out.println("row: " + row);
                            StaffDashboardEditItem.editProduct(frames[row], guiFrame);
                            break;
                        case 3:
                            System.out.println("row: " + row);
                            StaffDashboardEditItem.editProduct(wheels[row], guiFrame);
                            break;
                        case 4:
                            System.out.println("row: " + row);
                            StaffDashboardEditItem.editProduct(handlebars[row], guiFrame);
                            break;
                    }
                }
            }
        });

        JButton addProduct = new JButton("+");
        addProduct.addActionListener(e -> {
            switch (filter.getSelectedIndex()) {
                case 1:
                    StaffDashboardAddItem.addBike(guiFrame);
                    break;
                case 2:
                    StaffDashboardAddItem.addFrame(guiFrame);
                    break;
                case 3:
                    StaffDashboardAddItem.addWheels(guiFrame);
                    break;
                case 4:
                    StaffDashboardAddItem.addHandlebars(guiFrame);
                    break;
            }
        });

        stockPanel.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        stockPanel.add(addProduct, BorderLayout.WEST);

        tabbedPane.addTab("Stock", stockPanel);


        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BorderLayout());

        JTable ordersTable = new JTable();

        Order[] orders = guiFrame.getInteractionHandler().getOrders();

        TableModel orderModel;

        if (orders.length > 0) {
            orderModel = new DefaultTableModel(parseData(orders), parseColumnNames(orders[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            orderModel = new DefaultTableModel(new Object[][] {{"No orders found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        ordersTable.setModel(orderModel);

        ordersTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = ordersTable.rowAtPoint(evt.getPoint());
                if (row >= 0 && evt.getClickCount() == 2) {
                    String id = ordersTable.getValueAt(row, 0).toString();
                    StaffDashboardEditOrder.editOrder(orders[row], guiFrame);
                }
            }
        });

        ordersPanel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        tabbedPane.addTab("Orders", ordersPanel);


        JPanel customersPanel = new JPanel();
        JPanel addressPanel = new JPanel();

        customersPanel.setLayout(new BorderLayout());
        addressPanel.setLayout(new BorderLayout());

        JTable customersTable = new JTable();
        JTable addressesTable = new JTable();

        Customer[] customers = guiFrame.getInteractionHandler().getCustomers();
        Address[] addresses = guiFrame.getInteractionHandler().getAddresses();

        TableModel customerModel;
        TableModel addressModel;

        if (customers.length > 0) {
            customerModel = new DefaultTableModel(parseData(customers), parseColumnNames(customers[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            customerModel = new DefaultTableModel(new Object[][] {{"No customers found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        if (addresses.length > 0) {
            addressModel = new DefaultTableModel(parseData(addresses), parseColumnNames(addresses[0])) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }
        else {
            addressModel = new DefaultTableModel(new Object[][] {{"No addresses found"}}, new String[] {"Null"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
    
            };
        }

        customersTable.setModel(customerModel);
        addressesTable.setModel(addressModel);

        customersPanel.add(new JScrollPane(customersTable), BorderLayout.CENTER);
        addressPanel.add(new JScrollPane(addressesTable), BorderLayout.CENTER);

        tabbedPane.addTab("Customers", customersPanel);
        tabbedPane.addTab("Addresses", addressPanel);
        


        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);


    }
}
