package testapp.sliubetskyi.location.core.ui;

public interface IBaseActivityView extends IView {
    //TODO:add possibility to execute action on UI Thread.
    default void askLocationPermissions(){};
    default void onResolvableException(Exception resolvable){};
}
