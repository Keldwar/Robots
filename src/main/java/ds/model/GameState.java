package ds.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int amountOfTargets = 20;
    private Dimension dimension;
    private List<Target> targetList;

    public GameState() {
        this.targetList = new ArrayList<>(amountOfTargets);
        for (int i = 0; i < amountOfTargets; i++)
            targetList.add(new Target((int) (Math.random() * dimension.width), (int) (Math.random() * dimension.height)));
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return this.dimension;
    }
}
