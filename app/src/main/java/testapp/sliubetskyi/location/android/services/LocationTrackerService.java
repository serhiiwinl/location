package testapp.sliubetskyi.location.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.activities.MainActivity;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.presenters.BaseLocationUpdaterPresenter;
import testapp.sliubetskyi.location.core.ui.ILocationUpdaterView;

/**
 * Starts user location tracking in background and calculates user tracked distance.
 */
public class LocationTrackerService extends BaseService<BaseLocationUpdaterPresenter<ILocationUpdaterView>,
        ILocationUpdaterView> implements ILocationUpdaterView {

    private static final int TARGET_ACHIEVED_NOTIFICATION_ID = 1001;
    private static final int ONGOING_NOTIFICATION_ID = 1000;

    private LocationData prevLocation;
    private float trackedDistance;

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(ONGOING_NOTIFICATION_ID, getNotificationHelper()
                .buildForegroundNotification(this, getPendingIntent(), trackedDistance));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onLocationChanged(LocationData location) {
        if (prevLocation != null) {
            float[] results = new float[3];
            Location.distanceBetween(prevLocation.lat, prevLocation.lng, location.lat, location.lng, results);
            trackedDistance += results[0];
            getPersistentStorage().setTrackedDistance(trackedDistance);

            if (getPersistentStorage().getTargetDistance() <= trackedDistance) {
                getNotificationHelper()
                        .showNotificationAtNotificationBar(this, getString(R.string.target_achieved),
                        R.drawable.ic_launcher_foreground,
                        getString(R.string.service_status_online, getPersistentStorage().getTargetDistance()),
                        getPendingIntent(), TARGET_ACHIEVED_NOTIFICATION_ID);
                stopForeground(true);
            } else {
                getNotificationHelper().updateForegroundNotification(ONGOING_NOTIFICATION_ID,
                        this, getPendingIntent(), trackedDistance);
            }
        }
        prevLocation = location;
    }

    private PendingIntent getPendingIntent() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }

    @Override
    public void onResolvableException(Exception resolvable) {
        //TODO: Need to go to activity with this
    }

    @Override
    public void askLocationPermissions() {
        //TODO: Need to go to activity with this
    }

    @NonNull
    @Override
    public ILocationUpdaterView getIView() {
        return this;
    }

    @Override
    public BaseLocationUpdaterPresenter<ILocationUpdaterView> createPresenter() {
        return new BaseLocationUpdaterPresenter<>(getApp().getClientContext());
    }
}
