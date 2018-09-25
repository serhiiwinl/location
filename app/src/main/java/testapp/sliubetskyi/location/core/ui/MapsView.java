package testapp.sliubetskyi.location.core.ui;

import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.maps.LocationData;

public interface MapsView extends IBaseActivityView, ILocationUpdaterView {
    void openMapWithParams(StringsIds markerName, LocationData locationData, float cameraZoom);
}
