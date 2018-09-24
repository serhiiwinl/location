package testapp.sliubetskyi.location.core.ui;

import testapp.sliubetskyi.location.core.model.maps.LocationData;

public interface ILocationUpdaterView extends IBaseActivityView {
    void onLocationChanged(LocationData location);
}
