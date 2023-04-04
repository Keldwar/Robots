package ds.view.drawer;

import ds.model.Bacteria;
import ds.model.Entity;
import ds.model.Target;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TargetDrawer extends Drawer {
    @Override
    public void draw(Graphics2D g, Entity entity)
    {
        Target target = ((Bacteria)entity).getTarget();
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, target.getX(), target.getY(), 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, target.getX(), target.getY(), 5, 5);
    }

    @Override
    public Class<?> getDrawingType() {
        return Target.class;
    }
}
