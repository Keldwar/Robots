package ds.view;

import ds.log.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuCreator {
    private final JFrame frame;

    public MenuCreator(JFrame frame) {
        this.frame = frame;
    }

    private JMenu createMenu(String nameOfMenu, String description, int mnemonic) {
        JMenu lookAndFeelMenu = new JMenu(nameOfMenu);
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(description);
        return lookAndFeelMenu;
    }

    private JMenuItem createMenuItem(String name, ActionListener l) {
        JMenuItem systemLookAndFeel = new JMenuItem(name, KeyEvent.VK_S);
        systemLookAndFeel.addActionListener(l);

        return systemLookAndFeel;
    }

    public JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = createMenu("Режим отображения", "Управление режимом отображения приложения", KeyEvent.VK_V);
        JMenuItem systemLookAndFeel = createMenuItem("Системная схема", (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(systemLookAndFeel);

        JMenuItem crossplatformLookAndFeel = createMenuItem("Универсальная схема", (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            frame.invalidate();
        });
        lookAndFeelMenu.add(crossplatformLookAndFeel);

        JMenu testMenu = createMenu("Тесты", "Тестовые команды", KeyEvent.VK_S);

        JMenuItem addLogMessageItem = createMenuItem("Сообщение в лог", (event) -> Logger.debug("Новая строка"));
        testMenu.add(addLogMessageItem);

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            Logger.error("Error with setLookAndFell");
        }
    }
}
