package testapp.sliubetskyi.core.ui;

public interface ILocationTrackerView extends ILocationUpdaterView {
    void targetDistanceAchieved(final long targetDistance);
    void locationAccuracyIsToBig();
    void updateForegroundNotification(long targetDistance);
}
