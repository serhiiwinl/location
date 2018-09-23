package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.data.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;
import testapp.sliubetskyi.location.core.ui.impl.NullableMapsView;

public class MapsPresenter extends BaseLocationUpdaterPresenter<MapsView> {

    private String marker;

    public MapsPresenter(ClientContext clientContext) {
        super(clientContext);
        marker = "Marker";
    }

    public void openMap() {
        getView().openMapOnCoordinates(marker, clientContext.getLocationManager().getCurrentLocation());
    }

    @Override
    public void onLocationChanged(LocationData location) {
        getView().openMapOnCoordinates(marker, location);
    }

    @Override
    public MapsView getView() {
        MapsView view = super.getView();
        if(view == null)
            view = new NullableMapsView();
        return view;
    }
}
