package m6.components;

import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {

    public StyledButton() {
        super();
        initStyles();
    }

    public StyledButton(String s) {
        super(s);
        initStyles();
    }

    private void initStyles() {
        setFont(new Font("Calibri", Font.PLAIN, 14));
        setBackground(new Color(0x2dce98));
        setForeground(Color.white);
        setUI(new StyledButtonUI());
    }

}
