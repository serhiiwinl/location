package testapp.sliubetskyi.location.core.ui;

public interface IBaseActivityView extends IView {
    //These methods needed only for activities.
    default void onResolvableException(Exception resolvable){}
    default void askLocationPermissions(){}
}
