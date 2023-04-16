package ds.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface Entity extends PropertyChangeListener {
    void update(GameState gameState);

    void onStart(PropertyChangeSupport publisher);

    void onFinish(PropertyChangeSupport publisher);

    boolean isAlive();
}
