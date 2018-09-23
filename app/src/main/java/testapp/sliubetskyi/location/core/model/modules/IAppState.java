package testapp.sliubetskyi.location.core.model.modules;

public interface IAppState {
    boolean isLocationPermissionAllowed();
    void setLocationPermissionAllowed(boolean locationPermissionAllowed);
}
