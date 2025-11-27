package edu.univ.erp.ui;

import javax.swing.border.AbstractBorder;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ArcBorder extends AbstractBorder {
    private final Color borderColor;
    private final int thickness;
    private final int arcSize;

    public ArcBorder(Color borderColor, int thickness, int arcSize) {
        this.borderColor = borderColor;
        this.thickness = thickness;
        this.arcSize = arcSize;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(thickness));

        // Draw the rounded rectangle
        g2d.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0,
                width - thickness, height - thickness,
                arcSize, arcSize));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }
}
