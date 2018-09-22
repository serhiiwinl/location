package testapp.sliubetskyi.location.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.core.presenters.MainActivityPresenter;
import testapp.sliubetskyi.location.ui.MainActivityView;

public class MainActivity extends BaseActivity<MainActivityPresenter, MainActivityView> implements
        MainActivityView, View.OnClickListener, CompoundButton.OnCheckedChangeListener, LocationListener {

    // Request Code to ask the user for permission to view their current location (***).
    // Value 8bit (value <256)
    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.open_maps_button);
        button.setOnClickListener(this);
        CheckBox alloweLocationTracking = findViewById(R.id.allow_location_tracking_checkbox);
        alloweLocationTracking.setOnCheckedChangeListener(this);
    }

    @Override
    MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(getClientContext());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_maps_button)
            presenter.openMapsActivity();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.allow_location_tracking_checkbox)
            presenter.askLocationTracking(isChecked);
    }

    @Override
    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void askLocationPermission() {
        // With API> = 23, you have to ask the user for permission to view their location.
        if (Build.VERSION.SDK_INT >= 23) {
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                // The Permissions to ask user.
                String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                // Show a dialog asking the user to allow the above permissions.
                ActivityCompat.requestPermissions(this, permissions,
                        REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);
            }
        }
    }

    // When you have the request results.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean locationTrackingAllowed = false;
        switch (requestCode) {
            case REQUEST_ID_ACCESS_COURSE_FINE_LOCATION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (read/write).
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    locationTrackingAllowed = true;
                }
                // Cancelled or denied.
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                presenter.isLocationTrackingAllowed(locationTrackingAllowed);
                break;
            }
        }
    }

    // Find Location provider is opening.
    private String getEnabledLocationProvider() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Criteria to find location provider.
        Criteria criteria = new Criteria();

        // Returns the name of the provider that best meets the given criteria.
        // ==> "gps", "network",...
        String bestProvider = locationManager.getBestProvider(criteria, true);

        boolean enabled = locationManager.isProviderEnabled(bestProvider);

        if (!enabled) {
            Toast.makeText(this, "No location provider enabled!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "No location provider enabled!");
            return null;
        }
        return bestProvider;
    }

    // Call this method only when you have the permissions to view a user's location.
    private void showMyLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        String locationProvider = this.getEnabledLocationProvider();

        if (locationProvider == null) {
            return;
        }

        // Millisecond
        final long MIN_TIME_BW_UPDATES = 1000;
        // Met
        final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

        Location myLocation = null;
        try {
            // This code need permissions (Asked above ***)
            locationManager.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            // Getting Location.
            myLocation = locationManager.getLastKnownLocation(locationProvider);
        }
        // With Android API >= 23, need to catch SecurityException.
        catch (SecurityException e) {
            Toast.makeText(this, "Show My Location Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Show My Location Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
