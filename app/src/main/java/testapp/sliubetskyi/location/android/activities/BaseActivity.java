package testapp.sliubetskyi.location.android.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.android.components.PermissionManager;
import testapp.sliubetskyi.location.android.components.ClientContext;
import testapp.sliubetskyi.core.model.StringsIds;
import testapp.sliubetskyi.core.model.modules.IAppState;
import testapp.sliubetskyi.core.model.modules.IPersistentData;
import testapp.sliubetskyi.core.presenters.LocationUpdaterPresenter;
import testapp.sliubetskyi.core.presenters.IPresenterViewComponent;
import testapp.sliubetskyi.core.ui.ILocationUpdaterView;

public abstract class BaseActivity<P extends LocationUpdaterPresenter<V>, V extends ILocationUpdaterView> extends
        FragmentActivity implements ILocationUpdaterView, IPresenterViewComponent<P, V> {

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
        presenter.bindView(getIView());
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.unbindView();
        super.onStop();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionManager.ALL_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length == 0) {
                    permissionDenied();
                } else {
                    if (grantResults.length > 1
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        permissionGranted();
                    } else {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            boolean showRationale = shouldShowRequestPermissionRationale(permissions[0])
                                    || shouldShowRequestPermissionRationale(permissions[1]);
                            if (!showRationale)
                                permissionDeniedNeverAsk();
                            else
                                permissionDenied();
                        } else {
                            permissionDenied();
                        }
                    }
                }
                break;
            }
        }
    }

    //Helpers methods block

    protected void permissionGranted() {
        Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_LONG).show();
        presenter.enableLocationTracking(true);
    }

    protected void permissionDeniedNeverAsk() {
        getClientContext().getPersistentStorage().setPermissionsBlockedForever(true);
        presenter.enableLocationTracking(false);
    }

    protected void permissionDenied() {
        Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG).show();
        presenter.enableLocationTracking(false);
    }

    App getApp() {
        return (App) getApplication();
    }

    ClientContext getClientContext() {
        return getApp().getClientContext();
    }

    IPersistentData getPersistentStorage() {
        return getClientContext().getPersistentStorage();
    }

    IAppState getAppState() {
        return getApp().getClientContext().getAppState();
    }

    /**
     * Helps to retrieve string from stringFromEnum value.
     * @param string string type
     * @return localised string
     */
    public String getString(StringsIds string) {
        return getClientContext().getResProvider().stringFromEnum(string);
    }

    /**
     * Finds service by class name and return true if it is active.
     * @param serviceClass service class
     * @return true if needed service is founded
     */
    boolean isServiceRunning(Class<?> serviceClass) {
        //TODO:maybe possible to find better solution
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) return false;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
