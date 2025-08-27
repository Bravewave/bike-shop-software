package src.gui.deprecated;

import java.awt.*;
import javax.swing.*;

import src.gui.GUIFrame;
import src.gui.panels.StaffLoginPanel;

public class Toolbar extends JToolBar {

    // private GUIFrame guiFrame;

    public Toolbar(GUIFrame frame) {
        super.setFloatable(false);

        // guiFrame.changeTitle("Home");

        // JLabel homeLabel = new JLabel("Welcome to Build-a-Bike Ltd.");
        // JLabel homeLabel2 = new JLabel("If you're a customer please login");

        // set width to the screen width

        // setMinimumSize(new Dimension (frame.getScreenSize().width, 0));
        // System.out.printf("%s", frame.getScreenSize());

        JButton staffLogin = new JButton("Staff Login");
        staffLogin.setToolTipText("Staff Login Portal");
        JButton browse = new JButton("Browse");
        browse.setToolTipText("Browse Products");
        JButton customerLogin = new JButton("Customer Login");
        customerLogin.setToolTipText("Customer Login Portal");

        setLayout(new FlowLayout(FlowLayout.LEFT));

        // add(homeLabel, BorderLayout.NORTH);
        // add(homeLabel2, BorderLayout.NORTH);
        add(staffLogin);
        add(browse);
        add(customerLogin);

        staffLogin.addActionListener(e -> frame.navigateTo(new StaffLoginPanel(frame)));
    }

    
}
