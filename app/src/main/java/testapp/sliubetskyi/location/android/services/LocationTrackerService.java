package testapp.sliubetskyi.location.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.presenters.BaseLocationUpdaterPresenter;

public class LocationTrackerService extends Service {
    private BaseLocationUpdaterPresenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (presenter == null)
            presenter = new BaseLocationUpdaterPresenter(getApp().getClientContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    App getApp() {
       return (App) getApplicationContext();
    }

}
