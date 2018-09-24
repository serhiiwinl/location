package testapp.sliubetskyi.location.core.model;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.components.LocationManager;
import testapp.sliubetskyi.location.android.components.NotificationDisplayer;
import testapp.sliubetskyi.location.android.components.PermissionManager;
import testapp.sliubetskyi.location.android.components.PersistentStorage;
import testapp.sliubetskyi.location.android.components.ResourcesIdHolder;
import testapp.sliubetskyi.location.core.model.modules.IAppState;
import testapp.sliubetskyi.location.core.model.modules.IClientContext;
import testapp.sliubetskyi.location.core.model.modules.ILocationManager;
import testapp.sliubetskyi.location.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;
import testapp.sliubetskyi.location.core.model.modules.IResourcesProvider;

/**
 * Links all modules and components together and provide access for all needed apis.
 */
public final class ClientContext implements IClientContext {

    private final IAppState appState;
    private final IPersistentData persistentStorage;
    private final ILocationManager locationManager;
    private final IPermissionsManager permissionsManager;
    private final ResourcesIdHolder resourcesProvider;
    private final NotificationDisplayer notificationDisplayer;

    @SuppressWarnings("unused")
    public ClientContext(App.ClientContextKey clientContextKey, App app) {
        persistentStorage = new PersistentStorage(app);
        permissionsManager = new PermissionManager(app);
        locationManager = new LocationManager(app, persistentStorage.getUserLocation());
        resourcesProvider = new ResourcesIdHolder(app);
        notificationDisplayer = new NotificationDisplayer(app);
        appState = new AppState(app, persistentStorage, permissionsManager);
    }

    @Override
    public IPersistentData getPersistentStorage() {
        return persistentStorage;
    }

    @Override
    public ILocationManager getLocationManager() {
        return locationManager;
    }

    @Override
    public IAppState getAppState() {
        return appState;
    }

    @Override
    public IPermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    @Override
    public IResourcesProvider getResProvider() {
        return resourcesProvider;
    }

    @Override
    public NotificationDisplayer getNotificationDisplayer() {
        return notificationDisplayer;
    }
}
