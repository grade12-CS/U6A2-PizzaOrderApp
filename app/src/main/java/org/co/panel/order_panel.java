package org.co.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.co.components.dave_textfield;

public class order_panel extends JPanel {
    private final dave_textfield text_name, text_address; 
    private final JRadioButton rbtn_small, rbtn_medium, rbtn_large;
    private final JLabel label_size, label_toppings;
    private final JCheckBox cb_cheese, cb_pepperoni, cb_mushrooms, cb_olives;
    private final JButton btn_place_order;
    private final JTextPane order_pane;

    private size pizza_size = size.small;
    private final HashMap<String, Integer> orders = new HashMap<>();
    private final JFrame root;

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

    public order_panel(JFrame root) {
        this.root = root;
        setSize(getSize());
        setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 20, 30, 20)); 
        text_name = new dave_textfield("Name: ", 20); //TODO: how to make textfield stretch out as much as it can?
        text_address = new dave_textfield("Address: ", 20);
        label_size = new JLabel("Size: ");
        rbtn_small = new JRadioButton("Small ($8)");
        rbtn_medium = new JRadioButton("Medium ($10)");
        rbtn_large = new JRadioButton("Large ($12)");
        label_toppings = new JLabel("Toppings($1-sm, $1.5-md, $2-lg)");
        cb_cheese = new JCheckBox("Cheese");
        cb_pepperoni = new JCheckBox("Pepperoni");
        cb_mushrooms = new JCheckBox("Mushrooms");
        cb_olives = new JCheckBox("Olives");
        btn_place_order = new JButton("Place Order");
        order_pane = new JTextPane();
        add_components();
    }
    
    private void add_components() {
        add(text_name);
        add(text_address);
        add(Box.createVerticalStrut(10));

        var group = group_radio_buttons(); 
        group.setSelected(rbtn_small.getModel(), true);
        add(rbtns());
        add(Box.createVerticalStrut(10));

        add(toppings());
        add(Box.createVerticalStrut(10));

        btn_place_order.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(btn_place_order);
        add(Box.createVerticalStrut(10));

        add(orders_list());
    }

    private JPanel toppings() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        panel.add(label_toppings, c);
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

    private JPanel rbtns() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(label_size);
        panel.add(rbtn_small);
        panel.add(rbtn_medium);
        panel.add(rbtn_large);
        return panel;
    }

    private ButtonGroup group_radio_buttons() {
        ButtonGroup g = new ButtonGroup();
        g.add(rbtn_small);
        g.add(rbtn_medium);
        g.add(rbtn_large);
        return g;
    }

    private JScrollPane orders_list() {
        JScrollPane pane = new JScrollPane(order_pane);
        pane.setPreferredSize(new Dimension(root.getWidth(), 600));
        return pane;
    }
}
