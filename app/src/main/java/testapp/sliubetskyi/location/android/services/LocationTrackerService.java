package testapp.sliubetskyi.location.android.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import testapp.sliubetskyi.core.presenters.LocationTrackerPresenter;
import testapp.sliubetskyi.core.ui.ILocationTrackerView;
import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.activities.MainActivity;

import static testapp.sliubetskyi.location.android.activities.MainActivity.LOCATION_PERMISSION_ISSUE_KEY;
import static testapp.sliubetskyi.location.android.activities.MainActivity.LOCATION_ISSUE;
import static testapp.sliubetskyi.location.android.activities.MainActivity.PERMISSIONS_ISSUE;

/**
 * Starts user location tracking in background and calculates user tracked distance.
 */
public class LocationTrackerService extends BaseService<LocationTrackerPresenter,
        ILocationTrackerView> implements ILocationTrackerView {

    private static final int ONGOING_NOTIFICATION_ID = 1000;
    private static final int TARGET_ACHIEVED_NOTIFICATION_ID = 1001;
    private static final int PERMISSIONS_NOT_ALLOWED_NOTIFICATION_ID = 1002;
    private static final int LOCATION_NOT_ALLOWED_NOTIFICATION_ID = 1003;
    private static final int LOCATION_ACCURACY_IS_TOO_BIG_NOTIFICATION_ID = 1004;

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
    public void targetDistanceAchieved(long targetDistance) {
        getNotificationHelper()
                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
                        R.drawable.notification_icon,
                        getString(R.string.target_distance_achieved, targetDistance),
                        getPendingIntent(), TARGET_ACHIEVED_NOTIFICATION_ID);
        stopWork();
    }

    @Override
    public void locationAccuracyIsToBig() {
        getNotificationHelper()
                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
                        R.drawable.notification_icon,
                        getString(R.string.location_accuracy_is_too_big),
                        getPendingIntent(), LOCATION_ACCURACY_IS_TOO_BIG_NOTIFICATION_ID);
        stopWork();
    }

    @Override
    public void updateForegroundNotification(long targetDistance) {
        //update notification info
        getNotificationHelper().updateForegroundNotification(ONGOING_NOTIFICATION_ID,
                this, getPendingIntent(), targetDistance);
    }

    @Override
    public void onLocationNotAvailable() {
        //this callback will be handled in activity
        if (getAppState().appIsVisible()) return;
        getNotificationHelper().showNotificationAtNotificationBar(this, getString(R.string.app_name),
                R.drawable.notification_icon,
                getString(R.string.location_not_available),
                getPendingIntent(LOCATION_ISSUE), LOCATION_NOT_ALLOWED_NOTIFICATION_ID);
    }

    @Override
    public void onResolvableException(Exception resolvable) {
        onLocationNotAvailable();
    }

    @Override
    public void askLocationPermissions() {
        //this callback will be handled in activity
        if (getAppState().appIsVisible()) return;
        getNotificationHelper()
                .showNotificationAtNotificationBar(this, getString(R.string.app_name),
                        R.drawable.notification_icon,
                        getString(R.string.permission_denied),
                        getPendingIntent(PERMISSIONS_ISSUE), PERMISSIONS_NOT_ALLOWED_NOTIFICATION_ID);
    }

    /**
     * Sets new target distance for tracking.
     * @param targetDistance new target distance value.
     */
    public void restartDistanceTracking(long targetDistance) {
        presenter.restartDistanceTracking(targetDistance);
    }

    /**
     * Stops service at all if it is not bind.
     */
    public void stopWork() {
        presenter.unbindView();
        stopForeground(true);
        stopSelf();
    }

    private PendingIntent getPendingIntent(int errorCode) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        int flag = errorCode > 0 ? PendingIntent.FLAG_UPDATE_CURRENT : 0;
        notificationIntent.putExtra(LOCATION_PERMISSION_ISSUE_KEY, errorCode);
        return PendingIntent.getActivity(this, 0, notificationIntent, flag);
    }

    private PendingIntent getPendingIntent() {
        return getPendingIntent(0);
    }
}
