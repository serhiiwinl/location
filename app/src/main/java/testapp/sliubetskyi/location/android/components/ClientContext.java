package testapp.sliubetskyi.location.android.components;

import testapp.sliubetskyi.core.model.modules.IAppState;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.model.modules.location.ILocationManager;
import testapp.sliubetskyi.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.core.model.modules.IPersistentData;
import testapp.sliubetskyi.core.model.modules.IResourcesProvider;
import testapp.sliubetskyi.core.model.modules.IUITreadExecutor;
import testapp.sliubetskyi.location.android.App;

/**
 * Links all modules and components together and provide access for all needed apis.
 */
public final class ClientContext implements IClientContext {

    private final IAppState appState;
    private final IPersistentData persistentStorage;
    private final ILocationManager locationManager;
    private final IPermissionsManager permissionsManager;
    private final IResourcesProvider resourcesProvider;
    private final IUITreadExecutor uiTreadExecutor;

    @SuppressWarnings("unused")
    public ClientContext(App.ClientContextKey clientContextKey, App app) {
        persistentStorage = new PersistentStorage(app);
        permissionsManager = new PermissionManager(app);
        locationManager = new LocationManager(app, persistentStorage.getUserLocation());
        resourcesProvider = new ResourcesIdHolder(app);
        uiTreadExecutor = new UITreadExecutor(app);
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
    public IUITreadExecutor getUITreadExecutor() {
        return uiTreadExecutor;
    }
}
