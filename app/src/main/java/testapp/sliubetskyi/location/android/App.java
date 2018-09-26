package testapp.sliubetskyi.location.android;

import android.app.Application;

import testapp.sliubetskyi.location.android.components.NotificationHelper;

public class App extends Application {
    private ClientContext clientContext;

    public NotificationHelper getNotificationHelper() {
        return notificationHelper;
    }

    private NotificationHelper notificationHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        clientContext = new ClientContext(new ClientContextKey(), this);
        notificationHelper = new NotificationHelper(this);
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
