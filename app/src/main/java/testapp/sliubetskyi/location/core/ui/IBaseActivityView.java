package testapp.sliubetskyi.location.core.ui;

public interface IBaseActivityView extends IView {
    //TODO:add possibility to execute action on UI Thread.
    void askLocationPermissions();
    void onResolvableException(Exception resolvable);
}
