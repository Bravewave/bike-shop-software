package src.gui.panels;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import src.gui.GUIFrame;
import src.gui.misc.CheckoutFrame;
import src.models.*;

public class CheckoutPanel extends JPanel {

    private GUIFrame guiFrame;

    private double BESPOKE_FEE = 10.00;
    private double totalCost = 0.00;

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

    public CheckoutPanel(GUIFrame frame) {

        guiFrame = frame;
        setName("Checkout");
        // guiFrame.changeTitle("Customer Login");
        JPanel checkoutForm = new JPanel();

        JPanel orderSummary = new JPanel();
        orderSummary.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        orderSummary.setLayout(new BoxLayout(orderSummary, BoxLayout.Y_AXIS));
        // orderSummary.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel orderSummaryText = new JLabel("<html><h1 style='text-align:left;'>Order Summary</h1></html>");
        JLabel orderSummaryText2 = new JLabel("<html><h2 style='text-align:right;'>" + guiFrame.getInteractionHandler().getBasket().size() + " item(s)</h2></html>");
        
        JPanel orderSummaryHeader = new JPanel();
        orderSummaryHeader.setLayout(new BorderLayout());

        orderSummaryHeader.add(orderSummaryText , BorderLayout.WEST);
        orderSummaryHeader.add(orderSummaryText2 , BorderLayout.EAST);

        

        orderSummary.add(orderSummaryHeader);
        

        JLabel basketEmpty = new JLabel("Your basket is empty");

        if (guiFrame.getInteractionHandler().getBasket().isEmpty()) {
            orderSummary.add(basketEmpty);
        } else {
            for (Bike bike : guiFrame.getInteractionHandler().getBasket()) {
                totalCost += bike.calculateTotalCost();
                if (bike.isCustom()) {
                    totalCost += BESPOKE_FEE;
                }
                System.out.println("Bike: " + bike.getName());
                JLabel bikeNameLabel = new JLabel( "<html><h2>-= "+ bike.getName()+" =-</h2></html>" );
                orderSummary.add(bikeNameLabel);
                // align bikeNameLabel to left of panel
                bikeNameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT); // for some reason this works better than LEFT_ALIGNMENT
                    

                orderSummary.add(constructTable(bike));
            }
            // add total cost and format to 2dp with padding and £ sign
            JLabel totalCostLabel = new JLabel(String.format("<html><h2 style='text-align:right;'>Total Cost: £%1$-6.2f</h2></html>", totalCost));
            JPanel orderSummaryFooter = new JPanel();
            orderSummaryFooter.setLayout(new BorderLayout());
            orderSummaryFooter.add(totalCostLabel, BorderLayout.EAST);
            orderSummary.add(orderSummaryFooter);
        }




        JLabel checkoutText = new JLabel(
            "<html>" +
            "<style type=\"text/css\">" +
            "html {text-align: center;}" +
            "div {padding: 10px; margin: 10px; display: inline-block; background-color: white;}" +
            "h1 {font-size: 1.6em; padding: 0; margin: 0;}" +
            "p {font-size: 1.2em;}" +

            "</style>" +
                "<div>" +
                "<h1 class='header'>Checkout</h1>" +
                "<p>Review your order below and submit it when you're ready!</p>" +
                "</div>" +
            "</html>"
                );

        JButton confirmOrderButton = new JButton("Add Details"); // TODO: add listeners n shit
        // confirmOrderButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         confirmOrderButton.setEnabled(false);
        //         confirmOrderButton.setText("Please Wait...");
        //         if (guiFrame.getInteractionHandler().createOrder() == true) {
        //             guiFrame.updateMenu();
        //             confirmOrderButton.setText("Order Confirmed!");
        //             confirmOrderButton.setEnabled(false);
        //             JOptionPane.showMessageDialog(guiFrame, "Order confirmed! An employee will be in touch shortly to take payment.");
        //         }
        //         else {
        //             // order failed
        //             confirmOrderButton.setText("Order Failed!");
        //             confirmOrderButton.setEnabled(true);
        //             JOptionPane.showMessageDialog(guiFrame, "Order failed! The items may be out of stock, or there was some other error. Try adding the items to your cart again.");
        //         }
        //     }
        // });

        if (guiFrame.getInteractionHandler().getCustomer() == null) {
            confirmOrderButton.addActionListener(e -> {
                // open the checkoutframe and pass the guiframe to it
                CheckoutFrame checkoutFrame = new CheckoutFrame(guiFrame);
            });
        }


        JScrollPane scrollPane = new JScrollPane(orderSummary);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.setPreferredSize(new Dimension(560, 280));

        setLayout(new BorderLayout());
        add(checkoutText, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(confirmOrderButton, BorderLayout.SOUTH);
    }
}