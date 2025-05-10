package org.co.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * a custom label-integrated textfield, just like using SpringLayout.
 */
public class dave_textfield extends JPanel {
    private final JTextField text;
    private final JLabel label;

    /**
     * initializes textfield and label 
     * @param label_text text of label
     * @param text_placeholder placeholder of textfield
     * @param columns integer width of textfield
     */
    public dave_textfield(String label_text, String text_placeholder, int columns) {
        text = new JTextField(text_placeholder, columns);
        label = new JLabel(label_text);
        init();
    }

    /**
     * initializes textfield and label 
     * @param label_text text of label
     * @param text_placeholder placeholder of textfield
     */
    public dave_textfield(String label_text, String text_placeholder) {
        text = new JTextField(text_placeholder);
        label = new JLabel(label_text);
        init();
    }

    /**
     * initializes textfield and label 
     * @param label_text text of label
     */
    public dave_textfield(String label_text) {
        text = new JTextField();
        label = new JLabel(label_text);
        init();
    }

    /**
     * initializes textfield and label 
     * @param label_text text of label
     * @param columns integer width of textfield
     */
    public dave_textfield(String label_text, int columns) {
        text = new JTextField(columns);
        label = new JLabel(label_text);
        init();
    }

    /**
     * sets width (columns) of textfield
     * @param columns
     */
    public void set_textfield_width(int columns) {
        text.setColumns(columns);
    }

    /**
     * initializes layout of label and textfield
     */
    private void init() {
        label.setHorizontalAlignment(JLabel.TRAILING);
        label.setLabelFor(text);
        add(label);
        add(text);
    }

    /**
     * gets text value of textfield
     * @return textfield value
     */
    public String get_text() {
        return text.getText();
    }
    
    /**
     * sets text value for textfield
     * @param value text value to set
     */
    public void set_text(String value) {
        text.setText(value);
    }

    /**
     *  set text value for label
     * @param value name of label
     */
    public void set_label(String value) {
        label.setText(value);
    }
}
