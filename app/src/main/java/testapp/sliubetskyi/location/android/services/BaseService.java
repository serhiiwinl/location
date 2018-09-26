package testapp.sliubetskyi.location.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.components.NotificationHelper;
import testapp.sliubetskyi.core.model.modules.IPersistentData;
import testapp.sliubetskyi.core.presenters.IPresenterViewComponent;
import testapp.sliubetskyi.core.presenters.Presenter;
import testapp.sliubetskyi.core.ui.ILocationUpdaterView;
import testapp.sliubetskyi.core.ui.IView;

public abstract class BaseService<P extends Presenter<V>, V extends IView> extends Service
        implements ILocationUpdaterView, IPresenterViewComponent<P, V> {

    protected P presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = createPresenter();
        presenter.bindView(getIView());
    }

    @Override
    public void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    App getApp() {
        return (App) getApplicationContext();
    }

    IPersistentData getPersistentStorage() {
        return getApp().getClientContext().getPersistentStorage();
    }

    NotificationHelper getNotificationHelper() {
        return getApp().getNotificationHelper();
    }
}
