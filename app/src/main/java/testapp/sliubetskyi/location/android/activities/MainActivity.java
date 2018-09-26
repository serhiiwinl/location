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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.io.Serializable;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.services.LocationTrackerService;
import testapp.sliubetskyi.core.presenters.MainPresenter;
import testapp.sliubetskyi.core.ui.IMainView;

public class MainActivity extends BaseActivity<MainPresenter, IMainView> implements
        IMainView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox allowLocationTrackingCheckBox;
    private EditText distanceInputField;
    private Button distanceTrackerButton;
    private Button showCurrentLocationButton;

    private boolean isServiceBound;
    private LocationTrackerService locationTrackerService;

    public static final String ENABLE_LOCATION_REQUEST = "enable_location_request";

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
        distanceInputField.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (distanceInputField.getText() != null)
                    presenter.saveTargetDistance(distanceInputField.getText().toString());
            }
            return false;
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null)
            return;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Serializable serializable = extras.getSerializable(ENABLE_LOCATION_REQUEST);
            if (serializable != null)
                onResolvableException((Exception) serializable);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isServiceBound && getAppState().isServiceWorking()) {
            bindService(getServiceIntent(), connection, BIND_AUTO_CREATE);
            isServiceBound = false;
        }
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
    protected void onDestroy() {
        connection = null;
        locationTrackerService = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.show_current_location) {
            presenter.openMaps();
        } else if (id == R.id.start_distance_tracking) {
            if (distanceInputField.getText() != null)
                presenter.startDistanceTracking();
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
    public void updateUI(boolean isTrackingAllowed, boolean isPermissionsBlockedForever, long targetDistance) {
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(null);
        allowLocationTrackingCheckBox.setEnabled(!isPermissionsBlockedForever);
        allowLocationTrackingCheckBox.setChecked(isTrackingAllowed);
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(this);

        String openMapsButtonString = isTrackingAllowed ? getString(R.string.show_current_location) : getString(R.string.open_maps);
        showCurrentLocationButton.setText(openMapsButtonString);
        distanceTrackerButton.setEnabled(isTrackingAllowed && targetDistance > 0);

        String targetDistanceText = targetDistance > 0 ? String.valueOf(targetDistance) : "";
        distanceInputField.setText(targetDistanceText);

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
            Intent intent = getServiceIntent();
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
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
            locationTrackerService = null;
        }
    };

    Intent getServiceIntent() {
        return new Intent(this, LocationTrackerService.class);
    }
}
