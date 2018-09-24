package testapp.sliubetskyi.location.core.ui;

public interface MainView extends ILocationUpdaterView {
    void openMapsActivity();

    /**
     * Updates settings UI according to location permissions.
     * @param isTrackingAllowed true if all permissions granted and user enable location tracking
     * @param isPermissionsBlocked true if user pressed "never ask" button on permission dialog.
     */
    void setUpTrackingSettings(boolean isTrackingAllowed, boolean isPermissionsBlocked);

    void openDistanceTrackingService();
}
