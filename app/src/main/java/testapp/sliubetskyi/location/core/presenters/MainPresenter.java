package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.ui.IMainView;

public class MainPresenter extends LocationUpdaterPresenter<IMainView> {
    public MainPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull IMainView view) {
        view.updateTrackingSettings(getAppState().isLocationTrackingAllowed(), getAppState().isPermissionsBlockedForever());
    }

    public void openMaps() {
        runViewAction(IMainView::openMapsActivity);
    }

    @Override
    public void enableLocationTracking(boolean isChecked) {
        super.enableLocationTracking(isChecked);
        runViewAction(view -> view.updateTrackingSettings(isChecked, getAppState().isPermissionsBlockedForever()));
    }

    /**
     * Start foreground service for user distance tracking.
     * @param distance user target distance in meters.
     */
    public void startDistanceTracking(long distance) {
        getPersistentStorage().setTargetDistance(distance);
        if (distance > 0)
            runViewAction(view -> view.openDistanceTrackingService(distance));
    }


}
