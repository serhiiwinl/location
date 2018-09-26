package testapp.sliubetskyi.core.presenters;

import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.ui.IMainView;

public class MainPresenter extends LocationUpdaterPresenter<IMainView> {
    public MainPresenter(IClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(IMainView view) {
        view.updateUI(getAppState().isLocationTrackingAllowed(),
                getAppState().isPermissionsBlockedForever(), getPersistentStorage().getTargetDistance());
    }

    public void openMaps() {
        runViewAction(IMainView::openMapsActivity);
    }

    @Override
    public void enableLocationTracking(boolean isChecked) {
        super.enableLocationTracking(isChecked);
        runViewAction(view -> view.updateUI(isChecked,
                getAppState().isPermissionsBlockedForever(), getPersistentStorage().getTargetDistance()));
    }

    /**
     * Start foreground service for user distance tracking.
     */
    public void startDistanceTracking() {
        long targetDistance = getPersistentStorage().getTargetDistance();
        if (targetDistance > 0)
            runViewAction(view -> view.openDistanceTrackingService(targetDistance));
    }

    public void saveTargetDistance(String inputText) {
        if (inputText != null && !inputText.equalsIgnoreCase("")) {
            long targetDistance = Long.parseLong(inputText);
            getPersistentStorage().setTargetDistance(targetDistance);
        }
    }


}