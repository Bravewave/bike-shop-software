package src.gui.panels;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.*;

import src.Utilities;
import src.gui.GUIFrame;
import src.gui.misc.SizeGuideFrame;
import src.models.*;
import src.models.Wheels.BrakeType;

public class ConfigurePanel extends JPanel  implements ActionListener {
    // TODO: all of this is a fucking mess and needs to be cleaned up
    // TODO: dynamically read parts from database
    // TODO: make this work with objects

    private GUIFrame guiFrame;

    private static final int THUMBNAIL_SIZE = 100;

    private int bikeFrame;
    private int bikeHandlebar;
    private int bikeTyre;
    private int bikeBrakes;
    private int bikeFrameSize;
    private int bikeWheelSize;
    private double bikePrice = 0.0;
    private String bikeSerial;

    private FrameSet[] frameSets;
    private Wheels[] wheels;
    private Handlebars[] handlebars;

    private FrameSet selectedFrameSet;
    private Wheels selectedWheels;
    private Handlebars selectedHandlebars;

    private JLabel costLabel = new JLabel("Cost will be calculated once all parts are selected");

    // create a default placeholder image

    private ImageIcon parseImage(String path) {
        return new ImageIcon(new ImageIcon("src/assets/parts/" + path).getImage().getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_DEFAULT));
    }

    private ImageIcon randomImage(String path) {
        return new ImageIcon(new ImageIcon("src/assets/parts/" + path + (int) ((Math.random() * 3) + 1) + ".jpg").getImage().getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_DEFAULT));
    }

    private ImageIcon DEFAULT_IMAGE = parseImage("placeholder.jpg");

    // deprecated
    private String serializeBike() {
        // should allow for 256 parts for each category
        
        // convert each value to hex
        String hexFrame = Integer.toHexString(bikeFrame);
        String hexHandlebar = Integer.toHexString(bikeHandlebar);
        String hexTyre = Integer.toHexString(bikeTyre);
        String hexBrakes = Integer.toHexString(bikeBrakes);
        String hexFrameSize = Integer.toHexString(bikeFrameSize);
        String hexWheelSize = Integer.toHexString(bikeWheelSize);

        // pad each hex value with 0s to ensure 2 characters
        hexFrame = String.format("%02x", bikeFrame);
        hexHandlebar = String.format("%02x", bikeHandlebar);
        hexTyre = String.format("%02x", bikeTyre);
        hexBrakes = String.format("%02x", bikeBrakes);
        hexFrameSize = String.format("%02x", bikeFrameSize);
        hexWheelSize = String.format("%02x", bikeWheelSize);

        // concatenate all hex values
        bikeSerial = hexFrame + hexHandlebar + hexTyre + hexBrakes + hexFrameSize + hexWheelSize;
        return bikeSerial;
    }

    private Boolean validateConfig() {
        // check if all parts are selected
        if (bikeFrame == 0 || bikeHandlebar == 0 || bikeTyre == 0) {
            return false;
        }
        return true;
    }

    private void calculatePrice() {
        // if any of the bike parts are not selected set the price to 0
        if (!validateConfig())
            {
            bikePrice = 0.0;
            costLabel.setText("Cost will be calculated once all parts are selected");
        } else {
            // do some 'math' to calculate the price
            // bikePrice = Math.random() * 1000;

            // // round to 2 decimal places
            // bikePrice = Math.round(bikePrice * 100.0) / 100.0;

            bikePrice = selectedFrameSet.getUnitCost() + selectedWheels.getUnitCost() + selectedHandlebars.getUnitCost();
            costLabel.setText("Cost: £" + bikePrice + " ( + £10 assembly fee)");
        }
        // return bikePrice;
    }

    public ConfigurePanel(GUIFrame frame) {

        guiFrame = frame;

        frameSets = frame.getInteractionHandler().getFrames();
        wheels = frame.getInteractionHandler().getWheels();
        handlebars = frame.getInteractionHandler().getHandlebars();

        System.out.println("*******************************");
        System.out.println("FrameSets: " + Arrays.toString(frameSets));
        System.out.println("Wheels: " + wheels.length);
        System.out.println("Handlebars: " + handlebars.length);
        System.out.println("*******************************");

        setName("Configure");
        // guiFrame.changeTitle("Browse Products");

        JPanel introPanel = new JPanel();
        JLabel introLabel = new JLabel("Welcome to Build-a-Bike Ltd.");
        JLabel introLabel2 = new JLabel("Here you can customise your PERFECT bike using our configurator, using our in-stock parts!");
        JLabel introLabel3 = new JLabel("TIP: Hover over selected part images to see a description.");
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        introPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        introPanel.add(introLabel);
        introPanel.add(introLabel2);
        introPanel.add(introLabel3);
        introPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));


        
        // display a form with 4 dropdown boxes
        // 1. Bike Frame
        // 2. Bike Tyre
        // 3. Bike Handlebar
        // 4. Bike Brakes

        JPanel formPanelL = new JPanel();
        JPanel formPanelR = new JPanel();
        JPanel partDisplayPanel = new JPanel();
        
        formPanelL.setLayout(new BoxLayout(formPanelL, BoxLayout.Y_AXIS));
        formPanelR.setLayout(new BoxLayout(formPanelR, BoxLayout.Y_AXIS));
        partDisplayPanel.setLayout(new GridLayout(2,2));

        // TODO: dynamically load things based on stock
        JLabel frameLabel = new JLabel("Frame");
        JComboBox<String> frameBox = new JComboBox<String>();
        frameBox.addItem("Select Frame");
        for (FrameSet frameSet : frameSets) {
            if (frameSet.getStockCount() > 0) {
                frameBox.addItem(frameSet.getName());
            }
        }
        frameBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel tyreLabel = new JLabel("Tyre");
        JComboBox<String> tyreBox = new JComboBox<String>();
        tyreBox.addItem("Select Wheel");
        for (Wheels wheel : wheels) {
            if (wheel.getStockCount() > 0) {
                tyreBox.addItem(wheel.getName());
            }
        }
        tyreBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel handlebarLabel = new JLabel("Handlebar");
        JComboBox<String> handlebarBox = new JComboBox<String>();
        handlebarBox.addItem("Select Handlebar");
        for (Handlebars handlebar : handlebars) {
            if (handlebar.getStockCount() > 0) {
                handlebarBox.addItem(handlebar.getName());
            }
        }
        handlebarBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel brakeLabel = new JLabel("Brakes");
        JComboBox<String> brakeBox = new JComboBox<String>();
        brakeBox.addItem("Select Brakes");
        brakeBox.addItem("Rim Brakes");
        brakeBox.addItem("Disc Brakes");
        brakeBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        brakeBox.setEnabled(false);

        // formPanelL.add(frameLabel);
        formPanelL.add(frameBox);
        // formPanelL.add(tyreLabel);
        formPanelL.add(tyreBox);
        // formPanelR.add(handlebarLabel);
        formPanelR.add(handlebarBox);
        // formPanelR.add(brakeLabel);
        formPanelR.add(brakeBox);

        JPanel frameDisplayPanel = new JPanel();
        JPanel handlebarDisplayPanel = new JPanel();
        JPanel tyreDisplayPanel = new JPanel();
        JPanel brakeDisplayPanel = new JPanel();

        frameDisplayPanel.setLayout(new BoxLayout(frameDisplayPanel, BoxLayout.Y_AXIS));
        frameDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        handlebarDisplayPanel.setLayout(new BoxLayout(handlebarDisplayPanel, BoxLayout.Y_AXIS));
        handlebarDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tyreDisplayPanel.setLayout(new BoxLayout(tyreDisplayPanel, BoxLayout.Y_AXIS));
        tyreDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        brakeDisplayPanel.setLayout(new BoxLayout(brakeDisplayPanel, BoxLayout.Y_AXIS));
        brakeDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel frameDisplayLabel = new JLabel("Frame");
        JLabel handlebarDisplayLabel = new JLabel("Handlebar");
        JLabel tyreDisplayLabel = new JLabel("Tyre");
        JLabel brakeDisplayLabel = new JLabel("Brake");

        JLabel frameDisplayImage = new JLabel();
        JLabel handlebarDisplayImage = new JLabel();
        JLabel tyreDisplayImage = new JLabel();
        JLabel brakeDisplayImage = new JLabel();

        // TODO: dynamically load images based on selection
        // TODO: dynamically size images
        
        
        frameDisplayImage.setIcon(DEFAULT_IMAGE);
        frameDisplayImage.setToolTipText("No frame selected");
        handlebarDisplayImage.setIcon(DEFAULT_IMAGE);
        handlebarDisplayImage.setToolTipText("No handlebar selected");
        tyreDisplayImage.setIcon(DEFAULT_IMAGE);
        tyreDisplayImage.setToolTipText("No tyre selected");
        brakeDisplayImage.setIcon(DEFAULT_IMAGE);
        brakeDisplayImage.setToolTipText("No brake selected");


        // frameDisplayPanel.add(frameDisplayLabel);
        frameDisplayPanel.add(frameDisplayImage);
        // handlebarDisplayPanel.add(handlebarDisplayLabel);
        handlebarDisplayPanel.add(handlebarDisplayImage);
        // tyreDisplayPanel.add(tyreDisplayLabel);
        tyreDisplayPanel.add(tyreDisplayImage);
        // brakeDisplayPanel.add(brakeDisplayLabel);
        brakeDisplayPanel.add(brakeDisplayImage);

        partDisplayPanel.add(frameDisplayPanel);
        partDisplayPanel.add(handlebarDisplayPanel);
        partDisplayPanel.add(tyreDisplayPanel);
        partDisplayPanel.add(brakeDisplayPanel);



        // display a button to add the bike to the basket
        // JButton addToBasket = new JButton("Add to Basket");

        JPanel formPanelB = new JPanel();

        // add a Jslider for frame size
        // add a Jslider for wheel size
        // add a label for cost

        JSlider frameSizeSlider = new JSlider(JSlider.HORIZONTAL, 20, 100, 20);
        // frameSizeSlider.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JSlider wheelSizeSlider = new JSlider(JSlider.HORIZONTAL, 20, 40, 20);
        // wheelSizeSlider.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // JLabel costLabel = new JLabel("Cost will be calculated once all parts are selected");
        frameSizeSlider.setEnabled(false);
        wheelSizeSlider.setEnabled(false);

        JLabel frameSizeLabel = new JLabel("Frame Size");
        JLabel frameSizeNumber = new JLabel(frameSizeSlider.getValue() + "cm");

        JLabel wheelSizeLabel = new JLabel("Wheel Size");
        JLabel wheelSizeNumber = new JLabel(wheelSizeSlider.getValue() + "inches");

        JButton addToBasket = new JButton("Add to Basket");
        JButton openSizeGuide = new JButton("Open Size Guide");


        formPanelB.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        formPanelB.add(frameSizeLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        formPanelB.add(frameSizeSlider, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        formPanelB.add(frameSizeNumber, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        formPanelB.add(wheelSizeLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        formPanelB.add(wheelSizeSlider, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        formPanelB.add(wheelSizeNumber, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        formPanelB.add(costLabel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        formPanelB.add(addToBasket, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        formPanelB.add(openSizeGuide, c);

        // add a change listener to the sliders to repaint the labels
        frameSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bikeFrameSize = frameSizeSlider.getValue();
                frameSizeNumber.setText(frameSizeSlider.getValue() + "cm");
                calculatePrice();
            }
        });

        wheelSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bikeWheelSize = wheelSizeSlider.getValue();
                wheelSizeNumber.setText(wheelSizeSlider.getValue() + "in");
                calculatePrice();
            }
        });

        // add change listeners to the combo boxes to update the fields
        frameBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bikeFrame = frameBox.getSelectedIndex();
                // TODO: update this bit if we ever have over 10 parts
                // if index is 0, then set to DEFAULT_IMAGE image
                if (bikeFrame == 0) {
                    frameDisplayImage.setIcon(DEFAULT_IMAGE);
                    frameDisplayImage.setToolTipText("No frame selected");
                    frameSizeSlider.setValue(20);
                    selectedFrameSet = null;
                } else {
                    frameDisplayImage.setIcon(randomImage("frame-0"));
                    // TODO: read the description from the database
                    selectedFrameSet = frameSets[bikeFrame - 1];
                    frameDisplayImage.setToolTipText(selectedFrameSet.getName());
                    frameSizeSlider.setValue(selectedFrameSet.getSize());
                }
                calculatePrice();
            }
        });

        handlebarBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bikeHandlebar = handlebarBox.getSelectedIndex();
                if (bikeHandlebar == 0) {
                    handlebarDisplayImage.setIcon(DEFAULT_IMAGE);
                    handlebarDisplayImage.setToolTipText("No handlebar selected");
                    selectedHandlebars = null;
                } else {
                    handlebarDisplayImage.setIcon(randomImage("handles-0"));
                    selectedHandlebars = handlebars[bikeHandlebar - 1];
                    handlebarDisplayImage.setToolTipText(selectedHandlebars.getName());
                }
                calculatePrice();
            }
        });

        tyreBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bikeTyre = tyreBox.getSelectedIndex();
                if (bikeTyre == 0) {
                    tyreDisplayImage.setIcon(DEFAULT_IMAGE);
                    tyreDisplayImage.setToolTipText("No tyre selected");
                    brakeBox.setSelectedIndex(0);
                    wheelSizeSlider.setValue(20);
                    selectedWheels = null;
                } else {
                    tyreDisplayImage.setIcon(randomImage("tyre-0"));
                    selectedWheels = wheels[bikeTyre - 1];
                    tyreDisplayImage.setToolTipText(selectedWheels.getName());
                    if (selectedWheels.getBrakeType() == BrakeType.DISC) {
                        brakeBox.setSelectedIndex(2);
                        brakeDisplayImage.setIcon(parseImage("brakes-02.jpg"));
                    } else {
                        brakeBox.setSelectedIndex(1);
                        brakeDisplayImage.setIcon(parseImage("brakes-01.jpg"));
                    }
                    wheelSizeSlider.setValue(selectedWheels.getDiameter());
                }
                calculatePrice();
            }
        });

        // brakeBox.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         bikeBrakes = brakeBox.getSelectedIndex();
        //         if (bikeBrakes == 0) {
        //             brakeDisplayImage.setIcon(DEFAULT_IMAGE);
        //             brakeDisplayImage.setToolTipText("No brakes selected");
        //         } else {
        //             brakeDisplayImage.setIcon(randomImage("brakes-0"));
        //             brakeDisplayImage.setToolTipText("cock");
        //         }
        //         calculatePrice();
        //     }
        // });

        


        // add action listener to all form components
        // maybe not needed
        addToBasket.addActionListener(this);
        openSizeGuide.addActionListener(this);
        frameBox.addActionListener(this);
        handlebarBox.addActionListener(this);
        tyreBox.addActionListener(this);
        brakeBox.addActionListener(this);

        setLayout(new BorderLayout());

        add(introPanel, BorderLayout.NORTH);
        add(formPanelL, BorderLayout.WEST);
        add(formPanelR, BorderLayout.EAST);
        add(formPanelB, BorderLayout.SOUTH);
        add(partDisplayPanel, BorderLayout.CENTER);


        // open a new frame with the size guide
        openSizeGuide.addActionListener(e -> {
            new SizeGuideFrame(partDisplayPanel);
        });

        addToBasket.addActionListener(e -> {
            if (validateConfig()) {
                guiFrame.getInteractionHandler().handleConfiguration(new Bike(
                    Utilities.generateRandomSerialNo(),
                    "Custom Built",
                    "Bike",
                    "My Bicycle",
                    selectedWheels,
                    selectedFrameSet,
                    selectedHandlebars,
                    true
                    ));
                guiFrame.updateMenu();
            }
            else {
                JOptionPane.showMessageDialog(null, "Please select a frame, handlebars, tyres and brakes");
            }
        });
        
        // set each

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println("Command: " + command);
    }

    public double getPrice() {
        
        return bikePrice;
    }
}