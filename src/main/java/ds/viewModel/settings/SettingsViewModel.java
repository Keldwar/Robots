package ds.viewModel.settings;

import ds.model.GameModel;
import ds.view.settings.SettingsView;

public class SettingsViewModel {
    private final GameModel gameModel;
    private final SettingsView settingsView;

    public SettingsViewModel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.settingsView = new SettingsView(gameModel);
    }

    public SettingsView getSettingsView() {
        return settingsView;
    }
}
