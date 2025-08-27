package src.gui.deprecated;

import java.awt.*;
import javax.swing.*;

import src.gui.GUIFrame;
import src.gui.panels.StaffLoginPanel;

public class CustomerLoginSuccessPanel extends JPanel {

    private GUIFrame guiFrame;

    public CustomerLoginSuccessPanel(GUIFrame frame) {

        guiFrame = frame;
        setName("Login Success");
        // guiFrame.changeTitle("Home");

        JLabel homeLabel = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel homeLabel2 = new JLabel("If you're a customer please login");
        JButton staffLogin = new JButton("Staff Login");
        JButton browse = new JButton("Browse");
        JButton customerLogin = new JButton("Customer Login");

        setLayout(new FlowLayout());

        add(homeLabel);
        add(homeLabel2);
        add(staffLogin);
        add(browse);
        add(customerLogin);

        staffLogin.addActionListener(e -> guiFrame.navigateTo(new StaffLoginPanel(frame)));
    }
}
