package lu.europe.communication.jms.rabbitmq;

import com.nonexistentcompany.RouteEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouteTransformerLuxembourg {

    private final String LAND_CODE = "LU";

    /**
     * Creates the route used for communication via queue between two countries. It will
     * convert a Rout containing a list of points to a RichRout that contains all the rate information for that Rout.
     * @param route The rout that needs to be converted.
     * @return RichRoute with the costs and rates added
     */
    public RichRoute generateRichRoute(Trip route) {

        double totalDistance = 0;

        //Creating the list of rates
        List<Detail> rates = new ArrayList<>();

        for(List<EULocation> sortedList: route.getTrips()) {

            double distance = 0;

            //Starting engine pretending to be Germany for testing reasons
            RouteEngine engine = new RouteEngine(LAND_CODE);

            //Looping trough the list to calculate the amount KM driven.
            for (int index = 0; index < sortedList.size(); index++) {
                if (sortedList.size() - 1 == index) break;
                EULocation location = sortedList.get(index);
                EULocation nextLocation = sortedList.get(index + 1);
                if (engine.isLocationInOwnCountry(location.getLat(), location.getLon()) && engine.isLocationInOwnCountry(nextLocation.getLat(), nextLocation.getLon())) {
                    distance += LatLongToKM(location.getLat(), location.getLon(), nextLocation.getLat(), nextLocation.getLon());
                }
            }

            //Adding the only rate for Luxembourg
            rates.add(new Detail(0.02,"Normal rate",System.currentTimeMillis()-(new Random().nextInt(3600000)),System.currentTimeMillis(),distance));

            totalDistance += distance;
        }

        //Calculate the price
        double price = 0;

        //Looping trough each rate and the corresponding distance to calculate total price
        for (Detail rate: rates) {
            price += (rate.getDistance() * rate.getRate()) / 1000;
        }
        return new RichRoute(route.getId(),LAND_CODE,price,(int)totalDistance,18,rates);
    }


    /**
     * Return the distance of to points on a sphere using the Haversine formula.
     * @param lat1 Latitude of point 1
     * @param long1 Longitude of point 1
     * @param lat2 Latitude of point 2
     * @param long2 Longitude of point 2
     * @return distance in KM measured from point 1 to point 2
     */
    private double LatLongToKM(double lat1, double long1, double lat2, double long2){
        double radius = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = long2 * Math.PI / 180 - long1 * Math.PI / 180;
        double sideA = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(sideA), Math.sqrt(1-sideA));
        return (radius * c) * 1000; //distance in KM
    }
}
