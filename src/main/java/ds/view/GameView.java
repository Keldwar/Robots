package ds.view;

import ds.model.Entity;
import ds.model.GameModel;
import ds.model.Bacteria;
import ds.model.Target;
import ds.view.drawer.Drawer;
import ds.view.drawer.RobotDrawer;
import ds.view.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GameView extends JPanel {
    private final GameModel gameModel;
    private Map<Class<?>, Drawer> map;
    public GameView(GameModel gameModel) {
        map = new HashMap<>();
        map.put(new RobotDrawer().getDrawingType(), new RobotDrawer());
        map.put(new TargetDrawer().getDrawingType(), new TargetDrawer());
        this.gameModel = gameModel;
        setDoubleBuffered(true);
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
        ArrayList<Entity> entities = (ArrayList<Entity>) gameModel.getEntities();
        for (Entity entity : entities) {
            map.get(entity.getClass()).draw(g2d, entity);
            map.get(Target.class).draw(g2d, entity);
        }
    }
}
