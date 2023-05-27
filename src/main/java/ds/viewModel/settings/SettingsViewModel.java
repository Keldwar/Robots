package ds.viewModel.settings;

import ds.bus.GameAction;
import ds.bus.GameEvent;
import ds.bus.GameEventBus;
import ds.bus.GameEventType;
import ds.log.Logger;
import ds.model.GameModel;
import ds.view.settings.SettingsView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.awt.event.*;
import java.util.Objects;

public class SettingsViewModel {
    private final GameEventBus gameEventBus;
    private final GameModel gameModel;
    private final SettingsView settingsView;
    private final CompositeDisposable disposable = new CompositeDisposable();


    private final ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), GameAction.START.name())) {
                gameEventBus.sendData(GameEvent.getEventWithoutData(GameEventType.SET_SETTINGS));
            } else if (Objects.equals(e.getActionCommand(), GameAction.DEFAULT.name())) {
                gameEventBus.sendData(GameEvent.getEventWithoutData(GameEventType.SET_DEFAULT_SETTINGS));
            }
        }
    };

    public SettingsViewModel(GameModel gameModel, GameEventBus gameEventBus) {
        this.gameModel = gameModel;
        this.gameEventBus = gameEventBus;
        this.settingsView = new SettingsView(this.listener);
        initGameEventListeners();
    }

    private void initGameEventListeners() {
        disposable.add(
                gameEventBus
                        .getData()
                        .observeOn(Schedulers.computation())
                        .subscribe(this::onGameEventReceived)
        );
    }

    private void onGameEventReceived(GameEvent gameEvent) {
        Logger.debug("Received event: " + gameEvent.type().name());
        switch (gameEvent.type()) {
            case SET_SETTINGS -> gameModel.setSettings(settingsView.getSettings());
            case SET_DEFAULT_SETTINGS -> {
                settingsView.setDefaultSettings();
                gameModel.setSettings(settingsView.getSettings());
            }
            case STATISTICS -> settingsView.setStatistics(gameModel.getStatistics());
        }

    }

    public SettingsView getSettingsView() {
        return settingsView;
    }
}
