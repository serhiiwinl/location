package testapp.sliubetskyi.core.model.modules;

import testapp.sliubetskyi.core.model.maps.LocationData;

public interface ILocationUpdateListener {
    /**
     * Notify listener when new location available.
     *
     * @param location current user location
     */
    void onLocationUpdate(LocationData location);

    /**
     * You can handle this {@link Exception} only in activity listener.
     *
     * @param resolvable exception
     */
    default void onResolvableException(Exception resolvable) {

    }

    /**
     * Notify listener when new location not available more.
     */
    void onLocationNotAvailable();
}
