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
     * Start foreground service for user distance tracking.
     */
    public void startDistanceTracking() {
        long targetDistance = getPersistentStorage().getTargetDistance();
        if (targetDistance > 0)
            runViewAction(view -> view.openDistanceTrackingService(targetDistance));
    }

    public void saveTargetDistance(String inputText) {
        long targetDistance = Strings.isNullOrEmpty(inputText) ? 0 : Long.parseLong(inputText);
        getPersistentStorage().setTargetDistance(targetDistance);
        updateViewState(getAppState().isLocationTrackingAllowed());
    }

    private void updateViewState(boolean isChecked) {
        runViewAction(view -> view.updateUI(isChecked,
                getAppState().isPermissionsBlockedForever(), getPersistentStorage().getTargetDistance()));
    }

}
