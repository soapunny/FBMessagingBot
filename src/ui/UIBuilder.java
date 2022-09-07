package ui;

import javax.swing.*;
import java.awt.*;

public class UIBuilder {
    public static final Color CUSTOM_GREY = new Color(57,57,57);
    public static final Color CUSTOM_WHITE = new Color(255, 255,255);
    public static final Color CUSTOM_BLUE = new Color(24, 118, 242);
    public static final Font CUSTOM_BASIC_FONT = new Font("Century Gothic", Font.PLAIN, 14);

    public static final Font CUSTOM_BASIC_FONT_BOLD = new Font("Century Gothic", Font.BOLD, 14);
    public static final Font CUSTOM_BIG_FONT = new Font("Century Gothic", Font.PLAIN, 24);

    public static final Font CUSTOM_BIG_FONT_BOLD = new Font("Century Gothic", Font.BOLD, 24);

    public UIBuilder(){

    }

    public JLabel getTitleJLabelField(String title){
        JLabel jLabel = new JLabel(title);
        jLabel.setFont(CUSTOM_BIG_FONT_BOLD);
        jLabel.setForeground(CUSTOM_WHITE);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return jLabel;
    }

    public JButton getBasicJButton(String title){
        JButton jButton = new JButton(title);
        jButton.setBackground(CUSTOM_BLUE);
        jButton.setFont(CUSTOM_BASIC_FONT_BOLD);
        jButton.setForeground(CUSTOM_WHITE);
        jButton.setBorderPainted(false);
        jButton.setFocusPainted(false);
        return jButton;
    }
    public JTextField getBasicJTextField(){
        JTextField jTextField = new JTextField();
        jTextField.setBackground(CUSTOM_GREY);
        jTextField.setFont(CUSTOM_BASIC_FONT);
        jTextField.setForeground(CUSTOM_WHITE);
        jTextField.setBorder(BorderFactory.createLineBorder(CUSTOM_WHITE));
        jTextField.setCaretColor(Color.white);
        return jTextField;
    }
    public JPasswordField getBasicJPasswordField(){
        JPasswordField jPasswordField = new JPasswordField();
        jPasswordField.setBackground(CUSTOM_GREY);
        jPasswordField.setFont(CUSTOM_BASIC_FONT);
        jPasswordField.setForeground(CUSTOM_WHITE);
        jPasswordField.setBorder(BorderFactory.createLineBorder(CUSTOM_WHITE));
        jPasswordField.setCaretColor(Color.white);
        return jPasswordField;
    }
    public JLabel getBasicJLabel(String title){
        JLabel label = new JLabel();
        label.setText(title);
        label.setFont(CUSTOM_BASIC_FONT);
        label.setForeground(CUSTOM_WHITE);
        return label;
    }
}
