package src.gui.panels;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

import src.gui.GUIFrame;
import src.gui.misc.CustomerDashboardEditOrder;
import src.models.Order;

public class ViewOrderPanel extends JPanel {

    private GUIFrame guiFrame;

    // private void handleLoginButtonAction() throws SQLException {
    //     // System.out.printf("%s", usernameField.getText());
    //     // System.out.printf("%s", passwordField.getPassword());
    //     if (guiFrame.getInteractionHandler().checkCredentials(usernameField.getText(), passwordField.getPassword())) {
    //         JOptionPane.showMessageDialog(guiFrame, "Login successful! Click the user display in the top right to logout again.");
    //         guiFrame.updateMenu();
    //         guiFrame.navigateTo(new StaffDashboardPanel(guiFrame));
    //     } else {
    //         JOptionPane.showMessageDialog(guiFrame, "Invalid username or password");
    //     }
    //     // guiFrame.navigateTo(new StaffLoginSuccessPanel(guiFrame), "Staff Login Success");
    // }

    public ViewOrderPanel(GUIFrame frame) {

        


        guiFrame = frame;
        setName("Customer Login");
        // guiFrame.changeTitle("Staff Login");
        
        JButton submit = new JButton("Submit");
        JTextField orderIDField = new JTextField(20);

        // setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLayout(new BorderLayout());
        // setLayout(new GridBagLayout());

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
        JLabel welcomeText = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel welcomeText2 = new JLabel("Enter your order ID to view your order.");
        welcomeTextPanel.add(welcomeText);
        welcomeTextPanel.add(welcomeText2);
        welcomeTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel loginForm = new JPanel();
        loginForm.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 0;
        loginForm.add(new JLabel("Order ID:"), c);

        c.gridx = 1;
        c.gridy = 0;
        loginForm.add(orderIDField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        loginForm.add(submit, c);
        

        loginForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(loginForm, BorderLayout.CENTER);
        add(welcomeTextPanel, BorderLayout.NORTH);

        submit.addActionListener(e -> {
            try {
                Order order = guiFrame.getInteractionHandler().getOrder(Integer.parseInt(orderIDField.getText()));
                if (order != null) {
                    CustomerDashboardEditOrder.editOrder(order, frame);
                }
                else {
                    JOptionPane.showMessageDialog(guiFrame, "Invalid order ID");
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(guiFrame, "Error fetching order. Please try again later.");
                e1.printStackTrace();
            }
        });
    }
}