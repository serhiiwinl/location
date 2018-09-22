package testapp.sliubetskyi.location;

import android.app.Application;

import testapp.sliubetskyi.location.components.PersistentStorage;
import testapp.sliubetskyi.location.core.state.AppState;
import testapp.sliubetskyi.location.core.state.ClientContext;

public class App extends Application {
    private ClientContext clientContext;

    @Override
    public void onCreate() {
        super.onCreate();
        PersistentStorage persistentStorage = new PersistentStorage(this);
        clientContext = new ClientContext(
                new ClientContextKey(),
                persistentStorage,
                new AppState(persistentStorage)
        );
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    /**
     * Needs only for prevent {@link ClientContext} init not from {@link App}.
     */
    public class ClientContextKey {
        private ClientContextKey() {}
    }
}
