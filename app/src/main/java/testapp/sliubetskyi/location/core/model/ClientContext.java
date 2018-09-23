package testapp.sliubetskyi.location.core.model;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.components.LocationManager;
import testapp.sliubetskyi.location.android.components.PermissionManager;
import testapp.sliubetskyi.location.android.components.PersistentStorage;
import testapp.sliubetskyi.location.core.model.modules.IClientContext;
import testapp.sliubetskyi.location.core.model.modules.ILocationManager;
import testapp.sliubetskyi.location.core.model.modules.IPermissions;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;

/**
 * Links all modules and components together and provide access for all needed apis.
 */
public final class ClientContext implements IClientContext {

    private final AppState appState;
    private final IPersistentData persistentStorage;
    private final ILocationManager locationManager;
    private final IPermissions permissions;

    @SuppressWarnings("unused")
    public ClientContext(App.ClientContextKey clientContextKey, App app) {
        persistentStorage = new PersistentStorage(app);
        appState = new AppState(persistentStorage);
        permissions = new PermissionManager(app);
        locationManager = new LocationManager(app, persistentStorage.getUserLocation());
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
    public AppState getAppState() {
        return appState;
    }

    @Override
    public IPermissions getPermissions() {
        return permissions;
    }
}
