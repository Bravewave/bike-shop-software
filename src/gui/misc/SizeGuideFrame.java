package src.gui.misc;

import javax.swing.*;
import java.awt.*;

public class SizeGuideFrame extends JFrame {

    public SizeGuideFrame(Component anchor) {
        super("Size Guide");
        setSize(480, 320);
        setLocationRelativeTo(anchor);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel panel = new JPanel();

        JComboBox ageBox = new JComboBox();
        ageBox.addItem("Select Age");
        ageBox.addItem("Child");
        ageBox.addItem("Adult");

        JComboBox usageBox = new JComboBox();
        usageBox.addItem("Select Usage");
        usageBox.addItem("Road");
        usageBox.addItem("Mountain");
        usageBox.addItem("Hybrid");

        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField();

        JLabel inseamLabel = new JLabel("Inseam (cm):");
        JTextField inseamField = new JTextField();

        JButton calculateButton = new JButton("Calculate");
        JButton resetButton = new JButton("Reset");

        JLabel helpLabel = new JLabel("Use our handy calculator to find your recommended bike size!");
        JLabel resultLabel = new JLabel("");

        // calculate the recommended bike size based on the user's height and inseam
        // and display it in the resultLabel

        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);
        
        c.gridx = 0;
        c.gridy = 0;
        panel.add(ageBox, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(usageBox, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(heightLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(heightField, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(inseamLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        panel.add(inseamField, c);
        
        c.gridx = 0;
        c.gridy = 3;
        panel.add(calculateButton, c);

        c.gridx = 1;
        c.gridy = 3;
        panel.add(resetButton, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        panel.add(helpLabel, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        panel.add(resultLabel, c);


        add(panel);

        calculateButton.addActionListener(e -> {
            // calculate the recommended bike frame size based on the user's age, height, use type and inseam
            // and display it in the resultLabel
            int age = ageBox.getSelectedIndex();
            if (age == 0) {
                JOptionPane.showMessageDialog(this, "Please select your age.");
                return;
            }
            if (age == 1) {
                JOptionPane.showMessageDialog(this, "Bike sizes are difficult to calculate for children. We recommend you visit your local bike shop to get a professional fitting.");
                return;
            }
            int usage = usageBox.getSelectedIndex();
            int height = Integer.parseInt(heightField.getText()); // maybe not needed but just in case, idk why i'm spending so much time on this
            int inseam = Integer.parseInt(inseamField.getText());
            if (usage == 0) {
                JOptionPane.showMessageDialog(this, "Please select a usage type.");
                return;
            }
            if (usage == 1) {
                resultLabel.setText("Road Bike Frame Size: " + ((int) Math.floor(inseam * 0.665)) + "cm");
                return;
            }
            if (usage == 2) {
                resultLabel.setText("Mountain Bike Frame Size: " + ((int) Math.floor(inseam * 0.225)) + "in");
                return;
            }
            if (usage == 3) {
                resultLabel.setText("Hybrid Bike Frame Size: " + ((int) Math.floor(inseam * 0.415)) + "cm");
                return;
            }
            
            

        });

        resetButton.addActionListener(e -> {
            ageBox.setSelectedIndex(0);
            usageBox.setSelectedIndex(0);
            heightField.setText("");
            inseamField.setText("");
            resultLabel.setText("");
        });
        
    }

    
}
