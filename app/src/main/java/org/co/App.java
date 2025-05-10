package org.co;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.co.panel.order_panel;

public class App extends JFrame {
    /**
     * builds a Pizza Order App 
     */
    public App() {
        setTitle("Pizza Online Order");
        setPreferredSize(new Dimension(400, 500));
        setForeground(Color.black);
        setSize(getPreferredSize());
        add(new order_panel(this));
        setVisible(true);
        pack();
    }

    /**
     * a test method for build test
     * @return
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * initiates program
     */
    public static void main(String[] args) {
        App app = new App();
        System.out.println(app.getGreeting());
    }
}
