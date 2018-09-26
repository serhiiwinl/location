package testapp.sliubetskyi.location.core.ui;

public interface IMainView extends IBaseActivityView, ILocationUpdaterView {
    void openMapsActivity();

    /**
     * Updates settings UI according to location permissions.
     * @param isTrackingAllowed true if all permissions granted and user enable location tracking
     * @param isPermissionsBlocked true if user pressed "never ask" button on permission dialog
     * @param targetDistance user target distance
     */
    void updateUI(boolean isTrackingAllowed, boolean isPermissionsBlocked, long targetDistance);

    void openDistanceTrackingService(long distance);
}
