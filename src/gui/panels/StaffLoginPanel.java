package src.gui.panels;

import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

import src.gui.GUIFrame;

public class StaffLoginPanel extends JPanel {

    private GUIFrame guiFrame;

    private JTextField usernameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    private void handleLoginButtonAction() throws SQLException {
        // System.out.printf("%s", usernameField.getText());
        // System.out.printf("%s", passwordField.getPassword());
        if (guiFrame.getInteractionHandler().checkCredentials(usernameField.getText(), passwordField.getPassword())) {
            JOptionPane.showMessageDialog(guiFrame, "Login successful! Click the user display in the top right to logout again.");
            guiFrame.updateMenu();
            guiFrame.navigateTo(new StaffDashboardPanel(guiFrame));
        } else {
            JOptionPane.showMessageDialog(guiFrame, "Invalid username or password");
        }
        // guiFrame.navigateTo(new StaffLoginSuccessPanel(guiFrame), "Staff Login Success");
    }

    public StaffLoginPanel(GUIFrame frame) {

        


        guiFrame = frame;
        setName("Staff Login");
        // guiFrame.changeTitle("Staff Login");
        
        JButton login = new JButton("Login");

        // setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setLayout(new BorderLayout());
        // setLayout(new GridBagLayout());

        JPanel welcomeTextPanel = new JPanel();
        welcomeTextPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
        JLabel welcomeText = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel welcomeText2 = new JLabel("Login using your staff credentials to access the staff dashboard.");
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
        loginForm.add(new JLabel("Username:"), c);

        c.gridx = 1;
        c.gridy = 0;
        loginForm.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        loginForm.add(new JLabel("Password:"), c);

        c.gridx = 1;
        c.gridy = 1;
        loginForm.add(passwordField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        loginForm.add(login, c);

        


        // GridBagConstraints c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.insets = new Insets(10, 10, 10, 10);
        // c.gridx = 0;
        // c.gridy = 0;
        // loginForm.add(usernameField, c);

        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.insets = new Insets(10, 10, 10, 10);
        // c.gridx = 0;
        // c.gridy = 1;
        // loginForm.add(passwordField, c);

        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.insets = new Insets(10, 10, 10, 10);
        // c.gridx = 0;
        // c.gridy = 2;
        // loginForm.add(login);

        add(loginForm, BorderLayout.CENTER);
        add(welcomeTextPanel, BorderLayout.NORTH);

        // add(staffLogin);
        // add(usernameField);
        // add(passwordField);
        // add(login);

        login.addActionListener(e -> {
            try {
                handleLoginButtonAction();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}
