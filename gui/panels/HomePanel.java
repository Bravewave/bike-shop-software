package src.gui.panels;

import java.awt.*;
import javax.swing.*;

import src.gui.GUIFrame;
import src.gui.ImagePanel;
import src.gui.deprecated.Toolbar;

public class HomePanel extends JPanel {

    private GUIFrame guiFrame;

    public HomePanel(GUIFrame frame) {

        guiFrame = frame;
        setName("Home");



        // set dimensions to height and width from frame.getScreenSize()

        // Dimension dim = frame.getScreenSize();
        // setPreferredSize(frame.getScreenSize());

        // setSize(frame.getFrameSize());

        // guiFrame.changeTitle("Home");

        JLabel homeLabel = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel homeLabel2 = new JLabel("If you're a customer please login");


        setLayout(new BorderLayout(0, 10));

        // add(homeLabel, BorderLayout.NORTH);
        // add(homeLabel2, BorderLayout.NORTH);
        // add(staffLogin);
        // add(browse);
        // add(customerLogin);

        add(new ImagePanel("bike.jpg"), BorderLayout.CENTER);


        
    }

    
}
