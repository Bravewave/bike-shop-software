package src.gui.misc;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import src.Utilities;
import src.models.*;


public class UtilitiesFrame extends JFrame {
    
    public UtilitiesFrame() {
        super("Utilities");
        Bike bike = Utilities.generateRandomBike();
        FrameSet frameSet = Utilities.generateRandomFrameSet();
        Handlebars handlebars = Utilities.generateRandomHandlebars();
        Wheels wheels = Utilities.generateRandomWheels();
        Order order = Utilities.generateRandomOrder();
        Customer customer = Utilities.generateRandomCustomer();
        Address address = Utilities.generateRandomAddress();

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton addWheelsButton = new JButton("Add Wheels");
        JButton addFrameSetButton = new JButton("Add Frame Set");
        JButton addHandlebarsButton = new JButton("Add Handlebars");
        JButton getWheelButton = new JButton("Get Wheel");
        JButton getFrameSetButton = new JButton("Get Frame Set");
        JButton getHandlebarsButton = new JButton("Get Handlebars");
        JButton resetDatabaseButton = new JButton("Reset Database b");
        JButton addBikeButton = new JButton("Add Bike");
        JButton getBikeButton = new JButton("Get Bike");
        JButton addOrderButton = new JButton("Add Order");
        JButton getOrderButton = new JButton("Get Order");
        JButton addCustomerButton = new JButton("Add Customer");
        JButton getCustomerButton = new JButton("Get Customer");
        JButton addAddressButton = new JButton("Add Address");
        JButton getAddressButton = new JButton("Get Address");
        JButton advanceLastOrderButton = new JButton("Advance Last Order");
        JButton updateBikeStockButton = new JButton("Update Bike Stock");

        addWheelsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addWheels(wheels, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getWheelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.getWheelsCount();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addFrameSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addFrame(frameSet, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getFrameSetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.getFrameSetsCount();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addHandlebarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addHandlebar(handlebars, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getHandlebarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.getHandlebarsCount();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        resetDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.rebuildTable();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.insertRandomBike();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        getBikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.getBikes(null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addOrder(order, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // getOrderButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         try {
        //             Utilities.getOrders();
        //         } catch (SQLException e1) {
        //             e1.printStackTrace();
        //         }
        //     }
        // });

        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addCustomer(customer, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // getCustomerButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         try {
        //             Utilities.getCustomers();
        //         } catch (SQLException e1) {
        //             e1.printStackTrace();
        //         }
        //     }
        // });

        addAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.addAddress(address, null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // getAddressButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         try {
        //             Utilities.getAddresses();
        //         } catch (SQLException e1) {
        //             e1.printStackTrace();
        //         }
        //     }
        // });

        advanceLastOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.progressOrderStatus(Utilities.getLastOrder(null), null, 1);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        updateBikeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Utilities.updateBikeStock(null);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });



        
        
        panel.add(addWheelsButton);
        panel.add(getWheelButton);
        panel.add(addFrameSetButton);
        panel.add(getFrameSetButton);
        panel.add(addHandlebarsButton);
        panel.add(getHandlebarsButton);
        panel.add(addBikeButton);
        panel.add(getBikeButton);
        panel.add(addOrderButton);
        panel.add(getOrderButton);
        panel.add(addCustomerButton);
        panel.add(getCustomerButton);
        panel.add(addAddressButton);
        panel.add(getAddressButton);
        panel.add(advanceLastOrderButton);
        panel.add(updateBikeStockButton);






        // danger button >:(
        panel.add(resetDatabaseButton);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
