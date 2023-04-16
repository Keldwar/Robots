package ds.model;

public enum TargetType {
    FOOD(10),
    POISON(10);
    public final int damage;

    TargetType(int damage) {
        this.damage = damage;
    }
}
