package ds.bus;

import io.reactivex.rxjava3.annotations.Nullable;

public record GameEvent<T>(
        GameEventType type,
        @Nullable T data
) {
    public static GameEvent getEventWithoutData(GameEventType type) {
        return new GameEvent(type, null);
    }
}
