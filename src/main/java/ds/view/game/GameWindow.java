package ds.view.game;


import javax.swing.*;
import java.awt.*;

public class GameWindow extends JInternalFrame {

    public GameWindow(GameView gameView) {
        super("Игровое поле", true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(gameView, BorderLayout.CENTER);
        getContentPane().add(panel);
        setLocation(420, 10);
        pack();
    }

}