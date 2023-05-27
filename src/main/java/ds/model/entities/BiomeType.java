package ds.model.entities;

import java.util.Random;

public enum BiomeType {
    RED,
    GREEN,
    BLUE;
    public static BiomeType randomBiomeType() {
        int pick = new Random().nextInt(BiomeType.values().length);
        return BiomeType.values()[pick];
    }
}
