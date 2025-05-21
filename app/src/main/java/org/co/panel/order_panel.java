package org.co.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.co.components.dave_textfield;

/**
 * a Jpanel that constructs pizza order GUI 
 */
public class order_panel extends JPanel {
    private final dave_textfield text_name, text_address; 
    private final JRadioButton rbtn_small, rbtn_medium, rbtn_large;
    private final JLabel label_size, label_topping_checkboxes;
    private final JCheckBox cb_cheese, cb_pepperoni, cb_mushrooms, cb_olives;
    private final JButton btn_place_order, btn_reset;
    private final JTextPane order_pane;
    private final ButtonGroup rbtn_group = new ButtonGroup(); //button group of radio buttons. This ensures only one button is selected.

    private size pizza_size = size.small; //default pizza size is small
    private final HashSet<topping> orders = new HashSet<>(); //stores topping_checkboxes selected
    private final JFrame root; //access to the main root Jframe. it is mainly for getting sizes. (getRootPane or getParent returns null for unknown reason)
    private double subtotal = 0.0; //a variable to store subtotal. This will be used for calculating total cost
    private final double tax_rate = 0.1316; //Canada max tax rate lol
    
    private ItemListener size_listener, topping_checkboxes_listener;
    private final HashMap<JCheckBox, topping> cb_map = new HashMap<>(); //use a hashmap to efficiently handle state changes for all topping checkboxes

    /**
     * enum that categorizes pizza sizes. it stores size of pizza price and a topping price.
     */
    public enum size {
        small(8.0, 1.0),
        medium(10.0, 1.5),
        large(12.0, 2.0);
        public final double size_price;
        public final double topping_price;
        private size(double size_price, double topping_price) {
            this.size_price = size_price;
            this.topping_price = topping_price;
        }
    } 

    /**
     * enum type of toppings
     */
    public enum topping {
        chesse("Cheese"),
        mushroom("Mushroom"),
        olive("Olive"),
        pepperoni("Pepperoni");
        public final String value;
        private topping(String value) {
           this.value = value; 
        }
    }

