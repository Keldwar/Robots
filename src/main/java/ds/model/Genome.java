package ds.model;

import ds.model.commands.*;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Genome {
    private static final int genesLimit = 64;
    private final int[] genes;
    private int numberOfGen;
    public static final Map<Integer, CommandEnum> COMMAND_BY_INDEX = initCommandByIndex();
    private static final int MUTATION_PROBABILITY = 2;

    public Genome() {
        this.numberOfGen = 0;
        this.genes = new int[genesLimit];
        for (int i = 0; i < genesLimit; i++)
            this.genes[i] = (int) (Math.random() * genesLimit);
    }

    private static Map<Integer, CommandEnum> initCommandByIndex() {
        Map<Integer, CommandEnum> initMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            initMap.put(i, CommandEnum.MOVE_BACTERIA);
            initMap.put(i + 8, CommandEnum.LOOK_AROUND);
        }

        return initMap;
    }

    public static Genome combineGenomes(Genome genome1, Genome genome2) {
        // TODO
        return null;
    }

    public void mutateGenome() {
        // TODO
    }

    public Command getNextCommand() {
        try {
            int currentNum = numberOfGen;
            numberOfGen = (numberOfGen + 1) % 16;//genes[commandByIndex.get(currentNum).step];
            return CommandFactory.getCommand(COMMAND_BY_INDEX.get(currentNum));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
