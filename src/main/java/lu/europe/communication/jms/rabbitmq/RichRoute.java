package lu.europe.communication.jms.rabbitmq;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RichRoute {

    @Getter @Setter
    private String id;
    @Getter @Setter
    private String origin;

    @Getter @Setter
    private double price;

    @Getter @Setter
    private int distance;
    @Getter @Setter
    private int vat;

    @Getter @Setter
    private List<Detail> details;

    public RichRoute(){}

    public RichRoute(String id, String origin, double price, int distance, int vat, List<Detail> details) {
        this.id = id;
        this.origin = origin;
        this.price = price;
        this.distance = distance;
        this.vat = vat;
        this.details = details;
    }
}
