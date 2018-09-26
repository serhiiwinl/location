package testapp.sliubetskyi.core.presenters;

import testapp.sliubetskyi.core.model.StringsIds;
import testapp.sliubetskyi.core.model.maps.LocationData;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.ui.MapsView;

public class MapsPresenter extends LocationUpdaterPresenter<MapsView> {

    public MapsPresenter(IClientContext clientContext) {
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
