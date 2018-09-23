package testapp.sliubetskyi.location.android;

import android.app.Application;

import testapp.sliubetskyi.location.core.model.ClientContext;

public class App extends Application {
    private ClientContext clientContext;

    @Override
    public void onCreate() {
        super.onCreate();
        clientContext = new ClientContext(new ClientContextKey(), this);
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
