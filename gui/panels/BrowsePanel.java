package src.gui.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import src.Utilities;
import src.gui.GUIFrame;
import src.models.Bike;

public class BrowsePanel extends JPanel {

    // will have a dynamically loaded list of products, displayed in a grid layout
    // with 4 products on each page
    // will have a search bar to search for products
    // will have a filter to filter by category
    // will have a filter to filter by price
    // will have a filter to filter by brand
    // will have a scroll bar to scroll through pages

    // TODO: create description frame when product is clicked

    private GUIFrame guiFrame;
    private int productStartIndex = 0;
    private String filter;
    private String search;

    private JFrame productFrame(Bike bike) {
        // TODO: fill this in with product info
        JFrame frame = new JFrame();
        frame.setTitle(bike.getName());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel descriptionLabel = new JLabel("<html><h1>" + bike.getName() + "</h1><p>" + bike.toString() + "</p></html>");
        descriptionLabel.setMaximumSize(new Dimension(300, 400));
        JLabel imageLabel = new JLabel(Utilities.parseImage("parts/bike-" + (int) (Math.random() * 3) + ".jpg", 160));

        GridBagConstraints c = new GridBagConstraints();
        
        c.insets = new Insets(10, 20, 10, 20);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(imageLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        panel.add(descriptionLabel, c);


        frame.add(panel);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        // frame.setMaximumSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        // frame.pack();
        return frame;
    }

    // TODO: update to use part object when part object is created
    private JPanel createProductPanel(Bike[] bikes) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(2, 2, 10, 10));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (bikes == null || bikes.length == 0) {
            // TODO: make this look better
            productPanel.setLayout(new GridLayout(2, 1, 10, 10));
            productPanel.add(new JLabel("No products found"));
            return productPanel;
        }

