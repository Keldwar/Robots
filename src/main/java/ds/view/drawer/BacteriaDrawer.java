package ds.view.drawer;

import ds.model.Bacteria;
import ds.model.Entity;
import ds.model.Mood;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BacteriaDrawer extends Drawer {
    @Override
    public void draw(Graphics2D g, Entity entity) {
        Bacteria bacteria = (Bacteria) entity;
        AffineTransform oldTransform = g.getTransform();
        int bacteriaCenterX = (int) (Math.round(bacteria.getPositionX()));
        int bacteriaCenterY = (int) (Math.round(bacteria.getPositionY()));
        AffineTransform l = new AffineTransform(oldTransform);
        AffineTransform t = AffineTransform.getRotateInstance(bacteria.getBacteriaDirection(), bacteriaCenterX, bacteriaCenterY);
        l.concatenate(t);

        g.setTransform(l);

        g.setColor(bacteria.getMood().getColor());
        if (bacteria.getMood().equals(Mood.HUNGRY)) {
            fillOval(g, bacteriaCenterX, bacteriaCenterY, 30, 10);
            g.setColor(Color.BLACK);
            drawOval(g, bacteriaCenterX, bacteriaCenterY, 30, 10);
            g.setColor(Color.WHITE);
        } else {
            fillOval(g, bacteriaCenterX, bacteriaCenterY, 30, 15);
            g.setColor(Color.BLACK);
            drawOval(g, bacteriaCenterX, bacteriaCenterY, 30, 15);
            g.setColor(Color.WHITE);
        }

        fillOval(g, bacteriaCenterX + 10, bacteriaCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, bacteriaCenterX + 10, bacteriaCenterY, 5, 5);
        g.setTransform(oldTransform);
    }

    @Override
    public Class<?> getDrawingType() {
        return Bacteria.class;
    }
}
