package testapp.sliubetskyi.location.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    private long trackedDistance;
    private long targetDistance;

    @Override
    public void onCreate() {
        super.onCreate();
        targetDistance = getPersistentStorage().getTargetDistance();
        startForeground(ONGOING_NOTIFICATION_ID, getNotificationHelper()
                .buildForegroundNotification(this, getPendingIntent(), targetDistance));
    }

    private IBinder binder = new LocationTrackerBinder();

    public final class LocationTrackerBinder extends Binder {
        public LocationTrackerService getService() {
            return LocationTrackerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onLocationUpdate(LocationData location) {
        if (prevLocation != null) {
            if (location.equals(prevLocation))
                return;
            float[] results = new float[3];
            Location.distanceBetween(prevLocation.lat, prevLocation.lng, location.lat, location.lng, results);
            trackedDistance += results[0];

            if (targetDistance <= trackedDistance) {
                getNotificationHelper()
                        .showNotificationAtNotificationBar(this, getString(R.string.app_name),
                        R.drawable.notification_icon,
                        getString(R.string.target_distance_achieved, targetDistance),
                        getPendingIntent(), TARGET_ACHIEVED_NOTIFICATION_ID);
                stopWork();
            }
        }
        prevLocation = location;
    }

    /**
     * Stops service at all.
     */
    public void stopWork() {
        presenter.unbindView();
        stopForeground(true);
        stopSelf();
    }

    /**
     * Restarts target distance tracking.
     * @param targetDistance new target distance value.
     */
    public void restartDistanceTracking(long targetDistance) {
        this.targetDistance = targetDistance;
        this.trackedDistance = 0;
        getNotificationHelper().updateForegroundNotification(ONGOING_NOTIFICATION_ID,
                this, getPendingIntent(), targetDistance);
    }

    private PendingIntent getPendingIntent() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, notificationIntent, 0);
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
