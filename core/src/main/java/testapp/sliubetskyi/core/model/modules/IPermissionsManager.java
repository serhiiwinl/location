package testapp.sliubetskyi.core.model.modules;

public interface IPermissionsManager {
    boolean isLocationPermissionsAllowed();
    boolean isPermissionAvailable(String permission);
}
