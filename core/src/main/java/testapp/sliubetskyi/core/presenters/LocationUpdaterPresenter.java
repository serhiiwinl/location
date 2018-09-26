package testapp.sliubetskyi.core.presenters;

import testapp.sliubetskyi.core.model.maps.LocationData;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.model.modules.ILocationUpdateListener;
import testapp.sliubetskyi.core.ui.ILocationUpdaterView;

/**
 * Presenter is needed for all views interested in location updates.
 * @param <V> {@link testapp.sliubetskyi.core.ui.IView} sub type.
 */
public class LocationUpdaterPresenter<V extends ILocationUpdaterView> extends Presenter<V> implements
        ILocationUpdateListener {

    LocationUpdaterPresenter(IClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(V view) {
        startLocationTracking();
    }

    @Override
    public void onViewUnbound(V view) {
        stopLocationTracking();
    }

    @Override
    public void onLocationUpdate(LocationData location) {
        runViewAction(view -> view.onLocationUpdate(location));
    }

    @Override
    public void onResolvableException(Exception resolvable) {
        runViewAction(view -> view.onResolvableException(resolvable));
    }

    @Override
    public void onLocationNotAvailable() {
        runViewAction(ILocationUpdaterView::onLocationNotAvailable);
    }

    public void enableLocationTracking(boolean isChecked) {
        //save to cash
        getPersistentStorage().setUserAllowedTracking(isChecked);

        //start tracking if allowed
        if (isChecked)
            startLocationTracking();
        else
            stopLocationTracking();
    }

    public void restartLocationTracking() {
        if (getAppState().isLocationTrackingAllowed())
            getLocationManager().restartUpdates();
    }

    void startLocationTracking() {
        if (clientContext.getPersistentStorage().isUserAllowedTracking()) {
            if (getPermissionsManager().isLocationPermissionsAllowed()) {
                getLocationManager().addLocationUpdatesListener(this);
            } else {
                runViewAction(ILocationUpdaterView::askLocationPermissions);
            }
        }
    }

    void stopLocationTracking() {
        getLocationManager().removeLocationUpdatesListener(this);
        clientContext.getPersistentStorage().setUserLocation(getLocationManager().getCurrentLocation());
    }
}
