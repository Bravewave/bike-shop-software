package src.gui.misc;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import src.Utilities;
import src.gui.GUIFrame;
import src.models.*;
import src.models.FrameSet.FrameType;
import src.models.Handlebars.HandlebarType;
import src.models.Wheels.BrakeType;
import src.models.Wheels.WheelType;

public class StaffDashboardAddItem {
    public static JFrame addBike(GUIFrame guiFrame) {
        JFrame frame = new JFrame("Add Bike");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel bikePanel = new JPanel();
        bikePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel serialNumberLabel = new JLabel("Serial Number");
        JTextField serialNumberField = new JTextField();

        JLabel brandLabel = new JLabel("Brand");
        JTextField brandField = new JTextField();

        JLabel typeLabel = new JLabel("Type");
        JTextField typeField = new JTextField("Bike");
        typeField.setEditable(false);

        JLabel nameLabel = new JLabel("Name");
        JTextField nameField = new JTextField();

        JComboBox<String> wheelComboBox = new JComboBox<String>();
        for (Wheels wheel : guiFrame.getInteractionHandler().getWheels()) {
            wheelComboBox.addItem(wheel.getName());
        }

        JComboBox<String> frameSetComboBox = new JComboBox<String>();
        for (FrameSet frameSet : guiFrame.getInteractionHandler().getFrames()) {
            frameSetComboBox.addItem(frameSet.getName());
        }

        JComboBox<String> handlebarsComboBox = new JComboBox<String>();
        for (Handlebars handlebars : guiFrame.getInteractionHandler().getHandlebars()) {
            handlebarsComboBox.addItem(handlebars.getName());
        }

        JLabel customLabel = new JLabel("Custom");
        JCheckBox customCheckBox = new JCheckBox();

        bikePanel.setLayout(new GridLayout(0, 2));
        bikePanel.add(serialNumberLabel);
        bikePanel.add(serialNumberField);
        bikePanel.add(brandLabel);
        bikePanel.add(brandField);
        bikePanel.add(typeLabel);
        bikePanel.add(typeField);
        bikePanel.add(nameLabel);
        bikePanel.add(nameField);
        bikePanel.add(new JLabel("Wheels"));
        bikePanel.add(wheelComboBox);
        bikePanel.add(new JLabel("Frame Set"));
        bikePanel.add(frameSetComboBox);
        bikePanel.add(new JLabel("Handlebars"));
        bikePanel.add(handlebarsComboBox);
        bikePanel.add(customLabel);
        bikePanel.add(customCheckBox);

        JButton saveButton = new JButton("Save");

        frame.add(new JLabel("<html><p style='padding:8;font-style:italic;'>Stock cannot be added manually to complete bikes - you should add the individual parts, the program will calculate the available stock on next startup.</p></html>"), BorderLayout.NORTH);
        frame.add(bikePanel, BorderLayout.CENTER);
        frame.add(saveButton, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> {
            // int bikeID = ((Bike) product).getBikeID();
            String serialNumber = serialNumberField.getText();
            String brand = brandField.getText();
            String type = typeField.getText();
            String name = nameField.getText();
            Wheels wheels = guiFrame.getInteractionHandler().getWheels()[wheelComboBox.getSelectedIndex()];
            FrameSet frameSet = guiFrame.getInteractionHandler().getFrames()[frameSetComboBox.getSelectedIndex()];
            Handlebars handlebars = guiFrame.getInteractionHandler().getHandlebars()[handlebarsComboBox.getSelectedIndex()];
            // double unitCost = Double.parseDouble(unitCostField.getText());
            // int stockCount = Integer.parseInt(stockCountField.getText());
            boolean custom = customCheckBox.isSelected();

            Bike bike = new Bike(serialNumber, brand, type, name, wheels, frameSet, handlebars, custom);
            
            try {
                Utilities.addWheels(wheels, null);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            frame.dispose();
        });

        return frame;
    }

    public static JFrame addWheels(GUIFrame guiFrame) {
        JFrame frame = new JFrame("Add Wheels");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel wheelsPanel = new JPanel();
        wheelsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // add a form for each field

        JLabel wheelSerialNumberLabel = new JLabel("Serial Number");
        JTextField wheelSerialNumberField = new JTextField();

        JLabel wheelsBrandLabel = new JLabel("Brand");
        JTextField wheelsBrandField = new JTextField();

        JLabel wheelsTypeLabel = new JLabel("Type");
        JTextField wheelsTypeField = new JTextField("Wheels");
        wheelsTypeField.setEditable(false);

        JLabel wheelsNameLabel = new JLabel("Name");
        JTextField wheelsNameField = new JTextField();

        JLabel wheelsUnitCostLabel = new JLabel("Unit Cost");
        JTextField wheelsUnitCostField = new JTextField();

        JLabel wheelsStockCountLabel = new JLabel("Stock Count");
        JTextField wheelsStockCountField = new JTextField();

        JLabel diameterLabel = new JLabel("Diameter");
        JTextField diameterField = new JTextField();

        JLabel brakeTypeLabel = new JLabel("Brake Type");
        JComboBox<String> brakeTypeComboBox = new JComboBox<String>();
        for (BrakeType brakeType : BrakeType.values()) {
            brakeTypeComboBox.addItem(brakeType.toString());
        }

        JLabel wheelTypeLabel = new JLabel("Wheel Type");
        JComboBox<String> wheelTypeComboBox = new JComboBox<String>();
        for (WheelType wheelType : WheelType.values()) {
            wheelTypeComboBox.addItem(wheelType.toString());
        }


        wheelsPanel.setLayout(new GridLayout(0, 2));
        wheelsPanel.add(wheelSerialNumberLabel);
        wheelsPanel.add(wheelSerialNumberField);
        wheelsPanel.add(wheelsBrandLabel);
        wheelsPanel.add(wheelsBrandField);
        wheelsPanel.add(wheelsTypeLabel);
        wheelsPanel.add(wheelsTypeField);
        wheelsPanel.add(wheelsNameLabel);
        wheelsPanel.add(wheelsNameField);
        wheelsPanel.add(wheelsUnitCostLabel);
        wheelsPanel.add(wheelsUnitCostField);
        wheelsPanel.add(wheelsStockCountLabel);
        wheelsPanel.add(wheelsStockCountField);
        wheelsPanel.add(diameterLabel);
        wheelsPanel.add(diameterField);
        wheelsPanel.add(brakeTypeLabel);
        wheelsPanel.add(brakeTypeComboBox);
        wheelsPanel.add(wheelTypeLabel);
        wheelsPanel.add(wheelTypeComboBox);

        JButton wheelsSaveButton = new JButton("Save");

        frame.add(wheelsPanel, BorderLayout.CENTER);
        frame.add(wheelsSaveButton, BorderLayout.SOUTH);

        wheelsSaveButton.addActionListener(e -> {
            String serialNumber = wheelSerialNumberField.getText();
            String wheelsBrand = wheelsBrandField.getText();
            String wheelsType = wheelsTypeField.getText();
            String wheelsName = wheelsNameField.getText();
            double wheelsUnitCost = Double.parseDouble(wheelsUnitCostField.getText());
            int wheelsStockCount = Integer.parseInt(wheelsStockCountField.getText());
            int diameter = Integer.parseInt(diameterField.getText());
            BrakeType brakeType = BrakeType.valueOf(brakeTypeComboBox.getSelectedItem().toString());
            WheelType wheelType = WheelType.valueOf(wheelTypeComboBox.getSelectedItem().toString());

            Wheels wheels = new Wheels(serialNumber, wheelsBrand, wheelsType, wheelsName, wheelsUnitCost , wheelsStockCount, diameter, wheelType, brakeType);
            try {
                Utilities.addWheels(wheels, null);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            frame.dispose();

        });

        return frame;
    }

    public static JFrame addFrame (GUIFrame guiFrame) {
        JFrame frame = new JFrame("Add Frame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel frameSetPanel = new JPanel();
        frameSetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // add a form for each field

        JLabel frameSetSerialNumberLabel = new JLabel("Serial Number");
        JTextField frameSetSerialNumberField = new JTextField();

        JLabel frameSetBrandLabel = new JLabel("Brand");
        JTextField frameSetBrandField = new JTextField();

        JLabel frameSetTypeLabel = new JLabel("Type");
        JTextField frameSetTypeField = new JTextField();
        frameSetTypeField.setEditable(false);

        JLabel frameSetNameLabel = new JLabel("Name");
        JTextField frameSetNameField = new JTextField();

        JLabel frameSetUnitCostLabel = new JLabel("Unit Cost");
        JTextField frameSetUnitCostField = new JTextField();

        JLabel frameSetStockCountLabel = new JLabel("Stock Count");
        JTextField frameSetStockCountField = new JTextField();

        JLabel frameSizeLabel = new JLabel("Frame Size");
        JTextField frameSizeField = new JTextField();

        JLabel frameGearsLabel = new JLabel("Frame Gears");
        JTextField frameGearsField = new JTextField();

        JLabel frameTypeLabel = new JLabel("Frame Type");
        JComboBox<String> frameTypeComboBox = new JComboBox<String>();
        for (FrameType frameType : FrameType.values()) {
            frameTypeComboBox.addItem(frameType.toString());
        }

        JLabel frameHasShockAbsorbersLabel = new JLabel("Frame Has Shock Absorbers");
        JCheckBox frameHasShockAbsorbersCheckBox = new JCheckBox();

        frameSetPanel.setLayout(new GridLayout(0, 2));
        frameSetPanel.add(frameSetSerialNumberLabel);
        frameSetPanel.add(frameSetSerialNumberField);
        frameSetPanel.add(frameSetBrandLabel);
        frameSetPanel.add(frameSetBrandField);
        frameSetPanel.add(frameSetTypeLabel);
        frameSetPanel.add(frameSetTypeField);
        frameSetPanel.add(frameSetNameLabel);
        frameSetPanel.add(frameSetNameField);
        frameSetPanel.add(frameSetUnitCostLabel);
        frameSetPanel.add(frameSetUnitCostField);
        frameSetPanel.add(frameSetStockCountLabel);
        frameSetPanel.add(frameSetStockCountField);
        frameSetPanel.add(frameSizeLabel);
        frameSetPanel.add(frameSizeField);
        frameSetPanel.add(frameGearsLabel);
        frameSetPanel.add(frameGearsField);
        frameSetPanel.add(frameTypeLabel);
        frameSetPanel.add(frameTypeComboBox);
        frameSetPanel.add(frameHasShockAbsorbersLabel);
        frameSetPanel.add(frameHasShockAbsorbersCheckBox);

        JButton frameSetSaveButton = new JButton("Save");

        frame.add(frameSetPanel, BorderLayout.CENTER);
        frame.add(frameSetSaveButton, BorderLayout.SOUTH);

        frameSetSaveButton.addActionListener(e -> {
            String serialNumber = frameSetSerialNumberField.getText();
            String frameSetBrand = frameSetBrandField.getText();
            String frameSetType = frameSetTypeField.getText();
            String frameSetName = frameSetNameField.getText();
            double frameSetUnitCost = Double.parseDouble(frameSetUnitCostField.getText());
            int frameSetStockCount = Integer.parseInt(frameSetStockCountField.getText());
            int frameSize = Integer.parseInt(frameSizeField.getText());
            boolean frameHasShockAbsorbers = frameHasShockAbsorbersCheckBox.isSelected();
            int frameGears = Integer.parseInt(frameGearsField.getText());
            FrameType frameType = FrameType.valueOf(frameTypeComboBox.getSelectedItem().toString());

            FrameSet frameSet = new FrameSet(serialNumber, frameSetBrand, frameSetType, frameSetName, frameSetUnitCost , frameSetStockCount, frameSize, frameHasShockAbsorbers, frameGears, frameType);
            try {
                Utilities.addFrame(frameSet, null);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            frame.dispose();

        });
        
        return frame;
    }

    public static JFrame addHandlebars (GUIFrame guiFrame) {
        JFrame frame = new JFrame("Add Handlebars");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel handlebarsPanel = new JPanel();
        handlebarsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // add a form for each field

        JLabel handlebarsSerialNumberLabel = new JLabel("Serial Number");
        JTextField handlebarsSerialNumberField = new JTextField();

        JLabel handlebarsBrandLabel = new JLabel("Brand");
        JTextField handlebarsBrandField = new JTextField();

        JLabel handlebarsTypeLabel = new JLabel("Type");
        JTextField handlebarsTypeField = new JTextField();
        handlebarsTypeField.setEditable(false);

        JLabel handlebarsNameLabel = new JLabel("Name");
        JTextField handlebarsNameField = new JTextField();

        JLabel handlebarsUnitCostLabel = new JLabel("Unit Cost");
        JTextField handlebarsUnitCostField = new JTextField();

        JLabel handlebarsStockCountLabel = new JLabel("Stock Count");
        JTextField handlebarsStockCountField = new JTextField();

        JLabel handlebarTypeLabel = new JLabel("Handlebar Type");
        JComboBox<String> handlebarTypeComboBox = new JComboBox<String>();
        for (HandlebarType handlebarType : HandlebarType.values()) {
            handlebarTypeComboBox.addItem(handlebarType.toString());
        }

        handlebarsPanel.setLayout(new GridLayout(0, 2));
        handlebarsPanel.add(handlebarsSerialNumberLabel);
        handlebarsPanel.add(handlebarsSerialNumberField);
        handlebarsPanel.add(handlebarsBrandLabel);
        handlebarsPanel.add(handlebarsBrandField);
        handlebarsPanel.add(handlebarsTypeLabel);
        handlebarsPanel.add(handlebarsTypeField);
        handlebarsPanel.add(handlebarsNameLabel);
        handlebarsPanel.add(handlebarsNameField);
        handlebarsPanel.add(handlebarsUnitCostLabel);
        handlebarsPanel.add(handlebarsUnitCostField);
        handlebarsPanel.add(handlebarsStockCountLabel);
        handlebarsPanel.add(handlebarsStockCountField);
        handlebarsPanel.add(handlebarTypeLabel);
        handlebarsPanel.add(handlebarTypeComboBox);

        JButton handlebarsSaveButton = new JButton("Save");

        frame.add(handlebarsPanel, BorderLayout.CENTER);
        frame.add(handlebarsSaveButton, BorderLayout.SOUTH);

        handlebarsSaveButton.addActionListener(e -> {
            String serialNumber = handlebarsSerialNumberField.getText();
            String handlebarsBrand = handlebarsBrandField.getText();
            String handlebarsType = handlebarsTypeField.getText();
            String handlebarsName = handlebarsNameField.getText();
            double handlebarsUnitCost = Double.parseDouble(handlebarsUnitCostField.getText());
            int handlebarsStockCount = Integer.parseInt(handlebarsStockCountField.getText());
            HandlebarType handlebarType = HandlebarType.valueOf(handlebarTypeComboBox.getSelectedItem().toString());

            Handlebars handlebars = new Handlebars(serialNumber, handlebarsBrand, handlebarsType, handlebarsName, handlebarsUnitCost , handlebarsStockCount, handlebarType);
            try {
                Utilities.addHandlebar(handlebars, null);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            frame.dispose();

        });

        return frame;
    }
}
