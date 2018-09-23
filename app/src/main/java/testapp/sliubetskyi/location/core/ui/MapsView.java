package testapp.sliubetskyi.location.core.ui;

import testapp.sliubetskyi.location.core.model.data.LocationData;

public interface MapsView extends ILocationUpdaterView {
    void openMapOnCoordinates(String title, LocationData locationData);
}
