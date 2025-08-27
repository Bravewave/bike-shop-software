package src.gui.misc;

import javax.swing.*;

import src.Utilities;
import src.gui.GUIFrame;
import src.models.Address;
import src.models.Customer;

import java.awt.*;

import java.awt.event.*;
import java.util.Arrays;

import javax.swing.event.*;

public class CheckoutFrame extends JFrame {

    private boolean orderComplete = false;

    public CheckoutFrame(GUIFrame guiFrame) {
        super("Enter Details");
        setSize(320, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

        // add form for customer details
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        // c.weightx=1.;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        JLabel headerLabel = new JLabel("<html><h1 style='text-align:center;'>Enter Details</h1></html>");
        JLabel helpLabel = new JLabel("You'll be able to use your address to view your orders.");

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(20);

        JLabel separator = new JLabel();

        JLabel houseNameLabel = new JLabel("House Name/Number:");
        JTextField houseNameField = new JTextField(20);

        JLabel roadNameLabel = new JLabel("Road Name:");
        JTextField roadNameField = new JTextField(20);

        JLabel cityLabel = new JLabel("City:");
        JTextField cityField = new JTextField(20);

        JLabel postcodeLabel = new JLabel("Postcode:");
        JTextField postcodeField = new JTextField(20);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(headerLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        panel.add(helpLabel, c);

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 2;
        panel.add(firstNameLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(firstNameField, c);

        c.gridx = 0;
        c.gridy = 3;
        panel.add(lastNameLabel, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(lastNameField, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(separator, c);

        c.gridx = 0;
        c.gridy = 5;
        panel.add(houseNameLabel, c);

        c.gridx = 1;
        c.gridy = 5;
        panel.add(houseNameField, c);

        c.gridx = 0;
        c.gridy = 6;
        panel.add(roadNameLabel, c);

        c.gridx = 1;
        c.gridy = 6;
        panel.add(roadNameField, c);

        c.gridx = 0;
        c.gridy = 7;
        panel.add(cityLabel, c);

        c.gridx = 1;
        c.gridy = 7;
        panel.add(cityField, c);

        c.gridx = 0;
        c.gridy = 8;
        panel.add(postcodeLabel, c);

        c.gridx = 1;
        c.gridy = 8;
        panel.add(postcodeField, c);

        // add a 7-character limit to the postcode field
        postcodeField.getDocument().addDocumentListener(new DocumentListener() {
            private void trimText() {
                Runnable trimmer = new Runnable() { // https://stackoverflow.com/questions/15206586/getting-attempt-to-mutate-notification-exception
                    public void run() {
                        if (postcodeField.getText().length() > 7) {
                            postcodeField.setText(postcodeField.getText().substring(0, 7));
                        }
                    }
                };
                SwingUtilities.invokeLater(trimmer);
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                trimText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                trimText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                trimText();
            }
        });


        add(panel, BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm");
        add(confirmButton, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {
            Customer customer = guiFrame.getInteractionHandler().getCustomer();
            if (customer != null) {
                // alert the user they are already logged in
                JOptionPane.showMessageDialog(this, "You are already logged in as " + customer.getForename() + " " + customer.getSurname() + ". Please log out first.");
            }
            else {
                // create a new customer object
                Address address = new Address(
                    Utilities.sanitize(houseNameField.getText()), 
                    Utilities.sanitize(roadNameField.getText()), 
                    Utilities.sanitize(cityField.getText()), 
                    postcodeField.getText().replaceAll("\\s", "") // not sure if you can do an sql injection with a 7 character postcode lmao
                    );
                customer = new Customer(
                    Utilities.sanitize(firstNameField.getText()), 
                    Utilities.sanitize(lastNameField.getText()), 
                    address
                    );
                guiFrame.getInteractionHandler().setCustomer(customer);
                confirmButton.setText("Processing..."); // doesn't actually do anything because of multithreading (?)
                if (guiFrame.getInteractionHandler().createOrder()) {
                    JOptionPane.showMessageDialog(this, "Order confirmed! An employee will be in touch shortly to take payment.");
                    orderComplete = true;
                    guiFrame.updateMenu();
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Order failed! The items may be out of stock, or there was some other error. Try adding the items to your cart again.");
                }
            }
        });
        pack();
        setVisible(true);
    }

    public boolean isOrderComplete() {
        return orderComplete;
    }

    
}
