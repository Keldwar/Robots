package ds.view.drawer;

import ds.model.Bacteria;
import ds.model.Entity;
import ds.model.Mood;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotDrawer extends Drawer {
    @Override
    public void draw(Graphics2D g, Entity entity)
    {
        Bacteria bacteria = (Bacteria) entity;
        int robotCenterX = (int) Math.round(bacteria.getPositionX());
        int robotCenterY = (int) Math.round(bacteria.getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(bacteria.getRobotDirection(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(bacteria.getMood().getColor());
        if (bacteria.getMood().equals(Mood.HUNGRY)) {
            fillOval(g, robotCenterX, robotCenterY, 30, 10);
            g.setColor(Color.BLACK);
            drawOval(g, robotCenterX, robotCenterY, 30, 10);
            g.setColor(Color.WHITE);
        }
        else {
            fillOval(g, robotCenterX, robotCenterY, 30, 15);
            g.setColor(Color.BLACK);
            drawOval(g, robotCenterX, robotCenterY, 30, 15);
            g.setColor(Color.WHITE);
        }

        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }

    @Override
    public Class<?> getDrawingType() {
        return Bacteria.class;
    }
}
