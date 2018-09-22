package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.state.ClientContext;
import testapp.sliubetskyi.location.ui.MapsActivityView;

public class MapsActivityPresenter extends Presenter<MapsActivityView> {
    public MapsActivityPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMap() {
        view.openMapOnCoordinates("Marker On Sidney", clientContext.getAppState().getLocationData());
    }
}
