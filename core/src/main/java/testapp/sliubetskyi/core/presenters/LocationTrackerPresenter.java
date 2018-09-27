package testapp.sliubetskyi.core.presenters;


import testapp.sliubetskyi.core.model.maps.LocationData;
import testapp.sliubetskyi.core.model.modules.IClientContext;
import testapp.sliubetskyi.core.ui.ILocationTrackerView;
import testapp.sliubetskyi.core.ui.ILocationUpdaterView;

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
        runViewAction(view -> view.updateForegroundNotification(newTargetDistance));
        startLocationTracking();
        trackedDistance = 0;
    }

    @Override
    public void onLocationUpdate(LocationData location) {
        if (prevLocation != null) {
//
//            System.out.println("location target distance " + targetDistance);
//            System.out.println("location trackedDistance before " + trackedDistance);
            trackedDistance += getLocationManager().distanceBetween(prevLocation, location);
//            System.out.println("location trackedDistance after " + trackedDistance);

            //if target distance is achieved - stop tracking and show congrats notification.
            if (targetDistance <= trackedDistance) {
                stopLocationTracking();
                runViewAction(view -> view.targetDistanceAchieved((long)targetDistance));
                getPersistentStorage().setTargetDistance(0);
            }
        }
        prevLocation = location;
    }

    @Override
    public void onLocationNotAvailable() {
        runViewAction(ILocationUpdaterView::onLocationNotAvailable);
    }
}
