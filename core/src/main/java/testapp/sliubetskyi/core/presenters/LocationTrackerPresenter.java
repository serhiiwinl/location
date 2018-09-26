package testapp.sliubetskyi.core.presenters;


import testapp.sliubetskyi.core.model.maps.LocationData;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.ui.ILocationTrackerView;

public class LocationTrackerPresenter extends LocationUpdaterPresenter<ILocationTrackerView> {
    private LocationData prevLocation;
    private float trackedDistance;
    private float targetDistance;

    public LocationTrackerPresenter(IClientContext clientContext) {
        super(clientContext);
    }

    @Override
    public void onViewBound(ILocationTrackerView view) {
        super.onViewBound(view);
        targetDistance = getPersistentStorage().getTargetDistance();
    }

    public void restartDistanceTracking(long newTargetDistance) {
        if (newTargetDistance == getPersistentStorage().getTargetDistance())
            return;
        startLocationTracking();
        trackedDistance = 0;
        targetDistance = newTargetDistance;
        getPersistentStorage().setTargetDistance(newTargetDistance);
    }

    @Override
    public void onLocationUpdate(LocationData location) {
        if (prevLocation != null) {
            trackedDistance += getLocationManager().distanceBetween(prevLocation, location);

            //if target distance is achieved - stop tracking and show congrats notification.
            if (targetDistance <= trackedDistance) {
                stopLocationTracking();
                runViewAction(view -> view.targetDistanceAchieved((long)targetDistance));
                getPersistentStorage().setTargetDistance(0);
            }
        }
        prevLocation = location;
    }

}
