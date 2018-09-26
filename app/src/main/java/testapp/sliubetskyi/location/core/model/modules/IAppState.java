package testapp.sliubetskyi.location.core.model.modules;

public interface IAppState {
    boolean isLocationTrackingAllowed();
    boolean isPermissionsBlockedForever();
    boolean isServiceWorking();
}
