package testapp.sliubetskyi.location.core.ui.impl;

import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;

public class NullableMapsView implements MapsView {

    @Override
    public void askLocationPermissions() {

    }

    @Override
    public void onResolvableException(Exception resolvable) {

    }

    @Override
    public void openMapWithParams(StringsIds markerName, LocationData locationData, float cameraZoom) {

    }

    @Override
    public void updateCoordinatesOnMap(LocationData location) {

    }
}
