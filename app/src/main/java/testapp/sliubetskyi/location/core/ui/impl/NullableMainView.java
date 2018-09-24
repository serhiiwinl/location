package testapp.sliubetskyi.location.core.ui.impl;

import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.MainView;

public class NullableMainView implements MainView {
    @Override
    public void openMapsActivity() {

    }

    @Override
    public void setUpTrackingSettings(boolean isTrackingAllowed, boolean isPermissionsBlocked) {

    }

    @Override
    public void openDistanceTrackingService() {

    }

    @Override
    public void askLocationPermissions() {

    }

    @Override
    public void onResolvableException(Exception resolvable) {

    }

    @Override
    public void onLocationChanged(LocationData location) {

    }
}
