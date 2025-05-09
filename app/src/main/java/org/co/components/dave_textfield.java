package org.co.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class dave_textfield extends JPanel {
    private final JTextField text;
    private final JLabel label;

    public dave_textfield(String label_text, String text_placeholder, int columns) {
        text = new JTextField(text_placeholder, columns);
        label = new JLabel(label_text);
        init();
    }

    public dave_textfield(String label_text, String text_placeholder) {
        text = new JTextField(text_placeholder);
        label = new JLabel(label_text);
        init();
    }

    public dave_textfield(String label_text) {
        text = new JTextField();
        label = new JLabel(label_text);
        init();
    }

    public dave_textfield(String label_text, int columns) {
        text = new JTextField(columns);
        label = new JLabel(label_text);
        init();
    }

    public void set_textfield_width(int columns) {
        text.setColumns(columns);
    }

    private void init() {
        label.setHorizontalAlignment(JLabel.TRAILING);
        label.setLabelFor(text);
        add(label);
        add(text);
    }
}
