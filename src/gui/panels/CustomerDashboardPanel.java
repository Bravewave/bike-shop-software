package src.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.Utilities;
import src.gui.GUIFrame;
import src.gui.misc.CustomerDashboardEditOrder;
import src.gui.misc.StaffDashboardAddItem;
import src.gui.misc.StaffDashboardEditItem;
import src.gui.misc.StaffDashboardEditOrder;
import src.models.*;

public class CustomerDashboardPanel extends JPanel {

    private GUIFrame guiFrame;

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

    public CustomerDashboardPanel(GUIFrame frame) {

        guiFrame = frame;
        setName("Customer Dashboard");

        Customer customer = guiFrame.getInteractionHandler().getCustomer();
        
        // has all the staff controls (add/remove parts, process orders, etc)
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BorderLayout());

        JTable ordersTable = new JTable();

        Order[] orders = guiFrame.getInteractionHandler().getOrders(guiFrame.getInteractionHandler().getCustomer());

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
                    CustomerDashboardEditOrder.editOrder(orders[row], guiFrame);
                }
            }
        });

        ordersPanel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        tabbedPane.addTab("Orders", ordersPanel);



        JPanel detailsPanel = new JPanel();
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(customer.getForename());

        JLabel surnameLabel = new JLabel("Surname: ");
        JTextField surnameField = new JTextField(customer.getSurname());

        JLabel houseNameLabel = new JLabel("House Name: ");
        JTextField houseNameField = new JTextField(customer.getAddress().getHouseName());

        JLabel streetLabel = new JLabel("Street: ");
        JTextField streetField = new JTextField(customer.getAddress().getRoadName());

        JLabel cityLabel = new JLabel("City: ");
        JTextField cityField = new JTextField(customer.getAddress().getCityName());

        JLabel postcodeLabel = new JLabel("Postcode: ");
        JTextField postcodeField = new JTextField(customer.getAddress().getPostcode());

        JLabel separatorLabel = new JLabel();
        JButton saveOrderButton = new JButton("Save Profile");
        saveOrderButton.addActionListener(e -> {
            try {
                Utilities.amendCustomerDetails(Integer.valueOf(customer.getCustomerID()), nameField.getText(), surnameField.getText(), houseNameField.getText(), streetField.getText(), cityField.getText(), postcodeField.getText(), null);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        

        detailsPanel.setLayout(new GridLayout(0, 2));
        detailsPanel.add(nameLabel);
        detailsPanel.add(nameField);
        detailsPanel.add(surnameLabel);
        detailsPanel.add(surnameField);
        detailsPanel.add(houseNameLabel);
        detailsPanel.add(houseNameField);
        detailsPanel.add(streetLabel);
        detailsPanel.add(streetField);
        detailsPanel.add(cityLabel);
        detailsPanel.add(cityField);
        detailsPanel.add(postcodeLabel);
        detailsPanel.add(postcodeField);
        detailsPanel.add(separatorLabel);
        detailsPanel.add(saveOrderButton);

        tabbedPane.addTab("Details", detailsPanel);

        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
    
}
