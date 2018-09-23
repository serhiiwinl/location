package testapp.sliubetskyi.location.core.ui;

public interface MainView extends ILocationUpdaterView {
    void openMapsActivity();
    void setUpTrackingSettings(boolean isTrackingAllowed);
}
