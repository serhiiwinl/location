package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.android.components.NotificationHelper;

/**
 * Impl it if you wish to provide access to all app components.
 */
public interface IClientContext {
    IPersistentData getPersistentStorage();
    ILocationManager getLocationManager();
    IAppState getAppState();
    IPermissionsManager getPermissionsManager();
    IResourcesProvider getResProvider();
    NotificationHelper getNotificationHelper();
}
