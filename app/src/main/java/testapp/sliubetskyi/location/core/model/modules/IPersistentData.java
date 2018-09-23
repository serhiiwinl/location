package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.core.model.data.LocationData;

/**
 * Impl it if you wish to provide persistent data.
 */
public interface IPersistentData {
    LocationData getUserLocation();
    void setUserLocation(LocationData locationData);
    boolean isUserAllowedTracking();
    void setUserAllowedTracking(boolean userAllowedTracking);
}
