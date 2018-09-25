package testapp.sliubetskyi.location.android.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.services.LocationTrackerService;
import testapp.sliubetskyi.location.core.presenters.MainPresenter;
import testapp.sliubetskyi.location.core.ui.IMainView;

public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements
        IMainView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox allowLocationTrackingCheckBox;
    private EditText distanceInputField;
    private Button distanceTrackerButton;
    private Button showCurrentLocationButton;

    private boolean isServiceBound;
    private LocationTrackerService locationTrackerService;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(getClientContext());
    }

    @NonNull
    @Override
    public IMainView getIView() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCurrentLocationButton = findViewById(R.id.show_current_location);
        distanceTrackerButton = findViewById(R.id.start_distance_tracking);
        showCurrentLocationButton.setOnClickListener(this);
        distanceTrackerButton.setOnClickListener(this);
        allowLocationTrackingCheckBox = findViewById(R.id.allow_location_tracking_checkbox);
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(this);
        distanceInputField = findViewById(R.id.distance_input_field);

        long targetDistance = getClientContext().getPersistentStorage().getTargetDistance();
        if (targetDistance > 0)
            distanceInputField.setText(String.valueOf(targetDistance));
        else
            distanceInputField.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isServiceBound) {
            unbindService(connection);
            isServiceBound = false;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.show_current_location) {
            presenter.openMapsActivity();
        } else if (id == R.id.start_distance_tracking) {
            if (distanceInputField.getText() != null) {
                String inputText = distanceInputField.getText().toString();
                presenter.startDistanceTracking(Long.parseLong(inputText));
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.allow_location_tracking_checkbox)
            presenter.enableLocationTracking(isChecked);
    }

    @Override
    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateTrackingSettings(boolean isTrackingAllowed, boolean isPermissionsBlockedForever) {
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(null);
        allowLocationTrackingCheckBox.setEnabled(!isPermissionsBlockedForever);
        allowLocationTrackingCheckBox.setChecked(isTrackingAllowed);
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(this);

        String openMapsButtonString = isTrackingAllowed?getString(R.string.show_current_location):getString(R.string.open_maps);
        showCurrentLocationButton.setText(openMapsButtonString);
        distanceTrackerButton.setEnabled(isTrackingAllowed);
        if (isServiceBound && !isTrackingAllowed) {
            isServiceBound = false;
            unbindService(connection);
            locationTrackerService.stopForeground(true);
            locationTrackerService.stopSelf();
            locationTrackerService = null;
        }
    }

    @Override
    public void openDistanceTrackingService(long distance) {
        if (isServiceBound) {
            locationTrackerService.restartDistanceTracking(distance);
        } else {
            Intent intent = new Intent(this, LocationTrackerService.class);
            startService(intent);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocationTrackerService.LocationTrackerBinder binder = (LocationTrackerService.LocationTrackerBinder) service;
            locationTrackerService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isServiceBound = false;
        }
    };
}
