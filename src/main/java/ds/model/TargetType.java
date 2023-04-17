package ds.model;

import java.util.Random;

public enum TargetType {
    FOOD(10),
    POISON(-10),
    NONE(0);
    public final int damage;

    TargetType(int damage) {
        this.damage = damage;
    }

    public static TargetType randomTargetType() {
        int pick = new Random().nextInt(TargetType.values().length);
        return TargetType.values()[pick];
    }
}
