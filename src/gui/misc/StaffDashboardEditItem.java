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

public class StaffDashboardEditItem {
    public static JFrame editProduct(Product product, GUIFrame guiFrame) {
        JFrame editProductFrame = new JFrame("Edit Product");
        editProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editProductFrame.setSize(500, 500);
        editProductFrame.setLocationRelativeTo(null);
        editProductFrame.setLayout(new BorderLayout());
        switch (product.getType()) {
            case "Bike":
                JPanel bikePanel = new JPanel();
                bikePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel serialNumberLabel = new JLabel("Serial Number");
                JTextField serialNumberField = new JTextField(((Bike) product).getSerialNumber());

                JLabel brandLabel = new JLabel("Brand");
                JTextField brandField = new JTextField(((Bike) product).getBrand());

                JLabel typeLabel = new JLabel("Type");
                JTextField typeField = new JTextField(((Bike) product).getType());
                typeField.setEditable(false);

                JLabel nameLabel = new JLabel("Name");
                JTextField nameField = new JTextField(((Bike) product).getName());

                JComboBox<String> wheelComboBox = new JComboBox<String>();
                for (Wheels wheel : guiFrame.getInteractionHandler().getWheels()) {
                    wheelComboBox.addItem(wheel.getName());
                }
                wheelComboBox.setSelectedItem(((Bike) product).getWheels().getName());

                JComboBox<String> frameSetComboBox = new JComboBox<String>();
                for (FrameSet frameSet : guiFrame.getInteractionHandler().getFrames()) {
                    frameSetComboBox.addItem(frameSet.getName());
                }
                frameSetComboBox.setSelectedItem(((Bike) product).getFrameSet().getName());

                JComboBox<String> handlebarsComboBox = new JComboBox<String>();
                for (Handlebars handlebars : guiFrame.getInteractionHandler().getHandlebars()) {
                    handlebarsComboBox.addItem(handlebars.getName());
                }
                handlebarsComboBox.setSelectedItem(((Bike) product).getHandlebars().getName());

                JLabel customLabel = new JLabel("Custom");
                JCheckBox customCheckBox = new JCheckBox();
                customCheckBox.setSelected(((Bike) product).isCustom());

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

                editProductFrame.add(new JLabel("<html><p style='padding:8;font-style:italic;'>Stock cannot be added manually to complete bikes - you should add the individual parts, the program will calculate the available stock on next startup.</p></html>"), BorderLayout.NORTH);
                editProductFrame.add(bikePanel, BorderLayout.CENTER);
                editProductFrame.add(saveButton, BorderLayout.SOUTH);

                saveButton.addActionListener(e -> {
                    int bikeID = ((Bike) product).getBikeID();
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

                    Bike bike = new Bike(bikeID, serialNumber, brand, type, name, wheels, frameSet, handlebars, custom);
                    
                    try {
                        Utilities.updateBike(bike, null);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    editProductFrame.dispose();
                });
                break;
            case "Wheels":
                JPanel wheelsPanel = new JPanel();
                wheelsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                // add a form for each field

                JLabel wheelSerialNumberLabel = new JLabel("Serial Number");
                JTextField wheelSerialNumberField = new JTextField(((Wheels) product).getSerialNumber());

                JLabel wheelsBrandLabel = new JLabel("Brand");
                JTextField wheelsBrandField = new JTextField(((Wheels) product).getBrand());

                JLabel wheelsTypeLabel = new JLabel("Type");
                JTextField wheelsTypeField = new JTextField(((Wheels) product).getType());
                wheelsTypeField.setEditable(false);

                JLabel wheelsNameLabel = new JLabel("Name");
                JTextField wheelsNameField = new JTextField(((Wheels) product).getName());

                JLabel wheelsUnitCostLabel = new JLabel("Unit Cost");
                JTextField wheelsUnitCostField = new JTextField(String.valueOf(((Wheels) product).getUnitCost()));

                JLabel wheelsStockCountLabel = new JLabel("Stock Count");
                JTextField wheelsStockCountField = new JTextField(String.valueOf(((Wheels) product).getStockCount()));

                JLabel diameterLabel = new JLabel("Diameter");
                JTextField diameterField = new JTextField(String.valueOf(((Wheels) product).getDiameter()));

                JLabel brakeTypeLabel = new JLabel("Brake Type");
                JComboBox<String> brakeTypeComboBox = new JComboBox<String>();
                for (BrakeType brakeType : BrakeType.values()) {
                    brakeTypeComboBox.addItem(brakeType.toString());
                }
                brakeTypeComboBox.setSelectedItem(((Wheels) product).getBrakeType().toString());

                JLabel wheelTypeLabel = new JLabel("Wheel Type");
                JComboBox<String> wheelTypeComboBox = new JComboBox<String>();
                for (WheelType wheelType : WheelType.values()) {
                    wheelTypeComboBox.addItem(wheelType.toString());
                }
                wheelTypeComboBox.setSelectedItem(((Wheels) product).getWheelType().toString());


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

                editProductFrame.add(wheelsPanel, BorderLayout.CENTER);
                editProductFrame.add(wheelsSaveButton, BorderLayout.SOUTH);

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
                        Utilities.updateWheels(wheels, (Wheels) product, null);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    editProductFrame.dispose();

                });
                break;
            case "FrameSet":
                JPanel frameSetPanel = new JPanel();
                frameSetPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                // add a form for each field

                JLabel frameSetSerialNumberLabel = new JLabel("Serial Number");
                JTextField frameSetSerialNumberField = new JTextField(((FrameSet) product).getSerialNumber());

                JLabel frameSetBrandLabel = new JLabel("Brand");
                JTextField frameSetBrandField = new JTextField(((FrameSet) product).getBrand());

                JLabel frameSetTypeLabel = new JLabel("Type");
                JTextField frameSetTypeField = new JTextField(((FrameSet) product).getType());
                frameSetTypeField.setEditable(false);

                JLabel frameSetNameLabel = new JLabel("Name");
                JTextField frameSetNameField = new JTextField(((FrameSet) product).getName());

                JLabel frameSetUnitCostLabel = new JLabel("Unit Cost");
                JTextField frameSetUnitCostField = new JTextField(String.valueOf(((FrameSet) product).getUnitCost()));

                JLabel frameSetStockCountLabel = new JLabel("Stock Count");
                JTextField frameSetStockCountField = new JTextField(String.valueOf(((FrameSet) product).getStockCount()));

                JLabel frameSizeLabel = new JLabel("Frame Size");
                JTextField frameSizeField = new JTextField(String.valueOf(((FrameSet) product).getSize()));

                JLabel frameGearsLabel = new JLabel("Frame Gears");
                JTextField frameGearsField = new JTextField(String.valueOf(((FrameSet) product).getGears()));

                JLabel frameTypeLabel = new JLabel("Frame Type");
                JComboBox<String> frameTypeComboBox = new JComboBox<String>();
                for (FrameType frameType : FrameType.values()) {
                    frameTypeComboBox.addItem(frameType.toString());
                }
                frameTypeComboBox.setSelectedItem(((FrameSet) product).getFrameType().toString());

                JLabel frameHasShockAbsorbersLabel = new JLabel("Frame Has Shock Absorbers");
                JCheckBox frameHasShockAbsorbersCheckBox = new JCheckBox();
                frameHasShockAbsorbersCheckBox.setSelected(((FrameSet) product).getHasShockAbsorbers());

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

                editProductFrame.add(frameSetPanel, BorderLayout.CENTER);
                editProductFrame.add(frameSetSaveButton, BorderLayout.SOUTH);

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
                        Utilities.updateFrameSet(frameSet, (FrameSet) product, null);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    editProductFrame.dispose();

                });
                break;
            case "Handlebars":
                JPanel handlebarsPanel = new JPanel();
                handlebarsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                // add a form for each field

                JLabel handlebarsSerialNumberLabel = new JLabel("Serial Number");
                JTextField handlebarsSerialNumberField = new JTextField(((Handlebars) product).getSerialNumber());

                JLabel handlebarsBrandLabel = new JLabel("Brand");
                JTextField handlebarsBrandField = new JTextField(((Handlebars) product).getBrand());

                JLabel handlebarsTypeLabel = new JLabel("Type");
                JTextField handlebarsTypeField = new JTextField(((Handlebars) product).getType());
                handlebarsTypeField.setEditable(false);

                JLabel handlebarsNameLabel = new JLabel("Name");
                JTextField handlebarsNameField = new JTextField(((Handlebars) product).getName());

                JLabel handlebarsUnitCostLabel = new JLabel("Unit Cost");
                JTextField handlebarsUnitCostField = new JTextField(String.valueOf(((Handlebars) product).getUnitCost()));

                JLabel handlebarsStockCountLabel = new JLabel("Stock Count");
                JTextField handlebarsStockCountField = new JTextField(String.valueOf(((Handlebars) product).getStockCount()));

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

                editProductFrame.add(handlebarsPanel, BorderLayout.CENTER);
                editProductFrame.add(handlebarsSaveButton, BorderLayout.SOUTH);

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
                        Utilities.updateHandlebars(handlebars, (Handlebars) product, null);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    editProductFrame.dispose();

                });

                


        }


        // editProductFrame.pack();
        editProductFrame.setVisible(true);
        return editProductFrame;
    }
}
