package testapp.sliubetskyi.location.core.model.maps;

import java.util.Objects;

/**
 * Holds user location coordinates.
 */
public class LocationData {
    public final double lat;
    public final double lng;

    public LocationData(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationData)) return false;
        LocationData that = (LocationData) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }
}
