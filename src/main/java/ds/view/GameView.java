package ds.view;

import ds.model.GameModel;
import ds.model.Robot;
import ds.view.drawer.RobotDrawer;
import ds.view.drawer.TargetDrawer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JPanel {
    private final GameModel gameModel;
    RobotDrawer robotDrawer;
    TargetDrawer targetDrawer;

    public GameView(GameModel gameModel) {
        robotDrawer = new RobotDrawer();
        targetDrawer = new TargetDrawer();
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
        ArrayList<Robot> robots = (ArrayList<Robot>) gameModel.getRobots();
        for (Robot robot : robots) {
            robotDrawer.drawRobot(g2d, robot);
            targetDrawer.drawTarget(g2d, robot.getTarget());
        }
    }
}
