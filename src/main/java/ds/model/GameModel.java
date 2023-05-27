package ds.model;


import ds.bus.GameEventBus;
import ds.log.Logger;
import ds.model.entities.Bacteria;
import ds.model.entities.Biome;
import ds.model.entities.Entity;
import ds.model.entities.Target;
import ds.view.game.Statistics;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

public class GameModel {
    private int minAmountBacterias = 18;
    private int amountOfBacterias = 20;
    private int amountOfTargets = 20;
    private int amountOfBiomes = 5;
    private final int maxDiamOfBiome = 300;
    private final PropertyChangeSupport support;
    private volatile GameState gameState;
    private final GameEventBus gameEventBus;

    public GameModel(GameEventBus gameEventBus) {
        this.support = new PropertyChangeSupport(this);
        this.gameEventBus = gameEventBus;
        Timer timer = initTimer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Logger.debug("Satiety timer worked");
                support.firePropertyChange("change satiety", null, -10);
            }
        }, 0, 3000);
    }

    private Map<Class<? extends Entity>, Set<Entity>> initEntitiesByClass() {
        Map<Class<? extends Entity>, Set<Entity>> initEntities = new LinkedHashMap<>();
        initEntities.put(Bacteria.class, initStateOfBacterias(amountOfBacterias));
        initEntities.put(Target.class, initStateOfTargets(amountOfTargets));
        initEntities.put(Biome.class, initBiomes(amountOfBiomes));
        return initEntities;
    }

    private Set<Entity> initBiomes(int amountOfBiomes) {
        Set<Entity> entitySet = new LinkedHashSet<>();
        for (int i = 0; i < amountOfBiomes; i++) {
            entitySet.add(new Biome((int) (Math.random() * maxDiamOfBiome + 1), (int) (Math.random() * getDimension().width),
                    (int) (Math.random() * getDimension().height)));
        }
        return entitySet;
    }

    private static Timer initTimer() {
        return new Timer("satiety generator", true);
    }

    public void setDimension(Dimension dimension) {
        if (gameState == null) {
            return;
        }
        gameState.setDimension(dimension);
        support.firePropertyChange("set dimension", null, dimension);
    }

    public Dimension getDimension() {
        if (gameState != null) {
            return gameState.getDimension();
        } else {
            return new Dimension(400, 400);
        }
    }

    public void updateModel() {
        if (gameState == null) {
            return;
        }
        Map<Class<? extends Entity>, Set<Entity>> entitiesByClass = gameState.getEntitiesByClass();
        for (Map.Entry<Class<? extends Entity>, Set<Entity>> entry : entitiesByClass.entrySet()) {
            for (Entity entity : entry.getValue()) {
                entity.update(gameState);
                if (!entity.isAlive())
                    entity.onFinish(support);
            }
            entry.getValue().removeIf(entity -> !entity.isAlive());
        }

        if (entitiesByClass.get(Bacteria.class).size() <= minAmountBacterias) {
            startNewGeneration();
        }
        if (entitiesByClass.get(Target.class).size() <= amountOfTargets) {
            generateTargets(entitiesByClass);
        }
    }

    private void generateTargets(Map<Class<? extends Entity>, Set<Entity>> entitiesByClass) {
        entitiesByClass.get(Target.class).add(new Target((int) (Math.random() * getDimension().width),
                (int) (Math.random() * getDimension().height)));
    }

    private void startNewGeneration() {
        Map<Class<? extends Entity>, Set<Entity>> entitiesByClass = gameState.getEntitiesByClass();
        List<Entity> entities = entitiesByClass.get(Bacteria.class).stream().toList();
        entitiesByClass.get(Bacteria.class).clear();

        for (int i = 0; i <= minAmountBacterias; i++) {
            int j = i % entities.size();

            Bacteria bacteria1 = (Bacteria) entities.get(j);
            Bacteria bacteria2 = (Bacteria) entities.get((j + 1) % entities.size());

            Genome genome1 = Genome.combineGenomes(bacteria1.getGenome(), bacteria2.getGenome());
            genome1.mutateGenome();
            Bacteria newBacteria1 = new Bacteria(genome1, Math.random() * getDimension().width,
                    Math.random() * getDimension().height);
            newBacteria1.onStart(support);
            entitiesByClass.get(Bacteria.class).add(newBacteria1);
        }

        for (Entity entity : entities) {
            entity.onFinish(support);
        }
    }

    public Map<Class<? extends Entity>, Set<Entity>> getEntities() {
        if (gameState == null) {
            return null;
        }
        return gameState.getEntitiesByClass();
    }

    public void setTarget(Point point) {
        gameState.getEntitiesByClass().get(Target.class).clear();
        gameState.getEntitiesByClass().get(Target.class).add(new Target(point.x, point.y));
        support.firePropertyChange("new point", null, point);
    }

    public Set<Entity> initStateOfBacterias(int amount) {
        Set<Entity> entitySet = new LinkedHashSet<>();
        for (int i = 0; i < amount; i++) {
            Bacteria bacteria = new Bacteria(Math.random() * getDimension().width,
                    Math.random() * getDimension().height);
            bacteria.onStart(support);
            entitySet.add(bacteria);
        }
        return entitySet;
    }

    public Set<Entity> initStateOfTargets(int amount) {
        Set<Entity> entitySet = new LinkedHashSet<>();
        for (int i = 0; i < amount; i++) {
            entitySet.add(new Target((int) (Math.random() * getDimension().width),
                    (int) (Math.random() * getDimension().height)));
        }
        return entitySet;
    }


    public void setSettings(Settings settings) {
        amountOfBacterias = settings.amountOfBacteria();
        amountOfTargets = settings.amountOfTargets();
        minAmountBacterias = settings.minBacteria();
        amountOfBiomes = settings.amountOfBiomes();
        this.gameState = new GameState(initEntitiesByClass());
    }

    public Statistics getStatistics() {
        return new Statistics(gameState.getEntitiesByClass().get(Bacteria.class).size());
    }
}
