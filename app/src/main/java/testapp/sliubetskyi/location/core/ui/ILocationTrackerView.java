package testapp.sliubetskyi.location.core.ui;

public interface ILocationTrackerView extends ILocationUpdaterView {
    void targetDistanceAchieved(float targetDistance);
}
