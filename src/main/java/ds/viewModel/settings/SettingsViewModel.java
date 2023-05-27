package ds.viewModel.settings;

import ds.log.Logger;
import ds.model.GameModel;
import ds.view.settings.SettingsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SettingsViewModel {
    private final GameModel gameModel;
    private final SettingsView settingsView;
    private final ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), "Start")) {
                gameModel.setSettings(settingsView.getSettings());
                Logger.debug("StartButton clicked");
            }
        }
    };

    public SettingsViewModel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.settingsView = new SettingsView(this.listener);
    }

    public SettingsView getSettingsView() {
        return settingsView;
    }


}
