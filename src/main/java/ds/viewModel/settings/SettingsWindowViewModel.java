package ds.viewModel.settings;

import ds.model.GameModel;
import ds.view.settings.SettingsWindow;

public class SettingsWindowViewModel {
    private final SettingsViewModel settingsViewModel;
    private final SettingsWindow settingsWindow;

    public SettingsWindowViewModel(GameModel gameModel) {
        this.settingsViewModel = new SettingsViewModel(gameModel);
        this.settingsWindow = new SettingsWindow(this.settingsViewModel.getSettingsView());
    }

    public SettingsWindow getSettingsWindow() {
        return settingsWindow;
    }
}
