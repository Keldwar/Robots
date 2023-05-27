package ds.view.drawer;

import ds.model.entities.*;

import java.awt.*;

public class BiomeDrawer extends Drawer{
    @Override
    public void draw(Graphics2D g, Entity entity) {
        Biome biome = (Biome) entity;
        if (biome.getType() == BiomeType.GREEN) {
            g.setColor(new Color(145, 255, 165, 40));
        } else if (biome.getType() == BiomeType.RED) {
            g.setColor(new Color(255, 145, 145, 40));
        } else {
            g.setColor(new Color(146, 145, 255, 40));
        }
        fillOval(g, (int) biome.getX(), (int) biome.getY(), biome.getDiam(), biome.getDiam());

        drawOval(g, (int) biome.getX(), (int) biome.getY(), biome.getDiam(), biome.getDiam());
    }

    @Override
    public Class<? extends Entity> getDrawingType() {
        return Biome.class;
    }
}
