package ds.model;


import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

public class GameModel {
    private static final int MIN_AMOUNT_BACTERIAS = 2;
    private static final int AMOUNT_OF_BACTERIAS = 3;
    private static final int AMOUNT_OF_TARGETS = 3;
    private final Map<Class<? extends Entity>, Set<Entity>> entitiesByClass;
    private final PropertyChangeSupport support;
    private final GameState gameState;

    public GameModel() {
        this.support = new PropertyChangeSupport(this);
        this.entitiesByClass = initEntitiesByClass();
        this.gameState = new GameState();

        Timer timer = initTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
                support.firePropertyChange("change satiety", null, -10);
            }
        }, 0, 3000);
    }

    private Map<Class<? extends Entity>, Set<Entity>> initEntitiesByClass() {
        Map<Class<? extends Entity>, Set<Entity>> initEntities = new LinkedHashMap<>();
        initEntities.put(Bacteria.class, initStateOfBacterias(AMOUNT_OF_BACTERIAS));
        initEntities.put(Target.class, initStateOfTargets(AMOUNT_OF_TARGETS));
        return initEntities;
    }

    private static java.util.Timer initTimer() {
        return new Timer("satiety generator", true);
    }

    public void setDimension(Dimension dimension) {
        gameState.setDimension(dimension);
        support.firePropertyChange("set dimension", null, dimension);
    }

    public Dimension getDimension() {
        return new Dimension(400, 400);
    }

    public void updateModel() {
        for (Map.Entry<Class<? extends Entity>, Set<Entity>> entry : this.entitiesByClass.entrySet()) {
            for (Entity entity : entry.getValue()) {
                entity.update(gameState);
                if (!entity.isAlive())
                    entity.onFinish(support);
            }
            entry.getValue().removeIf(entity -> !entity.isAlive());
        }

        if (entitiesByClass.get(Bacteria.class).size() <= MIN_AMOUNT_BACTERIAS) {
            startNewGeneration();
        }
    }

    private void startNewGeneration() {
        List<Entity> entities = entitiesByClass.get(Bacteria.class).stream().toList();
        entitiesByClass.get(Bacteria.class).clear();

        for (int i = 0; i <= MIN_AMOUNT_BACTERIAS; i++) {
            int j = i % entities.size();

            Bacteria bacteria1 = (Bacteria) entities.get(j);
            Bacteria bacteria2 = (Bacteria) entities.get((j + 1) % entities.size());

            Genome genome1 = Genome.combineGenomes(bacteria1.getGenome(), bacteria2.getGenome());
            genome1.mutateGenome();
            Bacteria newBacteria1 = new Bacteria(genome1);
            newBacteria1.onStart(support);
            entitiesByClass.get(Bacteria.class).add(newBacteria1);
        }

        for (Entity entity : entities) {
            entity.onFinish(support);
        }
    }

    public Map<Class<? extends Entity>, Set<Entity>> getEntities() {
        return entitiesByClass;
    }

    public void setTarget(Point point) {
        support.firePropertyChange("new point", null, point);
    }

    public Set<Entity> initStateOfBacterias(int amount) {
        Set<Entity> entitySet = new LinkedHashSet<>();
        for (int i = 0; i < amount; i++) {
            Bacteria bacteria = new Bacteria(Math.random() * 400, Math.random() * 400);
            bacteria.setTarget(new Point((int) (Math.random() * 400), (int) (Math.random() * 400)));
            bacteria.onStart(support);
            entitySet.add(bacteria);
        }
        return entitySet;
    }

    public Set<Entity> initStateOfTargets(int amount) {
        Set<Entity> entitySet = new LinkedHashSet<>();
        for (int i = 0; i < amount; i++) {
            entitySet.add(new Target((int) (Math.random() * getDimension().width), (int) (Math.random() * getDimension().height)));
        }
        return entitySet;
    }
}
