package ds.view.drawer;

import ds.model.Bacteria;
import ds.model.Mood;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotDrawer extends Drawer {
    public void drawRobot(Graphics2D g, Bacteria bacteria)
    {
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
}
