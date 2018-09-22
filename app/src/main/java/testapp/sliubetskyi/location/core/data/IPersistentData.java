package testapp.sliubetskyi.location.core.data;

import testapp.sliubetskyi.location.model.data.LocationData;

/**
 * Impl it if you wish to provide persistent data.
 */
public interface IPersistentData {
    LocationData getUserLocation();
    void setUserLocation(LocationData locationData);
}
