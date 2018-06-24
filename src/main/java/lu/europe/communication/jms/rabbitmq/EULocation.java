package lu.europe.communication.jms.rabbitmq;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EULocation extends com.nonexistentcompany.domain.EULocation {

    private double lat;
    @JsonProperty("lng")
    private double lon;
    @JsonProperty("timestamp")
    private long unixTimestamp;
    @JsonIgnore
    private String license;

    @JsonCreator
    public EULocation(@JsonProperty("license") String license,@JsonProperty("lat") double lat,@JsonProperty("lng") double lon) {

        super(license, lat, lon);
    }

    public EULocation(String license, double lat, double lon, long unixTimestamp) {
        super(license, lat, lon, unixTimestamp);
    }

    public double getLat() {
        return super.getLat();
    }

    public void setLat(double lat) {
        super.setLat(lat);
    }

    public double getLon() {
        return super.getLon();
    }

    public void setLon(double lon) {
        super.setLon(lon);
    }

    public long getUnixTimestamp() {
        return super.getUnixTimestamp();
    }

    public void setUnixTimestamp(long unixTimestamp) {
        super.setUnixTimestamp(unixTimestamp);
    }

    public String getLicense() {
        return super.getLicense();
    }

    public void setLicense(String license) {
        super.setLicense(license);
    }

    public int compareTo(EULocation o) {
        return Long.compare(getUnixTimestamp(), o.unixTimestamp);
    }

    public String toString() {
        return "Location{lat=" + getLat() + ", lon=" + getLon() + ", unixTimestamp=" + getUnixTimestamp() + '}';
    }
}
