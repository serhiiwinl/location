package testapp.sliubetskyi.core.presenters;

import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.ui.IMainView;
import testapp.sliubetskyi.core.utils.Strings;

public class MainPresenter extends LocationUpdaterPresenter<IMainView> {
    public MainPresenter(IClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(IMainView view) {
        if (getAppState().isLocationTrackingAllowed())
            super.onViewBound(view);
        view.updateUI(getAppState().isLocationTrackingAllowed(),
                getAppState().isPermissionsBlockedForever(), getPersistentStorage().getTargetDistance());
    }

    public void openMaps() {
        runViewAction(IMainView::openMapsActivity);
    }

    @Override
    public void enableLocationTracking(boolean isChecked) {
        super.enableLocationTracking(isChecked);
        updateViewState(isChecked);
    }

    /**
     * Starts foreground service for user distance tracking.
     * @param targetDistance new user target distance
     */
    public void startDistanceTracking(String targetDistance) {
        long prevTargetDistance = getPersistentStorage().getTargetDistance();
        long newTargetDistance = Long.valueOf(targetDistance);

        if (newTargetDistance != prevTargetDistance) {
            getPersistentStorage().setTargetDistance(newTargetDistance);
            runViewAction(view -> view.openDistanceTrackingService(newTargetDistance));
        }
        updateViewState(getAppState().isLocationTrackingAllowed());
    }

    private void updateViewState(boolean isChecked) {
        runViewAction(view -> view.updateUI(isChecked,
                getAppState().isPermissionsBlockedForever(), getPersistentStorage().getTargetDistance()));
    }

}
