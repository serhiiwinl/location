package testapp.sliubetskyi.core.ui;

public interface ILocationTrackerView extends ILocationUpdaterView {
    void targetDistanceAchieved(long targetDistance);
}
