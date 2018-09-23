package testapp.sliubetskyi.location.core.presenters;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.ui.MainView;
import testapp.sliubetskyi.location.core.ui.impl.NullableMainView;

public class MainPresenter extends BaseLocationUpdaterPresenter<MainView> {
    public MainPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(MainView view) {
        getView().setUpTrackingSettings(clientContext.getPersistentStorage().isUserAllowedTracking());
        super.onViewBound(view);
    }

    @Override
    public MainView getView() {
        MainView view = super.getView();
        if (view == null)
            view = new NullableMainView();
        return view;
    }

    public void openMapsActivity() {
        getView().openMapsActivity();
    }

    public void enableLocationTracking(boolean isChecked) {
        //save to cash
        clientContext.getPersistentStorage().setUserAllowedTracking(isChecked);
        //start tracking if allowed
        if (isChecked)
            startLocationTracking();
        else
            stopLocationTracking();
    }
}
