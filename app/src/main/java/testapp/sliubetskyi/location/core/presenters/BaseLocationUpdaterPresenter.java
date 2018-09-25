package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.android.components.LocationManager;
import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.ILocationUpdaterView;

/**
 * Presenter is needed for all views interested in location updates.
 * @param <V> {@link testapp.sliubetskyi.location.core.ui.IView} sub type.
 */
public class BaseLocationUpdaterPresenter<V extends ILocationUpdaterView> extends Presenter<V> implements
        LocationManager.LocationUpdatesListener {

    public BaseLocationUpdaterPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull V view) {
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

    private void startLocationTracking() {
        if (clientContext.getPersistentStorage().isUserAllowedTracking()) {
            if (getPermissionsManager().isLocationPermissionsAllowed()) {
                getLocationManager().addLocationUpdatesListener(this);
            } else {
                runViewAction(ILocationUpdaterView::askLocationPermissions);
            }
        }
    }

    private void stopLocationTracking() {
        getLocationManager().removeLocationUpdatesListener(this);
        clientContext.getPersistentStorage().setUserLocation(getLocationManager().getCurrentLocation());
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
}
