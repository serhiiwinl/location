package testapp.sliubetskyi.core.model.modules;

public interface IAppState {
    boolean isLocationTrackingAllowed();
    boolean isPermissionsBlockedForever();

    void appIsVisible(boolean inForeground);

    boolean appIsVisible();
}
