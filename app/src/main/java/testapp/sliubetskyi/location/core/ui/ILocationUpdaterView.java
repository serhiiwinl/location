package testapp.sliubetskyi.location.core.ui;

import testapp.sliubetskyi.location.core.model.maps.LocationData;

public interface ILocationUpdaterView extends IBaseActivityView {
    default void onLocationUpdate(LocationData location){}
    void onResolvableException(Exception resolvable);
    void askLocationPermissions();
}
