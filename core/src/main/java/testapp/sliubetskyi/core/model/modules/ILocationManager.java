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

    /**
     * Computes the approximate distance in meters between two locations if accuracy less than computed distance.
     *
     * @param locationStart the starting location object
     * @param locationEnd the ending location object
     *
     * @return distance between two points or 0
     *
     * @throws IllegalArgumentException if results is null or has length < 1
     */
    float distanceBetweenWithAccuracy(LocationData locationStart, LocationData locationEnd);
}
