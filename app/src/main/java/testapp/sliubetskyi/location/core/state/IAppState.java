package testapp.sliubetskyi.location.core.state;

import testapp.sliubetskyi.location.model.data.LocationData;

public interface IAppState {
    boolean isLocationPermissionAllowed();
    void setLocationPermissionAllowed(boolean locationPermissionAllowed);
    LocationData getLocationData();
    void setLocationData(LocationData locationData);
}
