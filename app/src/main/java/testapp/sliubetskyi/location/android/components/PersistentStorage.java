package testapp.sliubetskyi.location.android.components;

import android.content.Context;
import android.content.SharedPreferences;

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.model.modules.IPersistentData;

/**
 * Wraps {@link SharedPreferences} object and implements {@link IPersistentData}.
 */
public class PersistentStorage implements IPersistentData {
    private static final String fileName = "testapp.sliubetskyi.location.PREFERENCE_FILE_KEY";
    private final SharedPreferences sharedPref;

    private static final String USER_LAT_COORDINATE_KEY = "user lat coordinate key";
    private static final String USER_LNG_COORDINATE_KEY = "user lng coordinate key";

    private static final String USER_ALLOWED_TRACKING_KEY = "user allowed tracking";
    private static final String USER_BLOCK_PERMISSIONS_FOREVER_KEY = "user block permissions forever";
    private static final String USER_CAMERA_ZOOM_LEVEL = "user camera zoom level";
    private static final String USER_TRACKED_DISTANCE_KEY = "user tracked distance key";
    private static final String USER_TARGET_DISTANCE_KEY = "user target distance key";

    //Sidney coordinates
    private static final int DEFAULT_USER_LAT_COORDINATE = -34;
    private static final int DEFAULT_USER_LNG_COORDINATE = 151;
    private static final float DEFAULT_USER_MAPS_CAMERA_ZOOM_LEVEL = -1f;

    public PersistentStorage(App context) {
        sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public LocationData getUserLocation() {
        float lat = sharedPref.getFloat(USER_LAT_COORDINATE_KEY, DEFAULT_USER_LAT_COORDINATE);
        float lng = sharedPref.getFloat(USER_LNG_COORDINATE_KEY, DEFAULT_USER_LNG_COORDINATE);
        return new LocationData(lat, lng);
    }

    @Override
    public void setUserLocation(LocationData locationData) {
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putFloat(USER_LAT_COORDINATE_KEY, (float) locationData.lat);
        edit.putFloat(USER_LNG_COORDINATE_KEY, (float) locationData.lng);
        edit.apply();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean isUserAllowedTracking() {
        return sharedPref.getBoolean(USER_ALLOWED_TRACKING_KEY, false);
    }

    @Override
    public void setUserAllowedTracking(boolean userAllowedTracking) {
        sharedPref.edit().putBoolean(USER_ALLOWED_TRACKING_KEY, userAllowedTracking).apply();
    }

    @Override
    public float getUserCameraZoomValue() {
        return sharedPref.getFloat(USER_CAMERA_ZOOM_LEVEL, DEFAULT_USER_MAPS_CAMERA_ZOOM_LEVEL);
    }

    @Override
    public void setUserCameraZoomValue(float zoomLevel) {
        sharedPref.edit().putFloat(USER_CAMERA_ZOOM_LEVEL, zoomLevel).apply();
    }

    @Override
    public void setPermissionsBlockedForever(boolean permissionsBlockedForever) {
        sharedPref.edit().putBoolean(USER_BLOCK_PERMISSIONS_FOREVER_KEY, permissionsBlockedForever).apply();
    }

    @Override
    public void setTrackedDistance(float trackedDistance) {
        sharedPref.edit().putFloat(USER_TRACKED_DISTANCE_KEY, trackedDistance).apply();
    }

    @Override
    public float getTrackedDistance() {
        return sharedPref.getFloat(USER_TRACKED_DISTANCE_KEY, 0);
    }

    @Override
    public void setTargetDistance(long targetDistance) {
        sharedPref.edit().putLong(USER_TARGET_DISTANCE_KEY, targetDistance).apply();
    }

    @Override
    public long getTargetDistance() {
        return sharedPref.getLong(USER_TARGET_DISTANCE_KEY, 0);
    }

    @Override
    public boolean isPermissionsBlockedForever() {
        return sharedPref.getBoolean(USER_BLOCK_PERMISSIONS_FOREVER_KEY, false);
    }
}
