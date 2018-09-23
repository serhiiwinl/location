package testapp.sliubetskyi.location.core.model;

import testapp.sliubetskyi.location.core.model.data.LocationData;
import testapp.sliubetskyi.location.core.model.modules.IAppState;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;

/**
 * Holds app state.
 */
public class AppState implements IAppState {

    private LocationData locationData;
    private IPersistentData persistentData;
    private boolean isLocationPermissionAllowed;

    public AppState(IPersistentData persistentData) {
        this.persistentData = persistentData;
        this.locationData = persistentData.getUserLocation();
    }

    @Override
    public boolean isLocationPermissionAllowed() {
        return false;
    }

    @Override
    public void setLocationPermissionAllowed(boolean locationPermissionAllowed) {
        this.isLocationPermissionAllowed = locationPermissionAllowed;
    }
}
