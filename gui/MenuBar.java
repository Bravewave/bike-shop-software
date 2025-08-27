package src.gui;

import java.awt.*;
import javax.swing.*;

import src.InteractionHandler;
import src.Utilities;
import src.InteractionHandler.LoginType;
import src.gui.misc.UtilitiesFrame;
import src.gui.panels.*;
import src.models.Bike;
import src.models.Staff;

public class MenuBar extends JMenuBar {
    
        private GUIFrame guiFrame;
        private boolean loggedIn = false;
        private LoginType userType;
        private Staff loggedInStaff;

        private JMenu seperator(String symbol) {
            JMenu seperator = new JMenu(" " + symbol + " ");
            seperator.setEnabled(false);
            return seperator;
        }

        private JMenuItem menuItem(String text, Component component, int index) {
            JMenuItem menuItem = new JMenuItem(text);
            setName(Integer.toString(index));
            menuItem.addActionListener(e -> {
                // getComponentZOrder(this);
                // System.out.println(getComponentZOrder(this));
                guiFrame.navigateBack(index, component.getName());
            });
            return menuItem;
        }

        private JPanel basketItem(Bike bike) {
            JPanel basketPanel = new JPanel();

            JButton basketLabel = new JButton(bike.getName());
            // make the button look like a label
            basketLabel.setOpaque(false);
            basketLabel.setContentAreaFilled(false);
            basketLabel.setBorderPainted(false);
            basketLabel.setFocusPainted(false);
            basketLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            JButton removeButton = new JButton("X");
            removeButton.setForeground(Color.RED);

            basketPanel.setLayout(new BoxLayout(basketPanel, BoxLayout.X_AXIS));
            basketPanel.add(basketLabel);
            basketPanel.add(Box.createHorizontalGlue());
            basketPanel.add(removeButton);

            basketLabel.addActionListener(e -> {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                // frame.add(new BasketPanel(guiFrame, serial)); // TODO: implement basket panel
                frame.add(new JLabel("Item: " + bike.getName()));
            });

            removeButton.addActionListener(e -> {
                guiFrame.getInteractionHandler().removeFromBasket(bike.getSerialNumber());
                guiFrame.updateMenu();
                Container parent = basketPanel.getParent();
                parent.remove(basketPanel);
                parent.revalidate();
                parent.repaint();

            });


            return basketPanel;
        }
    
