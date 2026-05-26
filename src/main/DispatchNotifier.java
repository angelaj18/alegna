import java.util.ArrayList;
import java.util.List;

public class DispatchNotifier {
    private final List<GameEventListener> listeners = new ArrayList<>();

    public void register(GameEventListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    public void notifyListeners(DispatchEvent event) {
        for (GameEventListener listener : listeners) {
            listener.onDispatchEvent(event);
        }
    }
}
