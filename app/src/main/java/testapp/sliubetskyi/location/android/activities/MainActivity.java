package testapp.sliubetskyi.location.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.core.presenters.MainPresenter;
import testapp.sliubetskyi.location.core.ui.MainView;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements
        MainView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox allowLocationTracking;

    @Override
    MainPresenter createPresenter() {
        return new MainPresenter(getClientContext());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.open_maps_button);
        button.setOnClickListener(this);
        allowLocationTracking = findViewById(R.id.allow_location_tracking_checkbox);
        allowLocationTracking.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.open_maps_button)
            presenter.openMapsActivity();
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
    public void setUpTrackingSettings(boolean isTrackingAllowed) {
        allowLocationTracking.setChecked(isTrackingAllowed);
    }
}
