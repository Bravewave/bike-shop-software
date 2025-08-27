package src.gui.panels;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

import src.gui.GUIFrame;

public class CustomerLoginPanel extends JPanel {

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

    public CustomerLoginPanel(GUIFrame frame) {

        


        guiFrame = frame;
        setName("Customer Login");
        // guiFrame.changeTitle("Staff Login");
        
        JButton login = new JButton("Login");
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField houseNameField = new JTextField(20);
        JTextField postcodeField = new JTextField(20);

        // setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLayout(new BorderLayout());
        // setLayout(new GridBagLayout());

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
        JLabel welcomeText = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel welcomeText2 = new JLabel("Login using your personal details to view and change your orders.");
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
        loginForm.add(new JLabel("First Name:"), c);

        c.gridx = 1;
        c.gridy = 0;
        loginForm.add(firstNameField, c);

        c.gridx = 0;
        c.gridy = 1;
        loginForm.add(new JLabel("Last Name:"), c);

        c.gridx = 1;
        c.gridy = 1;
        loginForm.add(lastNameField, c);

        c.gridx = 0;
        c.gridy = 2;
        loginForm.add(new JLabel("House Name:"), c);

        c.gridx = 1;
        c.gridy = 2;
        loginForm.add(houseNameField, c);

        c.gridx = 0;
        c.gridy = 3;
        loginForm.add(new JLabel("Postcode:"), c);

        c.gridx = 1;
        c.gridy = 3;
        loginForm.add(postcodeField, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        loginForm.add(login, c);

        loginForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(loginForm, BorderLayout.CENTER);
        add(welcomeTextPanel, BorderLayout.NORTH);

        login.addActionListener(e -> {
            try {
                if (guiFrame.getInteractionHandler().checkCustomerCredentials(firstNameField.getText(), lastNameField.getText(), houseNameField.getText(), postcodeField.getText())) {
                    JOptionPane.showMessageDialog(guiFrame, "Login successful! Click the user display in the top right to logout again.");
                    guiFrame.updateMenu();
                    guiFrame.navigateTo(new CustomerDashboardPanel(guiFrame));
                } else {
                    JOptionPane.showMessageDialog(guiFrame, "Invalid username or password");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
