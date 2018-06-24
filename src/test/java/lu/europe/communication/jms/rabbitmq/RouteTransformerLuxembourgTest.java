package lu.europe.communication.jms.rabbitmq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouteTransformerLuxembourgTest {
    RouteTransformerLuxembourg routeTransformerLuxembourg = new RouteTransformerLuxembourg();
    Trip trip1;
    Trip trip2;
    @Before
    public void setUp() throws Exception {
        trip1 = new Trip();
        trip1.setId("car-001-c2");
        trip1.setOrigin("DE");
        trip1.setVehicleWeight(2345);
        List<EULocation> part = new ArrayList<>();
        part.add(new EULocation("car-001-c2",49.844855,6.096061));
        part.add(new EULocation("car-001-c2",49.844274,6.098325));
        List<List<EULocation>> trip = new ArrayList<>();
        trip.add(part);
        trip1.setTrips(trip);

        trip2 = new Trip();
        trip2.setId("car-001-c3");
        trip2.setOrigin("DE");
        trip2.setVehicleWeight(2345);
        List<EULocation> part2 = new ArrayList<>();
        part2.add(new EULocation("car-001-c3",49.844858,6.096565));
        part2.add(new EULocation("car-001-c3",49.844274,6.098325));
        List<List<EULocation>> tripp2 = new ArrayList<>();
        tripp2.add(part2);
        trip2.setTrips(tripp2);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Give transformer a trip that then transforms the trip into a RichRout to send back to country.
     */
    @Test
    public void generateRichRoute() {
        RichRoute richRoute = routeTransformerLuxembourg.generateRichRoute(trip1);
        assertTrue(richRoute.getDistance() == 174);
        assertTrue(richRoute.getDetails().size() == 1);
        assertTrue(richRoute.getDetails().get(0).getRate() == 0.02);
        assertTrue(richRoute.getDetails().get(0).getDescription() == "Normal rate");
        assertTrue(richRoute.getId() == "car-001-c2");
    }

    @Test
    public void generateRichRouteFail() {
        RichRoute richRoute2 = routeTransformerLuxembourg.generateRichRoute(trip2);
        assertFalse(richRoute2.getDistance() == 174);
        assertTrue(richRoute2.getDetails().size() == 1);
        assertTrue(richRoute2.getDetails().get(0).getRate() == 0.02);
        assertTrue(richRoute2.getDetails().get(0).getDescription() == "Normal rate");
        assertFalse(richRoute2.getId() == "car-001-c2");
    }
}