package ds.view.game;

import ds.model.entities.Entity;
import ds.model.GameModel;
import ds.view.drawer.BiomeDrawer;
import ds.view.drawer.Drawer;
import ds.view.drawer.BacteriaDrawer;
import ds.view.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameView extends JPanel {
    private final GameModel gameModel;
    private final Map<Class<? extends Entity>, Drawer> map;

    public GameView(GameModel gameModel) {
        map = new HashMap<>();
        map.put(new BacteriaDrawer().getDrawingType(), new BacteriaDrawer());
        map.put(new TargetDrawer().getDrawingType(), new TargetDrawer());
        map.put(new BiomeDrawer().getDrawingType(), new BiomeDrawer());
        this.gameModel = gameModel;
        setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(400, 400));
        this.setSize(new Dimension(400, 400));
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
    }

    public void updateView() {
        onRedrawEvent();
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        // копирование не помогает
        Map<Class<? extends Entity>, Set<Entity>> entities = gameModel.getEntities();
        if (entities == null) {
            return;
        }
        for (Map.Entry<Class<? extends Entity>, Set<Entity>> entry : entities.entrySet()) {
            for (Entity entity : entry.getValue()) {
                map.get(entry.getKey()).draw(g2d, entity);
            }
        }
    }
}
