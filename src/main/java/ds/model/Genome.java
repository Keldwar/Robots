package ds.model;

import ds.model.commands.*;

import java.lang.Math;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Genome {
    private static final int GENES_LIMIT = 64;
    private final int[] genes;
    private int numberOfGen;
    public static final Map<Integer, CommandEnum> COMMAND_BY_INDEX = initCommandByIndex();
    private static final double MUTATION_PROBABILITY = 0.02;

    public Genome() {
        this.numberOfGen = 0;
        this.genes = new int[GENES_LIMIT];
        for (int i = 0; i < GENES_LIMIT; i++)
            this.genes[i] = (int) (Math.random() * GENES_LIMIT);
    }

    private static Map<Integer, CommandEnum> initCommandByIndex() {
        Map<Integer, CommandEnum> initMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            initMap.put(i, CommandEnum.MOVE_BACTERIA);
            initMap.put(i + 8, CommandEnum.LOOK_AROUND);
        }

        return initMap;
    }

    /**
     * Создаёт новый геном, в котором первые N элементов от первого генома,
     * а GENES_LIMIT - N элементов от второго генома
     *
     * @param genome1 Первый геном
     * @param genome2 Второй геном
     * @return Новый геном
     */
    public static Genome combineGenomes(Genome genome1, Genome genome2) {
        int midpoint = new Random().nextInt(GENES_LIMIT);
        Genome genome = new Genome();
        for (int i = 0; i < genome.genes.length; i++) {
            if (i > midpoint) {
                genome.genes[i] = genome1.genes[i];
            } else {
                genome.genes[i] = genome2.genes[i];
            }
        }
        return genome;
    }

    /**
     * Меняет каждый ген на случайный с вероятностью {@link #MUTATION_PROBABILITY}
     */
    public void mutateGenome() {
        Random random = new Random();
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() < MUTATION_PROBABILITY) {
                genes[i] = random.nextInt(GENES_LIMIT);
            }
        }
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
