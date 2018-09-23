package testapp.sliubetskyi.location.core.ui.impl;

import testapp.sliubetskyi.location.core.model.data.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;

public class NullableMapsView implements MapsView {
    @Override
    public void openMapOnCoordinates(String title, LocationData locationData) {

    }

    @Override
    public void askLocationPermissions() {

    }

    @Override
    public void onResolvableException(Exception resolvable) {

    }
}
