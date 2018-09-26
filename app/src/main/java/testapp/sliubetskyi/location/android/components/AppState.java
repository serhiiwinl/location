package testapp.sliubetskyi.location.android.components;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.core.model.modules.IAppState;
import testapp.sliubetskyi.core.model.modules.IPermissionsManager;
import testapp.sliubetskyi.core.model.modules.IPersistentData;

/**
 * Holds app state.
 */
public class AppState extends ApplicationComponent implements IAppState {

    private IPermissionsManager permissions;
    private IPersistentData persistentData;
    private boolean isAppVisible;

    AppState(App app, IPersistentData persistentStorage, IPermissionsManager permissions) {
        super(app);
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

    @Override
    public void appIsVisible(boolean isVisible) {
        this.isAppVisible = isVisible;
    }

    @Override
    public boolean appIsVisible() {
        return isAppVisible;
    }
}
