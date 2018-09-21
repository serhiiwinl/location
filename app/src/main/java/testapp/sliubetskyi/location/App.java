package testapp.sliubetskyi.location;

import android.app.Application;

import testapp.sliubetskyi.location.core.ClientContext;

public class App extends Application {
    private ClientContext clientContext;

    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        clientContext = new ClientContext();
    }
}
