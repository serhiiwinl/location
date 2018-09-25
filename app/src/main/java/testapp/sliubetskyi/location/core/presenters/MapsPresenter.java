package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;

public class MapsPresenter extends LocationUpdaterPresenter<MapsView> {

    public MapsPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMap() {
        float cameraZoom = clientContext.getPersistentStorage().getUserCameraZoomValue();
        LocationData currentLocation = clientContext.getLocationManager().getCurrentLocation();
        runViewAction(view -> view.openMapWithParams(getMarkerId(), currentLocation, cameraZoom));
    }

    public void saveCameraParams(float zoom) {
        getPersistentStorage().setUserCameraZoomValue(zoom);
    }

    private StringsIds getMarkerId() {
        if (getAppState().isLocationTrackingAllowed())
            return StringsIds.CURRENT_LOCATION;
        else
            return StringsIds.LOCATION_UNKNOWN;
    }
}
