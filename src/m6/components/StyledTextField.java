package m6.components;

import javax.swing.*;
import java.awt.*;

public class StyledTextField extends JTextField {

    public StyledTextField() {
        super();
        initStyles();
    }

    public StyledTextField(String s) {
        super(s);
        initStyles();
    }

    private void initStyles() {
        setFont(new Font("Calibri", Font.PLAIN, 14));
    }

}
