package m6.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StyledPasswordField extends JPasswordField {
    private Shape shape;

    public StyledPasswordField() {
        super();
        setOpaque(false);
        initStyles();
    }

    public StyledPasswordField(String s) {
        super(s);
        setOpaque(false);
        initStyles();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }
        return shape.contains(x, y);
    }

    private void initStyles() {
        setFont(new Font("Calibri", Font.BOLD, 14));
        setBorder(BorderFactory.createCompoundBorder(getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

}
