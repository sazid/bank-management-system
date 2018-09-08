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

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void initStyles() {
        setFont(new Font("Calibri", Font.BOLD, 14));
    }


}
