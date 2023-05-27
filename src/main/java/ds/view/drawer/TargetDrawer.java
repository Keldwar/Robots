package ds.view.drawer;

import ds.model.entities.Entity;
import ds.model.entities.Target;
import ds.model.entities.TargetType;

import java.awt.*;

public class TargetDrawer extends Drawer {
    @Override
    public void draw(Graphics2D g, Entity entity) {
        Target target = (Target) entity;
        if (target.getType() == TargetType.FOOD) {
            g.setColor(Color.GREEN);
        } else if (target.getType() == TargetType.POISON) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        fillOval(g, target.getX(), target.getY(), 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, target.getX(), target.getY(), 5, 5);
    }

    @Override
    public Class<? extends Entity> getDrawingType() {
        return Target.class;
    }
}
