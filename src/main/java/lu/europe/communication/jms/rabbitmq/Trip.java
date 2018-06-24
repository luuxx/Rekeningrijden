package lu.europe.communication.jms.rabbitmq;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Trip {

    @Getter @Setter
    private String id;

    @Getter @Setter
    private int vehicleWeight;

    @Getter @Setter
    private List<List<EULocation>> trips;

    @Getter @Setter
    private String origin;

    public Trip() {
    }
}
