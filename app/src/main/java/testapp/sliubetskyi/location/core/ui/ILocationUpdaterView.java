package testapp.sliubetskyi.location.core.ui;

import testapp.sliubetskyi.location.core.model.maps.LocationData;

public interface ILocationUpdaterView extends IView {
    default void onLocationUpdate(LocationData location){}

    //These methods needed only for activities.
    default void onResolvableException(Exception resolvable){}
    default void askLocationPermissions(){}
}
