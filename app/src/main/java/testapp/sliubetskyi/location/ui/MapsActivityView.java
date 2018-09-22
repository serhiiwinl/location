package testapp.sliubetskyi.location.ui;

import testapp.sliubetskyi.location.model.data.LocationData;

public interface MapsActivityView extends BaseView {
    void openMapOnCoordinates(String title, LocationData locationData);
}
