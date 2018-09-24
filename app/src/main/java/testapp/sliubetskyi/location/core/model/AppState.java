package testapp.sliubetskyi.location.core.model;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.model.modules.IAppState;
import testapp.sliubetskyi.location.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;

/**
 * Holds app state.
 */
public class AppState implements IAppState {

    private IPermissionsManager permissions;
    private IPersistentData persistentData;

    AppState(App app, IPersistentData persistentStorage, IPermissionsManager permissions) {
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
