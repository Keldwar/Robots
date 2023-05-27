package ds.bus;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
public class GameEventBus {

    private final PublishSubject<GameEvent> subject = PublishSubject.create();

    public void sendData(GameEvent event) {
        subject.onNext(event);
    }
    public Observable<GameEvent> getData() {
        return subject;
    }
}