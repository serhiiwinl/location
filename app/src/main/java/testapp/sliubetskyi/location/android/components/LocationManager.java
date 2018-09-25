package testapp.sliubetskyi.location.android.components;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
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

import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.model.modules.ILocationManager;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class LocationManager extends ApplicationComponent implements ILocationManager {

    private LocationRequest locationRequest;
    private long UPDATE_INTERVAL = 10000;  /* 10 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private final List<LocationUpdatesListener> listeners = new ArrayList<>();
    private LocationData locationData;

    public LocationManager(final App context, LocationData locationData) {
        super(context);
        this.locationData = locationData;
    }

    /**
     * Starts to track user location if it is possible.
     */
    private void startLocationUpdates() {
        //Get current location settings
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        locationRequest = new LocationRequest();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(app);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(locationSettingsRequest);
        task.addOnSuccessListener(locationSettingsResponse -> {
            int accessFinePermission = ContextCompat.checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION);
            if (accessFinePermission == PackageManager.PERMISSION_GRANTED) {
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(UPDATE_INTERVAL);
                locationRequest.setFastestInterval(FASTEST_INTERVAL);
                fusedLocationProviderClient = getFusedLocationProviderClient(app);
                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location lastLocation = locationResult.getLastLocation();
                        locationData = new LocationData(lastLocation.getLatitude(), lastLocation.getLongitude());
                        for (LocationUpdatesListener listener : listeners)
                            listener.onLocationUpdate(locationData);
                    }

                    @Override
                    public void onLocationAvailability(LocationAvailability locationAvailability) {
                        if (!locationAvailability.isLocationAvailable()) {
                            //TODO:we should handle it here or in listeners
                        }
                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            }
        });
        task.addOnFailureListener(e -> {
            for (LocationUpdatesListener listener : listeners)
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
    public void addLocationUpdatesListener(LocationUpdatesListener locationUpdatesListener) {
        listeners.add(locationUpdatesListener);
        if (fusedLocationProviderClient == null)
            startLocationUpdates();
    }

    @Override
    public void removeLocationUpdatesListener(LocationUpdatesListener listener) {
        listeners.remove(listener);
        if (listeners.isEmpty()) {
            stopLocationUpdates();
        }
    }

    @Override
    public LocationData getCurrentLocation() {
        return locationData;
    }

    /**
     * Impl it if your wish to receive user location.
     */
    public interface LocationUpdatesListener {
        /**
         * Notify listener when new location available.
         * @param location current user location
         */
        void onLocationUpdate(LocationData location);

        /**
         * Called when there is a change in the availability of location data.
         * @param locationAvailability The current status of location availability.
         */
        default void onLocationAvailability(LocationAvailability locationAvailability) {

        }

        /**
         * You can handle this {@link Exception} only in {@link android.app.Activity} listener.
         * @param resolvable exception
         */
        default void onResolvableException(Exception resolvable) {

        }
    }
}
