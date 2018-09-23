package testapp.sliubetskyi.location.android.activities;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.core.presenters.MapsPresenter;
import testapp.sliubetskyi.location.core.model.data.LocationData;
import testapp.sliubetskyi.location.core.ui.MapsView;

public class MapsActivity extends BaseActivity<MapsPresenter, MapsView> implements
        OnMapReadyCallback, MapsView {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    MapsPresenter createPresenter() {
        return new MapsPresenter(getClientContext());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        presenter.openMap();
    }

    @Override
    public void openMapOnCoordinates(String title, LocationData locationData) {
        // Add a marker in Sydney and move the camera
        LatLng coordinates = new LatLng(locationData.lat, locationData.lng);
        map.addMarker(new MarkerOptions().position(coordinates).title(title));
        map.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
    }
}
