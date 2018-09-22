package testapp.sliubetskyi.location.core.state;

import testapp.sliubetskyi.location.App;
import testapp.sliubetskyi.location.core.data.IPersistentData;

/**
 * Provides access to all app components.
 */
public final class ClientContext implements IClientContext {

    private final AppState appState;

    private final IPersistentData persistentStorage;

    @SuppressWarnings("unused")
    public ClientContext(App.ClientContextKey clientContextKey, IPersistentData persistentStorage, AppState appState) {
        this.persistentStorage = persistentStorage;
        this.appState = appState;
    }

    public IPersistentData getPersistentStorage() {
        return persistentStorage;
    }

    public AppState getAppState() {
        return appState;
    }
}
