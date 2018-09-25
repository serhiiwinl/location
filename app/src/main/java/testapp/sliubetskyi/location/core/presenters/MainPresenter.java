package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.ui.IMainView;

public class MainPresenter extends BaseLocationUpdaterPresenter<IMainView> {
    public MainPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull IMainView view) {
        view.setUpTrackingSettings(getAppState().isLocationTrackingAllowed(), getAppState().isPermissionsBlockedForever());
    }

    public void openMapsActivity() {
        runViewAction(IMainView::openMapsActivity);
    }

    public void enableLocationTracking(boolean isChecked) {
        //save to cash
        getPersistentStorage().setUserAllowedTracking(isChecked);
        runViewAction(view -> view.setUpTrackingSettings(isChecked, getAppState().isPermissionsBlockedForever()));
        //start tracking if allowed
        if (isChecked)
            startLocationTracking();
        else
            stopLocationTracking();
    }

    /**
     * Start foreground service for user distance tracking.
     * @param distance user target distance in meters.
     */
    public void startDistanceTracking(long distance) {
        getPersistentStorage().setTargetDistance(distance);
        runViewAction(IMainView::openDistanceTrackingService);
    }
}
