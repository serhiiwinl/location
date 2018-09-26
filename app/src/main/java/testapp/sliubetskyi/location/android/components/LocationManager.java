package testapp.sliubetskyi.location.android.components;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import testapp.sliubetskyi.core.model.maps.LocationData;
import testapp.sliubetskyi.core.model.modules.ILocationManager;
import testapp.sliubetskyi.core.model.modules.ILocationUpdateListener;
import testapp.sliubetskyi.location.android.App;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

/**
 * Encapsulates logic with retrieving and subscribing for location updates.
 */
public class LocationManager extends ApplicationComponent implements ILocationManager {

    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 10000;  /* 10 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private final List<ILocationUpdateListener> listeners = new ArrayList<>();
    private LocationData locationData;

    /**
     * CTOR
     * @param app application
     * @param locationData last saved location data
     */
    LocationManager(final App app, LocationData locationData) {
        super(app);
        this.locationData = locationData;
    }

    /**
     * Starts to track user location if it is possible.
     */
    private void startLocationUpdates() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        locationRequest = new LocationRequest();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        //Get settings client
        SettingsClient settingsClient = LocationServices.getSettingsClient(app);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);

        task.addOnSuccessListener(locationSettingsResponse -> {
            int accessFinePermission = ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION);
            if (accessFinePermission == PackageManager.PERMISSION_GRANTED) {
                //set up location request params
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(UPDATE_INTERVAL);
                locationRequest.setFastestInterval(FASTEST_INTERVAL);
                fusedLocationProviderClient = getFusedLocationProviderClient(app);
                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        if (locationResult == null)
                            return;
                        Location lastLocation = locationResult.getLastLocation();
                        locationData = new LocationData(lastLocation.getLatitude(), lastLocation.getLongitude(), lastLocation.getAccuracy());
                        for (ILocationUpdateListener listener : listeners)
                            listener.onLocationUpdate(locationData);
                    }

                    @Override
                    public void onLocationAvailability(LocationAvailability locationAvailability) {
                        if (!locationAvailability.isLocationAvailable()) {
                            //TODO:we should handle it here or in listeners
                        }
                    }
                };
                //start updates
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        });
        //force user to enable GPS or WI-FI
        task.addOnFailureListener(e -> {
            for (ILocationUpdateListener listener : listeners)
                listener.onResolvableException(e);
        });
    }

    /**
     * Stops to track user location.
     */
    private void stopLocationUpdates() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            fusedLocationProviderClient = null;
        }
    }

    @Override
    public void addLocationUpdatesListener(ILocationUpdateListener locationUpdatesListener) {
        listeners.add(locationUpdatesListener);
        if (fusedLocationProviderClient == null)
            startLocationUpdates();
    }

    @Override
    public void removeLocationUpdatesListener(ILocationUpdateListener listener) {
        listeners.remove(listener);
        if (listeners.isEmpty()) {
            stopLocationUpdates();
        }
    }

    @Override
    public LocationData getCurrentLocation() {
        return locationData;
    }

    @Override
    public float distanceBetween(LocationData locationStart, LocationData locationEnd) {
        float[] results = new float[3];
        Location.distanceBetween(locationStart.lat, locationStart.lng, locationEnd.lat, locationEnd.lng, results);
        float accuracy = locationStart.accuracy > locationEnd.accuracy ? locationStart.accuracy : locationEnd.accuracy;
        float distance = results[0];
        if (distance < accuracy)
            return 0;
        return distance;
    }
}
