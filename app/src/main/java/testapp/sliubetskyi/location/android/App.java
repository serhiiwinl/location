package testapp.sliubetskyi.location.android;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import testapp.sliubetskyi.location.android.components.ClientContext;
import testapp.sliubetskyi.location.android.components.NotificationHelper;

public class App extends Application implements Application.ActivityLifecycleCallbacks {
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
        registerActivityLifecycleCallbacks(this);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        clientContext.getAppState().appIsVisible(true);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        clientContext.getAppState().appIsVisible(true);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        clientContext.getAppState().appIsVisible(true);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        clientContext.getAppState().appIsVisible(true);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        clientContext.getAppState().appIsVisible(true);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        clientContext.getAppState().appIsVisible(false);
    }

    /**
     * Needs only for prevent {@link ClientContext} creating not from {@link App}.
     */
    public class ClientContextKey {
        private ClientContextKey() {}
    }
}
