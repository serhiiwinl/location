package testapp.sliubetskyi.location.android.activities;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.maps.LocationData;
import testapp.sliubetskyi.location.core.presenters.MapsPresenter;
import testapp.sliubetskyi.location.core.ui.MapsView;

public class MapsActivity extends BaseActivity<MapsPresenter, MapsView> implements OnMapReadyCallback, MapsView {

    private GoogleMap map;
    private Marker marker;
    private String defaultMarkerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        defaultMarkerTitle = getString(R.string.current_unknown);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        if (map != null)
            presenter.saveCameraParams(map.getCameraPosition().zoom);
        super.onPause();
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
    public void openMapWithParams(StringsIds markerName, LocationData locationData, float cameraZoom) {
        if (map == null)
            return;
        // Add a marker in Sydney and move the camera
        LatLng coordinates = new LatLng(locationData.lat, locationData.lng);
        updateMarker(getString(markerName), coordinates);

        if (cameraZoom < 0)
            cameraZoom = map.getCameraPosition().zoom;

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, cameraZoom);
        map.moveCamera(cameraUpdate);
    }

    @Override
    public void onLocationChanged(LocationData location) {
        updateCoordinatesOnMap(location);
    }

    private void updateCoordinatesOnMap(LocationData locationData) {
        if (map == null)
            return;
        LatLng coordinates = new LatLng(locationData.lat, locationData.lng);
        updateMarker(defaultMarkerTitle, coordinates);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, map.getCameraPosition().zoom);
        map.moveCamera(cameraUpdate);
    }

    private void updateMarker(String markerTitle, LatLng coordinates) {
        if (marker != null) {
            marker.setTitle(markerTitle);
            marker.setPosition(coordinates);
        } else {
            marker = map.addMarker(new MarkerOptions().position(coordinates).title(markerTitle));
        }
    }
}
