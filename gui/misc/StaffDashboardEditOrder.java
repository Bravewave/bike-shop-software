package src.gui.misc;

import javax.swing.*;

import src.Utilities;
import src.gui.GUIFrame;
import src.models.*;

import java.awt.*;
import java.sql.SQLException;

public class StaffDashboardEditOrder {
    private double BESPOKE_FEE = 10.00;
    
    private JPanel constructTable(Bike bike) {
        String[] columnNames = {"Part", "Name", "Brand", "Serial #", "Unit Cost", "Total Cost"};
        Wheels wheels = bike.getWheels();
        FrameSet frameSet = bike.getFrameSet();
        Handlebars handlebars = bike.getHandlebars();
        double custom_fee = BESPOKE_FEE;
        if (!bike.isCustom()) {
            custom_fee = 0.00;
        }
        Object[][] data = {
            {
                "Frame",
                frameSet.getName(),
                frameSet.getBrand(),
                frameSet.getSerialNumber(),
                String.format("£%1$-6.2f (1)", frameSet.getUnitCost()),
                String.format("£%1$-6.2f", frameSet.getUnitCost())
            },
            {
                "Wheels",
                wheels.getName(),
                wheels.getBrand(),
                wheels.getSerialNumber(),
                String.format("£%1$-6.2f (2)", wheels.getUnitCost() / 2),
                String.format("£%1$-6.2f", wheels.getUnitCost())
            },
            {
                "Handlebars",
                handlebars.getName(),
                handlebars.getBrand(),
                handlebars.getSerialNumber(),
                String.format("£%1$-6.2f (1)", handlebars.getUnitCost()),
                String.format("£%1$-6.2f", handlebars.getUnitCost())
            },
            {
                "Custom Fee",
                "",
                "",
                "",
                String.format("£%1$-6.2f (1)", custom_fee),
                String.format("£%1$-6.2f", custom_fee)
            },
            {
                "Subtotal",
                "",
                "",
                "",
                "",
                String.format("£%1$-6.2f", bike.calculateTotalCost() + custom_fee)
            },
            {
                "",
                "",
                "",
                "",
                "",
                ""
            }
        };
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(600, 70));
        table.setFillsViewportHeight(true);
        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        return tablePanel;
    }
    public static JFrame editOrder(Order order, GUIFrame guiFrame) {

        int currentStaffID = guiFrame.getInteractionHandler().getStaff().getStaffID();
        Staff staff;

        try {
            staff = Utilities.getStaffFromID(order.getStaffID(), null);
        }
        catch (Exception e) {
            staff = null;
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Edit Order");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel orderIDLabel = new JLabel("Order ID:");
        JTextField orderIDField = new JTextField(String.valueOf(order.getOrderID()));
        orderIDField.setEditable(false);

        JLabel customerIDLabel = new JLabel("Customer ID:");
        JTextField customerIDField = new JTextField(String.valueOf(order.getCustomer().getCustomerID()));
        customerIDField.setEditable(false);

        JLabel orderDateLabel = new JLabel("Order Date:");
        JTextField orderDateField = new JTextField(order.getOrderDate().toString());
        orderDateField.setEditable(false);

        JLabel orderStatusLabel = new JLabel("Order Status:");
        JTextField orderStatusField = new JTextField(order.getStatus().toString());
        orderStatusField.setEditable(false);

        JLabel staffIDLabel = new JLabel("Staff Member:");
        JTextField staffIDField;
        try {
            staffIDField = new JTextField(String.valueOf(staff.getFirstName() + " " + staff.getLastName()));
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            staffIDField = new JTextField(String.valueOf(order.getStaffID()));
            e1.printStackTrace();
        }
        staffIDField.setEditable(false);

        JLabel separatorLabel = new JLabel();
        JButton advanceOrderButton = new JButton("Advance Order");
        advanceOrderButton.addActionListener(e -> {
            try {
                Order newOrder = Utilities.progressOrderStatus(order, null, currentStaffID);
                orderStatusField.setText(newOrder.getStatus().toString());
                JOptionPane.showMessageDialog(frame, "Order status and stock updated successfully. Reload the app to see changes.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
            orderStatusField.setText(order.getStatus().toString());
        });

        panel.setLayout(new GridLayout(0, 2));
        panel.add(orderIDLabel);
        panel.add(orderIDField);
        panel.add(customerIDLabel);
        panel.add(customerIDField);
        panel.add(orderDateLabel);
        panel.add(orderDateField);
        panel.add(orderStatusLabel);
        panel.add(orderStatusField);
        panel.add(staffIDLabel);
        panel.add(staffIDField);
        panel.add(separatorLabel);
        panel.add(advanceOrderButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new StaffDashboardEditOrder().constructTable(order.getBike()), BorderLayout.CENTER);
        frame.setVisible(true);
        return frame;

    }
}
