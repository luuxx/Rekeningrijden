package lu.europe.communication.jms.rabbitmq;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String originCountry;
    private String drivenInCountry;
    private List<EULocation> locationList;

    public Route(String originCountry, String drivenInCountry) {
        this.originCountry = originCountry;
        this.drivenInCountry = drivenInCountry;
        this.locationList = new ArrayList();
    }

    public Route(){}

    public String getOriginCountry() {
        return this.originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getDrivenInCountry() {
        return this.drivenInCountry;
    }

    public void setDrivenInCountry(String drivenInCountry) {
        this.drivenInCountry = drivenInCountry;
    }

    public void addLocation(EULocation location) {
        this.locationList.add(location);
    }

    public List<EULocation> getLocationList() {
        return this.locationList;
    }

    public void setLocationList(List<EULocation> locationList) {
        this.locationList = locationList;
    }
}