        public MenuBar(GUIFrame frame, InteractionHandler handler) {
            
            guiFrame = frame;
            userType = handler.getLoginType();
            loggedInStaff = handler.getStaff();

            JMenu backMenu = new JMenu("↓");
            // System.out.println(guiFrame.getCompleteFlow().length);
            if (guiFrame.getCompleteFlow()[0] != null) {
                for (int i = guiFrame.getCompleteFlow().length - 1; i >= 0; i--) {
                    if (guiFrame.getCompleteFlow()[i] != null) {
                        backMenu.add(menuItem(guiFrame.getCompleteFlow()[i].getName(), guiFrame.getCompleteFlow()[i], i));
                    }
                }
            }
            else {
                backMenu.setEnabled(false);
            }
            // JMenuItem back = new JMenuItem("Back");

            // styling for back and exit buttons
            JButton back = new JButton("Back"); // wanna try and figure out a way of showing the last 10 pages when held
            back.setBorderPainted(false);
            back.setFocusPainted(false);
            back.setContentAreaFilled(false);
            if (guiFrame.getFlow(1) == null) {
                back.setEnabled(false);
            }

            JButton refresh = new JButton("Refresh");
            refresh.setBorderPainted(false);
            refresh.setFocusPainted(false);
            refresh.setContentAreaFilled(false);
            refresh.addActionListener(e -> {
                handler.updateStock();
                guiFrame.updateMenu();
            });

            JButton logout = new JButton("✖  Logout");
            logout.setBorderPainted(false);
            logout.setFocusPainted(false);
            logout.setContentAreaFilled(false);

            // main menu
            JMenu menu = new JMenu("Main Menu");
            JMenuItem home = new JMenuItem("Home");
            JMenuItem browse = new JMenuItem("Browse");
            JMenuItem configure = new JMenuItem("Configure");
            menu.add(home);
            menu.add(browse);
            menu.add(configure);

            // customer menu
            JMenu customerMenu = new JMenu("Customers");
            JMenuItem customerLogin = new JMenuItem("Customer Login");
            JMenuItem customerDashboard = new JMenuItem("Customer Dashboard");
            JMenuItem viewOrders = new JMenuItem("View Order");
            if (userType == LoginType.CUSTOMER) {
                customerMenu.add(customerDashboard);
            }
            else {
                customerMenu.add(customerLogin);
            }
            customerMenu.add(viewOrders);

            // utilities menu
            JButton utilitiesMenu = new JButton("Utilities [DEBUG]");
            utilitiesMenu.setBorderPainted(false);
            utilitiesMenu.setFocusPainted(false);
            utilitiesMenu.setContentAreaFilled(false);
            utilitiesMenu.addActionListener(e -> {
                JFrame utilitiesFrame = new UtilitiesFrame();
                utilitiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                utilitiesFrame.setSize(400, 400);
                utilitiesFrame.setLocationRelativeTo(null);
                utilitiesFrame.setVisible(true);
            });


            // basket menu
            JMenu basketMenu = new JMenu("Basket");
            // JMenuItem basket = new JMenuItem("Checkout");
            // basketMenu.add(basket);
            System.out.println("Basket: " + handler.getBasketSize());
            if (handler.getBasketSize() == 0) {
                basketMenu.setEnabled(false);
                basketMenu.setText("Basket (0)");
            }
            else {
                basketMenu.setEnabled(true);
                basketMenu.setText("Basket (" + handler.getBasketSize() + ")");
                // basket.setText("Checkout (" + handler.getBasketSize() + ")");
                for (int i = 0; i < handler.getBasketSize(); i++) {
                    basketMenu.add(basketItem(handler.getBasket().get(i))); // TODO: replace with serial
                }
                JMenuItem checkoutButton = new JMenuItem("Checkout →");
                // make the checkout button look like a menu item
                checkoutButton.setBorderPainted(false);
                checkoutButton.setFocusPainted(false);
                checkoutButton.setContentAreaFilled(false);
                checkoutButton.addActionListener(e -> {
                    guiFrame.navigateTo(new CheckoutPanel(guiFrame));
                });
                basketMenu.add(checkoutButton);
            }

            // staff menu
            JMenu staffMenu = new JMenu("Staff");
            JMenuItem staffLogin = new JMenuItem("Staff Login");
            JMenuItem staffDashboard = new JMenuItem("Staff Dashboard");
            if (userType == LoginType.CUSTOMER) {
                staffMenu.setEnabled(false);
            }
            else {
                staffMenu.setEnabled(true);
            }
            if (userType == LoginType.STAFF) {
                staffMenu.add(staffDashboard);
            }
            else {
                staffMenu.add(staffLogin);
            }
            
            

            // displays if the user is logged in
            JButton userDisplay = new JButton("Not logged in");
            userDisplay.setBorderPainted(false);
            userDisplay.setFocusPainted(false);
            userDisplay.setContentAreaFilled(false);
            userDisplay.setEnabled(false);
            // TODO: update to show username once backend is implemented
            if (userType == LoginType.STAFF) {
                userDisplay.setText(loggedInStaff.getUsername());
                userDisplay.setEnabled(true);
            }
            else if (userType == LoginType.CUSTOMER) {
                userDisplay.setText("Customer");
                userDisplay.setEnabled(true);
            }
    
            add(backMenu);
            add(back);
            
            add(seperator("|"));
            add(refresh);
            add(seperator("|"));
            add(menu);
            add(seperator("•"));
            add(customerMenu);
            add(seperator("•"));
            add(utilitiesMenu);
            add(Box.createHorizontalGlue());
            add(basketMenu);
            add(seperator("•"));
            add(staffMenu);
            add(seperator("|"));
            if (loggedIn) {
                add(logout);
            }
            else {
                add(userDisplay);
            }
    
            home.addActionListener(e -> guiFrame.navigateTo(new HomePanel(guiFrame)));
            staffLogin.addActionListener(e -> guiFrame.navigateTo(new StaffLoginPanel(guiFrame)));
            staffDashboard.addActionListener(e -> guiFrame.navigateTo(new StaffDashboardPanel(guiFrame)));
            customerLogin.addActionListener(e -> guiFrame.navigateTo(new CustomerLoginPanel(guiFrame)));
            customerDashboard.addActionListener(e -> guiFrame.navigateTo(new CustomerDashboardPanel(guiFrame)));
            viewOrders.addActionListener(e -> guiFrame.navigateTo(new ViewOrderPanel(guiFrame)));
            browse.addActionListener(e -> guiFrame.navigateTo(new BrowsePanel(guiFrame)));
            configure.addActionListener(e -> guiFrame.navigateTo(new ConfigurePanel(guiFrame)));
            back.addActionListener(e -> guiFrame.navigateBack());
            userDisplay.addActionListener(e -> {
                guiFrame.getInteractionHandler().logout();
                JOptionPane.showMessageDialog(null, "You have been logged out.");
                guiFrame.navigateTo(new HomePanel(guiFrame));
                guiFrame.updateMenu();
            });
            // logout.addActionListener(e -> System.exit(0));
        }
    
}
