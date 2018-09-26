package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.android.components.LocationManager;
import testapp.sliubetskyi.location.core.model.maps.LocationData;

public interface ILocationManager {
    void addLocationUpdatesListener(LocationManager.LocationUpdatesListener listener);
    void removeLocationUpdatesListener(LocationManager.LocationUpdatesListener listener);
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
