package testapp.sliubetskyi.core.model.modules;

import testapp.sliubetskyi.core.model.maps.LocationData;

public interface ILocationManager {
    void addLocationUpdatesListener(ILocationUpdateListener listener);
    void removeLocationUpdatesListener(ILocationUpdateListener listener);
    void restartUpdates();
    LocationData getCurrentLocation();

    /**
     * Computes the approximate distance in meters between two locations.
     *
     * @param locationStart the starting location object
     * @param locationEnd the ending location object
     *
     * @return distance between two points
     *
     * @throws IllegalArgumentException if results is null or has length < 1
     */

    float distanceBetween(LocationData locationStart, LocationData locationEnd);
}
