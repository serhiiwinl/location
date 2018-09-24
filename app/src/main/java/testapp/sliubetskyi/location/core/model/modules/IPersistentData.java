package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.core.model.maps.LocationData;

/**
 * Impl it if you wish to provide persistent data.
 */
public interface IPersistentData {
    LocationData getUserLocation();
    void setUserLocation(LocationData locationData);
    boolean isUserAllowedTracking();
    void setUserAllowedTracking(boolean userAllowedTracking);
    float getUserCameraZoomValue();
    void setUserCameraZoomValue(float zoomLevel);
    boolean isPermissionsBlockedForever();
    void setPermissionsBlockedForever(boolean permissionsBlockedForever);
}
