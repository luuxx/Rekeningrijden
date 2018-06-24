package lu.europe.communication.jms.rabbitmq;

import lombok.Getter;
import lombok.Setter;

public class Detail {

    @Getter @Setter
    private double rate;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double distance;

    @Getter @Setter
    private long start;
    @Getter @Setter
    private long end;

    public Detail(double rate, String description, long start, long end, double distance) {
        this.rate = rate;
        this.description = description;
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public Detail() {
    }
}