        // this is disgusting
        for (int i = productStartIndex; i < productStartIndex + 4; i++) {
            // System.out.println("i: " + i);
            // System.out.println("productStartIndex: " + productStartIndex);
            // System.out.println("products.length: " + products.length);
            // System.out.println("product: " + products[i]);
            JPanel product = new JPanel();
            product.setLayout(new BorderLayout());
            // TODO: add more info to product
            JLabel productLabel = new JLabel();
            JLabel productStockLabel = new JLabel();

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1, 2, 0, 10));
            
            productLabel.setHorizontalAlignment(JLabel.CENTER);
            productStockLabel.setHorizontalAlignment(JLabel.CENTER);
            
            // System.out.println("i: " + i);
            // System.out.println("bikes.length: " + bikes.length);
            // System.out.println("bikes[i]: " + bikes[i]);
            
            if (i >= bikes.length || bikes[i] == null) {
                product.add(productLabel, BorderLayout.CENTER);
            }
            else {

                // this bit is painful to look at but i can't see a better way 
                JButton productButton = new JButton("View");
                JButton productAddButton = new JButton("Add to cart");
                Bike bike = bikes[i];
                productButton.setName(bike.getName()); // TODO: needs to be set to product id/serial number once implemented
                productButton.addActionListener(e -> {
                    productFrame(bike);
                });

                productAddButton.addActionListener(e -> {
                    guiFrame.getInteractionHandler().handleConfiguration(bike);
                    guiFrame.updateMenu();
                });

                productLabel.setText("<html><h3 style='margin:0;padding:0;text-align:center;'>" + bike.getName() + "</h3><p style='margin:0;padding:0;padding-bottom:4;text-align:center;'>" + bike.getBrand() + "</p></html>");

                // TODO: replace with stock check from database
                // System.out.println("bike.getStockCount(): " + bike.getStockCount());
                if (bike.getStockCount() > 0)  {
                    // format calculateTotalCost to 2 decimal places with padding and add £ sign
                    productStockLabel.setText(String.format("In Stock (" + bike.getStockCount() + ") | %-10s", "£" + String.format("%.2f", bike.calculateTotalCost())));
                    // productStockLabel.setText("In Stock | " + "£" + bike.calculateTotalCost());
                }
                else {
                    productStockLabel.setText("Out of Stock");
                    productStockLabel.setForeground(Color.RED);
                    productButton.setEnabled(false);
                    productAddButton.setEnabled(false);
                }
                product.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                product.add(productLabel, BorderLayout.NORTH);
                product.add(productStockLabel, BorderLayout.CENTER);

                buttonPanel.add(productButton);
                buttonPanel.add(productAddButton);

                product.add(buttonPanel, BorderLayout.SOUTH);
            }
            

            productPanel.add(product);
        }

        return productPanel;
    }
    

    private Bike[] products;
    private JPanel productPanel;
    private JPanel productScrollPanel = new JPanel();
    

    private JButton scrollLeft = new JButton("<");
    private JButton scrollRight = new JButton(">");

    private void updateProductPanel() {
        // productPanel.removeAll();
        // for (int i = productStartIndex; i < productStartIndex + 4; i++) {
        //     productPanel.add(productPanel(products[i], products[i], products[i]));
        // }
        // productPanel.revalidate();
        // productPanel.repaint();
        System.out.println("filter: |" + filter + "|");
        System.out.println("search: |" + search + "|");
        System.out.println("productStartIndex: " + productStartIndex);
        Bike[] filteredProducts;

        // truly horrific code but it works
        if ((filter == null || filter == "---") && (search == null || search.length() == 0)) {
            System.out.println("no filter or search");
            filteredProducts = products;
            // System.out.println("filteredProducts: " + filteredProducts.length);
            productPanel = createProductPanel(filteredProducts);
            
        }
        else {
            System.out.println("filter or search");
            filteredProducts = guiFrame.getInteractionHandler().getProducts(filter, search);
            productPanel = createProductPanel(filteredProducts);
        }
        if (productStartIndex == 0) {
            scrollLeft.setEnabled(false);
        } else {
            scrollLeft.setEnabled(true);
        }
        if (productStartIndex + 4 >= filteredProducts.length) {
            scrollRight.setEnabled(false);
        } else {
            scrollRight.setEnabled(true);
        }
        productScrollPanel.remove(1);
        productScrollPanel.add(productPanel, BorderLayout.CENTER, 1);
        this.revalidate();
        this.repaint();
        // this.revalidate();
    }

    

    public BrowsePanel(GUIFrame frame) {

        guiFrame = frame;
        products = guiFrame.getInteractionHandler().getProducts();
        productPanel = createProductPanel(products);
        setName("Browse");

        // guiFrame.changeTitle("Browse Products");

        // JLabel introLabel = new JLabel("<html><h1 style='text-align:center'>Browse Products</h1><p>Take a look at our selection of pre-built bikes below!</p></html>");
        JLabel introLabel = new JLabel(
            "<html>" +
            "<style type=\"text/css\">" +
            "html {text-align: center;}" +
            "div {padding: 10px; margin: 10px; display: inline-block; background-color: white;}" +
            "h1 {font-size: 1.6em; padding: 0; margin: 0;}" +
            "p {font-size: 1.2em;}" +
            ".footer {font-size: 0.85em; font-style: italic;}" +

            "</style>" +
                "<div>" +
                "<h1 class='header'>Browse Products</h1>" +
                "<p>Take a look at our selection of pre-built bikes below!</p>" +
                "<p class='footer'>See something you like that's out of stock? Contact us and we'll see what we can do!</p>" +
                "</div>" +
            "</html>"
                );

        introLabel.setHorizontalAlignment(JLabel.CENTER); // why the fuck does this work but not in the other panel
        // JLabel introLabel2 = new JLabel("You can filter by category, price, and brand");

        JPanel introPanel = new JPanel();
        introPanel.setLayout(new BoxLayout(introPanel, BoxLayout.Y_AXIS));
        introPanel.add(introLabel);
        // introPanel.add(introLabel2);


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        JLabel searchLabel = new JLabel("Search: ");
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 20));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        // stupid way to do it because java is sus, but it works
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search = searchField.getText();
                productStartIndex = 0;
                updateProductPanel();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                search = searchField.getText();
                productStartIndex = 0;
                updateProductPanel();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                search = searchField.getText();
                productStartIndex = 0;
                updateProductPanel();
            }
        });


        JPanel filterPanel = new JPanel(); // not sure how to do this yet
        JLabel filterLabel = new JLabel("Filter by: ");
        JComboBox<String> filterBox = new JComboBox<String>();

        filterBox.addItem("---");
        filterBox.addItem("Category");
        filterBox.addItem("Price");
        filterBox.addItem("Brand");

        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.add(filterLabel);
        filterPanel.add(filterBox);
        // update products when filter is changed
        filterBox.addActionListener(e -> {
            filter = (String) filterBox.getSelectedItem();
            productStartIndex = 0;
            updateProductPanel();
        });

        // JPanel productPanel = new JPanel();
        // productPanel.setLayout(new GridBagLayout());
        // productPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        // GridBagConstraints c = new GridBagConstraints();
        // c.fill = GridBagConstraints.HORIZONTAL;
        
        
        // these shouldn't need to be checked for if the index is out of bounds
        // because the updateProductPanel() method will disable the buttons
        // but don't quote me on that
        scrollLeft.addActionListener(e -> {
            productStartIndex -= 4;
            updateProductPanel();
        });
        
        scrollRight.addActionListener(e -> {
            productStartIndex += 4;
            updateProductPanel();
        });

        JButton resetButton = new JButton("Reset");
        // reset the search and filter panels'
        resetButton.addActionListener(e -> {
            searchField.setText("");
            filterBox.setSelectedIndex(0);
            productStartIndex = 0;
            updateProductPanel();
        });


        // JPanel productScrollPanel = new JPanel(); // will contain scrollLeft, productPanel, scrollRight
        productScrollPanel.setLayout(new BoxLayout(productScrollPanel, BoxLayout.X_AXIS));
        productScrollPanel.add(scrollLeft);
        productScrollPanel.add(productPanel);
        productScrollPanel.add(scrollRight);


        // works?
        JPanel displayHeaderPanel = new JPanel(); // will contain the search and filter panels
        displayHeaderPanel.setLayout(new FlowLayout());
        displayHeaderPanel.add(searchPanel);
        displayHeaderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        displayHeaderPanel.add(filterPanel);
        displayHeaderPanel.add(resetButton);
        // add reset button



        JPanel displayPanel = new JPanel(); // will contain the displayHeaderPanel and productScrollPanel
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.add(displayHeaderPanel);
        displayPanel.add(productScrollPanel);
        

        setLayout(new BorderLayout(0, 10));
        add(introPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);


        updateProductPanel();
        

    }
}