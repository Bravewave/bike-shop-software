package src.gui;

import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePanel extends JPanel {
    // load an image from the resources folder and display it

    private BufferedImage image;

    public ImagePanel(String path) {
        // set the size of the panel
        
        try {
            // load logo.png from the assets folder
            image = ImageIO.read(getClass().getResource("/src/assets/"+path));
        } catch (IOException e) {
            image = null;
            e.printStackTrace();
        }
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(image).getImage(), 0, 0, null);
    }
}
