package testapp.sliubetskyi.location.android.activities;

import android.Manifest;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.ResolvableApiException;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.components.PermissionManager;
import testapp.sliubetskyi.location.core.model.ClientContext;
import testapp.sliubetskyi.location.core.presenters.Presenter;
import testapp.sliubetskyi.location.core.ui.IBaseActivityView;

public abstract class BaseActivity<P extends Presenter<V>, V extends IBaseActivityView> extends FragmentActivity implements IBaseActivityView {

    protected P presenter;
    private int REQUEST_CHECK_SETTINGS = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null)
            presenter = createPresenter();
    }

    @Override
    protected void onStart() {
        presenter.onViewBound((V) this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onViewUnbound((V) this);
        super.onStop();
    }

    abstract P createPresenter();

    App getApp() {
        return (App) getApplication();
    }

    ClientContext getClientContext() {
        return getApp().getClientContext();
    }


    @Override
    public void onResolvableException(Exception resolvable) {
        if (resolvable instanceof ResolvableApiException) {
            // Location settings are not satisfied, but this can be fixed
            // by showing the user a dialog.
            try {
                // Show the dialog by calling startResolutionForResult(),
                // and check the result in onActivityResult().
                ((ResolvableApiException) resolvable).startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException sendEx) {
                // Ignore the error.
            }
        }
    }

    @Override
    public void askLocationPermissions() {
        PermissionManager.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION);
    }


}
