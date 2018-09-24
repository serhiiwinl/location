package testapp.sliubetskyi.location.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.services.LocationTrackerService;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.presenters.MainPresenter;
import testapp.sliubetskyi.location.core.ui.MainView;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements
        MainView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox allowLocationTrackingCheckBox;
    private EditText distanceInputField;

    @Override
    MainPresenter createPresenter() {
        return new MainPresenter(getClientContext());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.show_current_location);
        Button distanceTracker = findViewById(R.id.start_distance_tracking);
        button.setOnClickListener(this);
        distanceTracker.setOnClickListener(this);
        allowLocationTrackingCheckBox = findViewById(R.id.allow_location_tracking_checkbox);
        distanceInputField = findViewById(R.id.distance_input_field);
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.show_current_location)
            presenter.openMapsActivity();
        else if (id == R.id.start_distance_tracking)
            presenter.startDistanceTracking(Long.valueOf(distanceInputField.getText().toString()));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.allow_location_tracking_checkbox)
            presenter.enableLocationTracking(isChecked);
    }

    //MainView impl block
    @Override
    public void openMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setUpTrackingSettings(boolean isTrackingAllowed, boolean isPermissionsBlockedForever) {
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(null);
        allowLocationTrackingCheckBox.setEnabled(!isPermissionsBlockedForever);
        allowLocationTrackingCheckBox.setChecked(isTrackingAllowed);
        allowLocationTrackingCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void openDistanceTrackingService() {
        Intent intent = new Intent(this, LocationTrackerService.class);
        startService(intent);
    }

    @Override
    protected void permissionGranted() {
        super.permissionGranted();
        presenter.enableLocationTracking(true);
    }

    @Override
    protected void permissionDeniedNeverAsk() {
        super.permissionDeniedNeverAsk();
        presenter.enableLocationTracking(false);
    }

    @Override
    protected void permissionDenied() {
        super.permissionDenied();
        presenter.enableLocationTracking(false);
    }

    @Override
    public void onLocationChanged(LocationData location) {

    }
}
