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
public abstract class BaseLocationUpdaterPresenter<V extends ILocationUpdaterView> extends Presenter<V> implements
        LocationManager.LocationUpdatesListener {

    BaseLocationUpdaterPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull V view) {
        super.onViewBound(view);
        startLocationTracking();
    }

    @Override
    public void onViewUnbound(V view) {
        stopLocationTracking();
        super.onViewUnbound(view);
    }

    void startLocationTracking() {
        if (clientContext.getPersistentStorage().isUserAllowedTracking()) {
            if (clientContext.getPermissionsManager().isLocationPermissionsAllowed())
                clientContext.getLocationManager().addLocationUpdatesListener(this);
            else
                getView().askLocationPermissions();
        }
    }

    void stopLocationTracking() {
        clientContext.getLocationManager().removeLocationUpdatesListener(this);
        clientContext.getPersistentStorage().setUserLocation(clientContext.getLocationManager().getCurrentLocation());
    }

    @Override
    public void onLocationChanged(LocationData location) {
        System.out.println(location);
    }

    @Override
    public void onResolvableException(Exception resolvable) {
        getView().onResolvableException(resolvable);
    }
}
