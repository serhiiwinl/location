package testapp.sliubetskyi.core.ui;

import testapp.sliubetskyi.core.model.StringsIds;
import testapp.sliubetskyi.core.model.maps.LocationData;

public interface MapsView extends IBaseActivityView, ILocationUpdaterView {
    void openMapWithParams(StringsIds markerName, LocationData locationData, float cameraZoom);
}
