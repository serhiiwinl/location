package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.android.components.LocationManager;
import testapp.sliubetskyi.location.core.model.data.LocationData;

public interface ILocationManager {
    void addLocationUpdatesListener(LocationManager.LocationUpdatesListener listener);
    void removeLocationUpdatesListener(LocationManager.LocationUpdatesListener listener);
    LocationData getCurrentLocation();
}
