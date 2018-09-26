package testapp.sliubetskyi.location.android.components;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.core.model.modules.IAppState;
import testapp.sliubetskyi.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.core.model.modules.IPersistentData;

/**
 * Holds app state.
 */
public class AppState implements IAppState {

    private IPermissionsManager permissions;
    private IPersistentData persistentData;

    public AppState(App app, IPersistentData persistentStorage, IPermissionsManager permissions) {
        this.persistentData = persistentStorage;
        this.permissions = permissions;
    }

    @Override
    public boolean isLocationTrackingAllowed() {
        return persistentData.isUserAllowedTracking() && permissions.isLocationPermissionsAllowed();
    }

    @Override
    public boolean isPermissionsBlockedForever() {
        return persistentData.isPermissionsBlockedForever() && !permissions.isLocationPermissionsAllowed();
    }
}
