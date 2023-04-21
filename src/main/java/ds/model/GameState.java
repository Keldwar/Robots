package ds.model;

import java.awt.*;
import java.util.*;

public class GameState {
    private final int amountOfTargets = 20;
    private Dimension dimension;
    private final Map<Class<? extends Entity>, Set<Entity>> entitiesByClass;

    public GameState() {
        this.dimension = new Dimension(400, 400);
        this.entitiesByClass = new LinkedHashMap<>();
    }

    public GameState(Map<Class<? extends Entity>, Set<Entity>> entitiesByClass) {
        this.entitiesByClass = entitiesByClass;
        this.dimension = new Dimension(400, 400);
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension getDimension() {
        return this.dimension;
    }

    public Map<Class<? extends Entity>, Set<Entity>> getEntitiesByClass() {
        return this.entitiesByClass;
    }
}
