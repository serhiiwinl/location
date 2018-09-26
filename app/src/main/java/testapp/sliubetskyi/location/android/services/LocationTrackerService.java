package testapp.sliubetskyi.location.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.activities.MainActivity;
import testapp.sliubetskyi.core.presenters.LocationTrackerPresenter;
import testapp.sliubetskyi.core.ui.ILocationTrackerView;

/**
 * Starts user location tracking in background and calculates user tracked distance.
 */
public class LocationTrackerService extends BaseService<LocationTrackerPresenter,
        ILocationTrackerView> implements ILocationTrackerView {

    private static final int TARGET_ACHIEVED_NOTIFICATION_ID = 1001;
    private static final int ONGOING_NOTIFICATION_ID = 1000;

    @NonNull
    @Override
    public ILocationTrackerView getIView() {
        return this;
    }

    @Override
    public LocationTrackerPresenter createPresenter() {
        return new LocationTrackerPresenter(getApp().getClientContext());
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
    public void onCreate() {
        super.onCreate();

        //service always starts like a foreground
        startForeground(ONGOING_NOTIFICATION_ID, getNotificationHelper()
                .buildForegroundNotification(this, getPendingIntent(), getPersistentStorage().getTargetDistance()));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void targetDistanceAchieved(float targetDistance) {
        getNotificationHelper()
                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
                        R.drawable.notification_icon,
                        getString(R.string.target_distance_achieved, targetDistance),
                        getPendingIntent(), TARGET_ACHIEVED_NOTIFICATION_ID);
        stopWork();
    }

    @Override
    public void onResolvableException(Exception resolvable) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(MainActivity.ENABLE_LOCATION_REQUEST, resolvable);
//        PendingIntent pendingIntent = getPendingIntent(bundle);
//        getNotificationHelper()
//                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
//                        R.drawable.notification_icon,
//                        "location tracking not allowed without GPS or WI-FI, LTE",
//                        pendingIntent, TARGET_ACHIEVED_NOTIFICATION_ID);
    }

    @Override
    public void askLocationPermissions() {
//        PendingIntent pendingIntent = getPendingIntent();
//        getNotificationHelper()
//                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
//                        R.drawable.notification_icon,
//                        "location tracking not allowed without permissions",
//                        pendingIntent, TARGET_ACHIEVED_NOTIFICATION_ID);
    }

    /**
     * Sets new target distance for tracking.
     * @param targetDistance new target distance value.
     */
    public void restartDistanceTracking(long targetDistance) {
        presenter.restartDistanceTracking(targetDistance);
        //update notification info
        getNotificationHelper().updateForegroundNotification(ONGOING_NOTIFICATION_ID,
                this, getPendingIntent(), targetDistance);
    }

    /**
     * Stops service at all if it is not bind.
     */
    public void stopWork() {
        presenter.unbindView();
        stopForeground(true);
        stopSelf();
    }

    private PendingIntent getPendingIntent(Bundle bundle) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        if (bundle != null)
            notificationIntent.putExtras(bundle);
        return PendingIntent.getActivity(this, 0, notificationIntent, 0);
    }

    private PendingIntent getPendingIntent() {
        return getPendingIntent(null);
    }
}
