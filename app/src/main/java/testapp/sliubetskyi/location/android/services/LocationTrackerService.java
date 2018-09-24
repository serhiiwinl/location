package testapp.sliubetskyi.location.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.activities.MainActivity;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;
import testapp.sliubetskyi.location.core.presenters.BaseLocationUpdaterPresenter;
import testapp.sliubetskyi.location.core.ui.ILocationUpdaterView;

/**
 * Starts user location tracking in background and calculates user tracked distance.
 */
public class LocationTrackerService extends Service implements ILocationUpdaterView {
    private BaseLocationUpdaterPresenter<LocationTrackerService> presenter;
    private static final int ONGOING_NOTIFICATION_ID = -1337;

    private IBinder binder = new LocalBinder();
    private LocationData prevLocation;
    private float trackedDistance;

    public final class LocalBinder extends Binder {
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
    public void onCreate() {
        super.onCreate();
        presenter = new BaseLocationUpdaterPresenter<>(getApp().getClientContext());
        startForeground(ONGOING_NOTIFICATION_ID, getApp()
                .getClientContext()
                .getNotificationDisplayer()
                .buildForegroundNotification(this, getPendingIntent()));
        presenter.onViewBound(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        presenter.onViewUnbound(this);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(LocationData location) {
        if (prevLocation != null) {
            float[] results = new float[3];
            Location.distanceBetween(prevLocation.lat, prevLocation.lng, location.lat, location.lng, results);
            trackedDistance += results[0];
            getPersistentStorage().setTrackedDistance(trackedDistance);

            if (getPersistentStorage().getTargetDistance() <= trackedDistance) {
                getApp().getClientContext().getNotificationDisplayer()
                        .showNotificationAtNotificationBar(this, getString(R.string.target_achieved),
                        R.drawable.ic_launcher_foreground,
                        getString(R.string.service_status_online, getPersistentStorage().getTargetDistance()),
                        getPendingIntent(), 11);
                stopForeground(true);
            }
        }
        prevLocation = location;
    }

    private PendingIntent getPendingIntent() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }

    App getApp() {
        return (App) getApplicationContext();
    }

    private IPersistentData getPersistentStorage() {
        return getApp().getClientContext().getPersistentStorage();
    }

}
