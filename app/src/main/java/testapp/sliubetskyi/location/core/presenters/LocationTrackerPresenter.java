package testapp.sliubetskyi.location.core.presenters;

import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.ui.ILocationTrackerView;

public class LocationTrackerPresenter extends LocationUpdaterPresenter<ILocationTrackerView> {
    private LocationData prevLocation;
    private float trackedDistance;
    private float targetDistance;

    public LocationTrackerPresenter(ClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(@NonNull ILocationTrackerView view) {
        super.onViewBound(view);
        getPersistentStorage().setServiceWorking(true);
    }

    @Override
    public void onViewUnbound(ILocationTrackerView view) {
        getPersistentStorage().setServiceWorking(false);
        super.onViewUnbound(view);
    }

    public void restartDistanceTracking(long newTargetDistance) {
        startLocationTracking();
        targetDistance = newTargetDistance;
        trackedDistance = 0;
        getPersistentStorage().setTargetDistance(newTargetDistance);
    }

    @Override
    public void onLocationUpdate(LocationData location) {
        if (prevLocation != null) {
            trackedDistance += getLocationManager().distanceBetween(prevLocation, location);

            //if target distance is achieved - stop tracking and show congrats notification.
            if (targetDistance <= trackedDistance) {
                getPersistentStorage().setTargetDistance(0);
                stopLocationTracking();
                runViewAction(view -> view.targetDistanceAchieved(targetDistance));
            }
        }
        prevLocation = location;
    }

}
