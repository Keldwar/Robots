package ds.model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private List<Robot> robots;
    private PropertyChangeSupport support;

    public GameModel() {
        this.robots = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);

        robots.add(new Robot());
        for (Robot robot : robots)
            addPropertyChangeListener(robot);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setDimension(Dimension dimension) {
        for (Robot robot : robots)
            robot.setDimension(dimension);
    }

    public Dimension getDimension() {
        return robots.get(0).getDimension();
    }

    public void updateModel() {
        for (Robot robot : robots)
            robot.update();
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setTarget(Point point) {
        support.firePropertyChange("new point", null, point);
    }
}
