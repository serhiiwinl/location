package testapp.sliubetskyi.location.android.components;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.model.modules.IPermissionsManager;

/**
 * Encapsulates location permissions request and checking logic.
 */
@SuppressWarnings("WeakerAccess")
public class PermissionManager extends ApplicationComponent implements IPermissionsManager {

    public static final int ALL_PERMISSIONS_REQUEST_CODE = 537;


    static List<String> locationPermissions = Arrays.asList(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION);

    public PermissionManager(App app) {
        super(app);
    }

    public static void askForRejectedPermissions(Activity activity) {
        String[] rejectedPermissions = getRejectedPermissions(activity);
        if (rejectedPermissions.length > 0)
            ActivityCompat.requestPermissions(activity, rejectedPermissions, ALL_PERMISSIONS_REQUEST_CODE);
    }

    public static String[] getRejectedPermissions(Context context) {
        ArrayList<String> rejectedPermissions = new ArrayList<>();
        for (String permission : locationPermissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED)
                rejectedPermissions.add(permission);
        }
        return  rejectedPermissions.toArray(new String[rejectedPermissions.size()]);
    }

    public static void askForPermission(Activity activity, String... permission) {
        ActivityCompat.requestPermissions(activity, permission, ALL_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public boolean isPermissionAvailable(String permission) {
        return ContextCompat.checkSelfPermission(app, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean isLocationPermissionsAllowed() {
        for (String permission : locationPermissions) {
            if (!isPermissionAvailable(permission))
                return false;
        }
        return true;
    }
}
