package ds.model.entities;

import java.util.Random;

public enum TargetType {
    FOOD(10),
    POISON(-10),
    NONE(10);
    public final int damage;

    TargetType(int damage) {
        this.damage = damage;
    }

    public static TargetType randomTargetType() {
        int pick = new Random().nextInt(TargetType.values().length);
        return TargetType.values()[pick];
    }
}
