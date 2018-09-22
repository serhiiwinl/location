package testapp.sliubetskyi.location.components;

import android.content.Context;
import android.content.SharedPreferences;

import testapp.sliubetskyi.location.App;
import testapp.sliubetskyi.location.core.data.IPersistentData;
import testapp.sliubetskyi.location.model.data.LocationData;

/**
 * Wraps {@link SharedPreferences} object and implements {@link IPersistentData}.
 */
public class PersistentStorage implements IPersistentData {
    private static final String fileName = "testapp.sliubetskyi.location.PREFERENCE_FILE_KEY";
    private final SharedPreferences sharedPref;

    private static final String USER_LAT_COORDINATE_KEY = "user lat coordinate key";
    private static final String USER_LNG_COORDINATE_KEY = "user lng coordinate key";

    //Sidney coordinates
    private static final int DEFAULT_USER_LAT_COORDINATE = -34;
    private static final int DEFAULT_USER_LNG_COORDINATE = 151;

    public PersistentStorage(App context) {
        sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public LocationData getUserLocation() {
        int lat = sharedPref.getInt(USER_LAT_COORDINATE_KEY, DEFAULT_USER_LAT_COORDINATE);
        int lng = sharedPref.getInt(USER_LNG_COORDINATE_KEY, DEFAULT_USER_LNG_COORDINATE);
        return new LocationData(lat, lng);
    }

    @Override
    public void setUserLocation(LocationData locationData) {
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putInt(USER_LAT_COORDINATE_KEY, locationData.lat);
        edit.putInt(USER_LNG_COORDINATE_KEY, locationData.lng);
        edit.apply();
    }
}
