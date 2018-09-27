package testapp.sliubetskyi.core.model.maps;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationData)) return false;
        LocationData that = (LocationData) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lng, lng) == 0 &&
                Float.compare(that.accuracy, accuracy) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng, accuracy);
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", accuracy=" + accuracy +
                '}';
    }
}
