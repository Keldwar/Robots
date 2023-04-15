package ds.model;

import ds.model.commands.Command;
import ds.model.commands.LookAroundCommand;
import ds.model.commands.MoveBacteriaCommand;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

public class Genome {
    private int[] genes;
    private int numberOfGen;
    public static final Map<Integer, Pair> commandByIndex = initCommandByIndex();

    public Genome() {
        this.numberOfGen = 0;
        this.genes = new int[64];
        for (int i = 0; i < 64; i++)
            this.genes[i] = (int) (Math.random() * 64);
    }

    public static Genome combineGenomes(Genome genome1, Genome genome2) {
        // TODO
        return null;
    }

    private static Map<Integer, Pair> initCommandByIndex() {
        Map<Integer, Pair> initMap = new HashMap<>();
        initMap.put(0, new Pair(MoveBacteriaCommand.class, 1));
        initMap.put(1, new Pair(LookAroundCommand.class, 1));

        return initMap;
    }

    public Command getNextCommand() {
        try {
            int currentNum = numberOfGen;
            numberOfGen = (numberOfGen + 1) % 2;//genes[commandByIndex.get(currentNum).step];
            return commandByIndex.get(currentNum).command.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static class Pair {
        Class<? extends Command> command;
        int step;

        public Pair(Class<? extends Command> command, int step) {
            this.command = command;
            this.step = step;
        }
    }
}
