package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.state.ClientContext;
import testapp.sliubetskyi.location.ui.MainActivityView;

public class MainActivityPresenter extends Presenter<MainActivityView> {
    public MainActivityPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    public void openMapsActivity() {
        view.openMapsActivity();
    }

    public void askLocationTracking(boolean isUserAllowLocationTracking) {
        if (isUserAllowLocationTracking && !clientContext.getAppState().isLocationPermissionAllowed()) {
            view.askLocationPermission();
        }
    }

    public void isLocationTrackingAllowed(boolean locationTrackingAllowed) {
        clientContext.getAppState().setLocationPermissionAllowed(locationTrackingAllowed);
    }
}
