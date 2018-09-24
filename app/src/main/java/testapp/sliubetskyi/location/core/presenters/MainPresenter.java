package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.ui.MainView;
import testapp.sliubetskyi.location.core.ui.impl.NullableMainView;

public class MainPresenter extends BaseLocationUpdaterPresenter<MainView> {
    public MainPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull MainView view) {
        super.onViewBound(view);
        view.setUpTrackingSettings(getAppState().isLocationTrackingAllowed(), getAppState().isPermissionsBlockedForever());
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
        getPersistentStorage().setUserAllowedTracking(isChecked);
        getView().setUpTrackingSettings(isChecked, getAppState().isPermissionsBlockedForever());
        //start tracking if allowed
        if (isChecked)
            startLocationTracking();
        else
            stopLocationTracking();
    }
}
