package testapp.sliubetskyi.location.core.model.modules;

public interface IPermissions {
    boolean isLocationPermissionsAllowed();
    boolean isPermissionAvailable(String permission);
}
