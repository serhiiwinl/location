package testapp.sliubetskyi.location.core.state;

import testapp.sliubetskyi.location.core.data.IPersistentData;
import testapp.sliubetskyi.location.model.data.LocationData;

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

    @Override
    public LocationData getLocationData() {
        return locationData;
    }

    @Override
    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
        persistentData.setUserLocation(locationData);
    }
}
