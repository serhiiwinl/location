package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.ClientContext;
import testapp.sliubetskyi.location.ui.MapsActivityView;

public class MapsActivityPresenter extends Presenter<MapsActivityView> {
    public MapsActivityPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMapInCoordinates() {
        //TODO:get data from model
        view.openMapOnCoordinates("Marker On Sidney", -34, 151);
    }
}
