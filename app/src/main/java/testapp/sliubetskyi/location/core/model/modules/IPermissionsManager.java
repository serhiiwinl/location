package testapp.sliubetskyi.location.core.model.modules;

public interface IPermissionsManager {
    boolean isLocationPermissionsAllowed();
    boolean isPermissionAvailable(String permission);
}
