package testapp.sliubetskyi.core.model.maps;

/**
 * Holds user location coordinates and accuracy.
 */
public class LocationData {
    public final double lat;
    public final double lng;
    public final float accuracy;

    public LocationData(double lat, double lng, float accuracy) {
        this.lat = lat;
        this.lng = lng;
        this.accuracy = accuracy;
    }
}
