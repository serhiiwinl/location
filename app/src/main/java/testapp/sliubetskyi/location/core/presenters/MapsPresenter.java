package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;
import testapp.sliubetskyi.location.core.ui.impl.NullableMapsView;

public class MapsPresenter extends BaseLocationUpdaterPresenter<MapsView> {

    public MapsPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMap() {
        float cameraZoom = clientContext.getPersistentStorage().getUserCameraZoomValue();
        LocationData currentLocation = clientContext.getLocationManager().getCurrentLocation();
        getView().openMapWithParams(getMarkerId(), currentLocation, cameraZoom);
    }

    @Override
    public MapsView getView() {
        MapsView view = super.getView();
        if(view == null)
            view = new NullableMapsView();
        return view;
    }

    public void saveCameraParams(float zoom) {
        clientContext.getPersistentStorage().setUserCameraZoomValue(zoom);
    }

    private StringsIds getMarkerId() {
        if (clientContext.getAppState().isLocationTrackingAllowed())
            return StringsIds.CURRENT_LOCATION;
        else
            return StringsIds.LOCATION_UNKNOWN;
    }
}
