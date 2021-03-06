package testapp.sliubetskyi.core.ui;

import testapp.sliubetskyi.core.model.maps.LocationData;

public interface ILocationUpdaterView extends IBaseView {
    default void onLocationUpdate(LocationData location){}
    void onLocationNotAvailable();
    void onResolvableException(Exception resolvable);
    void askLocationPermissions();
}
