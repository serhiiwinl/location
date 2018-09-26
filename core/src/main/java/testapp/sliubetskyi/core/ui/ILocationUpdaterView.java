package testapp.sliubetskyi.core.ui;

import testapp.sliubetskyi.core.model.maps.LocationData;

public interface ILocationUpdaterView extends IBaseActivityView {
    default void onLocationUpdate(LocationData location){}
    void onResolvableException(Exception resolvable);
    void askLocationPermissions();
}
