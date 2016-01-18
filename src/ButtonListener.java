import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/**
 *
 * @author Johan Eliasson (johane@cs.umu.se)
 * @version 10 nov 2013
 */
class ButtonListener implements ActionListener {

    private final JTextField textField;
    private Color textColor;
    
    public ButtonListener(JTextField textField, Color c) {
        this.textField = textField;
        textColor = c;
    }
    
    public void actionPerformed(ActionEvent e) {
        textField.setForeground(textColor);
    }
}