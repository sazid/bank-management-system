package m6.components;

import javax.swing.*;
import java.awt.*;

public class StyledLabel extends JLabel {

    public StyledLabel() {
        super();
        initStyles();
    }

    public StyledLabel(String s) {
        super(s);
        initStyles();
    }

    private void initStyles() {
        setFont(new Font("Calibri", Font.PLAIN, 14));
    }


}
