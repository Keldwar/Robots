package ds.model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel {
    private List<Bacteria> bacterias;
    private PropertyChangeSupport support;
    private final java.util.Timer timer = initTimer();
    public GameModel() {
        this.bacterias = initStateOfBacterias(20);
        this.support = new PropertyChangeSupport(this);

        for (Bacteria bacteria : bacterias)
            addPropertyChangeListener(bacteria);
        // переделать
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                for (Bacteria bacteria : bacterias) {
                    bacteria.changeSatiety(-10);
                }
            }
        }, 0, 3000);
    }

    private static java.util.Timer initTimer() {
        return new Timer("satiety generator", true);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setDimension(Dimension dimension) {
        for (Bacteria bacteria : bacterias)
            bacteria.setDimension(dimension);
    }

    public Dimension getDimension() {
        return bacterias.get(0).getDimension();
    }

    public void updateModel() {
        for (Bacteria bacteria : bacterias) {
            bacteria.update();
        }
    }

    public List<Bacteria> getRobots() {
        return bacterias;
    }

    public void setTarget(Point point) {
        support.firePropertyChange("new point", null, point);
    }

    public List<Bacteria> initStateOfBacterias(int amount) {

        List<Bacteria> bacteriaList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            bacteriaList.add(new Bacteria(Math.random() * 400, Math.random() * 400));
            bacteriaList.get(i).setTarget(new Point((int) (Math.random() * 400), (int) (Math.random() * 400)));
        }
        return bacteriaList;
    }
}