    /**
     * constructor of order_panel. All JComponents, action listeners, layouts, and necessary data structures are initialized.
     * @param root
     */
    public order_panel(JFrame root) {
        this.root = root;
        setSize(getSize());
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 20, 30, 20)); 
        text_name = new dave_textfield("Name: ", 25); //TODO: how to make textfield stretch out as much as it can?
        text_address = new dave_textfield("Address: ", 24);
        label_size = new JLabel("Size: ");
        rbtn_small = new JRadioButton("Small ($8)");
        rbtn_medium = new JRadioButton("Medium ($10)");
        rbtn_large = new JRadioButton("Large ($12)");
        label_topping_checkboxes = new JLabel("Topping_checkboxes($1-sm, $1.5-md, $2-lg)");
        cb_cheese = new JCheckBox("Cheese");
        cb_pepperoni = new JCheckBox("Pepperoni");
        cb_mushrooms = new JCheckBox("Mushrooms");
        cb_olives = new JCheckBox("Olives");
        btn_place_order = new JButton("Place Order");
        btn_reset = new JButton("Reset");
        order_pane = new JTextPane();
        cb_map.put(cb_cheese, topping.chesse);
        cb_map.put(cb_mushrooms, topping.mushroom);
        cb_map.put(cb_pepperoni, topping.pepperoni);
        cb_map.put(cb_olives, topping.olive);
        add_components();
        init_listeners();
    }
    
    /**
     * adds custom panels and Jcomponents
     */
    private void add_components() {
        add(text_name);
        add(text_address);
        add(Box.createVerticalStrut(10));
        
        add(size_rbtns());
        add(Box.createVerticalStrut(10));
        
        add(topping_checkboxes());
        add(Box.createVerticalStrut(10));
        
        add(order_and_reset_btns());
        add(Box.createVerticalStrut(10));
        
        add(orders_list());
    }

    /**
     * initializes action and item listeners and add them to Jcomponents
     */
    private void init_listeners() {
        //add actions to order and reset buttons
        btn_place_order.addActionListener((ActionEvent e) -> {
            order_pane.setText(get_results());
        });
        btn_reset.addActionListener((ActionEvent e) -> reset());
        //a trational way to add action listener: add listener to each button manually and use if statement to discern type of a source.
        size_listener = (ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                var source = e.getSource();
                if (source == rbtn_small) {
                    pizza_size = size.small;
                } else if (source == rbtn_medium) {
                    pizza_size = size.medium;
                } else if (source == rbtn_large) {
                    pizza_size = size.large;
                }
            }
        };
        rbtn_small.addItemListener(size_listener);
        rbtn_medium.addItemListener(size_listener);
        rbtn_large.addItemListener(size_listener);

        //another (more efficient) way to add action listeners: create a hashmap for buttons and iterate through the map
        topping_checkboxes_listener = (ItemEvent e) -> {
            var s = (JCheckBox) e.getSource(); 
            var topping = cb_map.get(s);
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //increment subtotal by topping cost based on the pizza size
                subtotal += pizza_size.topping_price;
                orders.add(topping);
            } else {
                orders.remove(topping);
                subtotal -= pizza_size.topping_price;
            }
        };
        for (var entry : cb_map.entrySet()) {
            entry.getKey().addItemListener(topping_checkboxes_listener);
        }
    }

    /**
     * resets instance variables, text fields, and selections
     */
    public void reset() {
        subtotal = 0;
        pizza_size = size.small;
        orders.clear();
        order_pane.setText("");
        text_name.set_text("");
        text_address.set_text("");
        rbtn_group.setSelected(rbtn_small.getModel(), true);
        //unselects topping checkboxes
        for (var cb : cb_map.keySet()) {
            cb.setSelected(false); 
        }
    }

    /**
     * creates a reciept of orders, incluing all informations provided by user
     * @return summary of orders and user informations
     */
    private String get_results() {
        String st = "Order Summary: \nName: " + text_name.get_text() + "\nAddress: " + text_address.get_text();
        st += "\nPizza Size: " + pizza_size.name();
        st += "\nTopping_checkboxes: ";
        for (topping t :  orders) {
            st += t.name() + ' ';
        }
        st += "\nSub Total: $" + subtotal + pizza_size.size_price; //calculates subtotal sum of pizza size and topping_checkboxes
        double tax = subtotal * tax_rate;
        st += "\nTax: $" + tax; 
        st += "\nTotal: $" + (tax + subtotal);
        st += "\n\nThank you for your order!";
        return st;
    }

    /**
     * creates a Jpanel to layout topping checkboxes 
     * @return topping checkboxes panel
     */
    private JPanel topping_checkboxes() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(label_topping_checkboxes, c);
        c.ipadx = 0;
        c.gridwidth = 1;
        c.gridx = 2;
        panel.add(cb_cheese, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(cb_pepperoni, c);
        c.gridx = 1;
        panel.add(cb_mushrooms, c);
        c.gridx = 2;
        panel.add(cb_olives, c);
        return panel;
    }

    /**
     * creates a Jpanel to layout pizza size radio buttons
     * @return pizza size radio buttons panel
     */
    private JPanel size_rbtns() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label_size);
        panel.add(rbtn_small);
        panel.add(rbtn_medium);
        panel.add(rbtn_large);
        //group the radio buttons
        rbtn_group.add(rbtn_small);
        rbtn_group.add(rbtn_medium);
        rbtn_group.add(rbtn_large);
        rbtn_group.setSelected(rbtn_small.getModel(), true);

        return panel;
    }

    /**
     * creates a Jpanel to layout place_order and reset buttons
     * @return place_order and reset buttons panel
     */
    private JPanel order_and_reset_btns() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(btn_place_order);
        panel.add(btn_reset);
        return panel;
    }

    /**
     * creatrs a scrollable pane that allows orders list to expand when necessary
     * @return scrollable pane of orders list or reciept
     */
    private JScrollPane orders_list() {
        JScrollPane pane = new JScrollPane(order_pane);
        pane.setPreferredSize(new Dimension(root.getWidth(), 600));
        return pane;
    }
}