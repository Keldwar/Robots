package ds.model;

import java.awt.*;
import java.util.List;
import java.util.Random;

public enum Mood {
    CALM(0, Color.BLUE),
    EVIL(1, Color.RED),
    ENAMORED(2, Color.MAGENTA),
    HUNGRY(3, Color.YELLOW),
    DEAD(4, Color.BLACK);
    private final int state;
    private final Color color;
    private static final Random RANDOM = new Random();
    private static final List<Mood> values = List.of(values());

    public static Mood randomMood() {
        return values.get(RANDOM.nextInt(values().length - 2));
    }

    Mood(int state, Color color) {
        this.state = state;
        this.color = color;
    }


    public Color getColor() {
        return color;
    }
}
