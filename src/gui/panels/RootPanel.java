package src.gui.panels;

import java.awt.*;
import javax.swing.*;

import src.gui.GUIFrame;
import src.gui.ImagePanel;
import src.gui.deprecated.Toolbar;

public class RootPanel extends JPanel {

    private GUIFrame guiFrame;

    private Toolbar toolbar;

    public RootPanel(GUIFrame frame) {

        toolbar = new Toolbar(frame);
        // set dimensions to height and width from frame.getScreenSize()

        // Dimension dim = frame.getScreenSize();
        // setPreferredSize(frame.getScreenSize());

        // setSize(frame.getFrameSize());

        setLayout(new BorderLayout(0, 10));

        // add(homeLabel, BorderLayout.NORTH);
        // add(homeLabel2, BorderLayout.NORTH);
        // add(staffLogin);
        // add(browse);
        // add(customerLogin);
        

        add(new ImagePanel("bike.jpg"), BorderLayout.CENTER);

    }

    public void navigateTo(Component component, String title) {
        removeAll();
        // add(toolbar, BorderLayout.NORTH);
        add(component, BorderLayout.CENTER);
        setVisible(true);
        revalidate();
        repaint();
    }

    
}
