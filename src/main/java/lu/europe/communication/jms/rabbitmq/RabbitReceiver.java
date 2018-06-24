package lu.europe.communication.jms.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lu.europe.communication.CommunicationApplication;
import lu.europe.communication.RabbitSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitReceiver {

    private CountDownLatch latch = new CountDownLatch(1);
    private ObjectMapper mapper = new ObjectMapper();

    private RouteTransformerLuxembourg routeTransformerLuxembourg = new RouteTransformerLuxembourg();

    private RabbitSender sender;

    RabbitReceiver(RabbitSender sender){
        this.sender = sender;
    }


    @RabbitListener(queues = CommunicationApplication.queueName)
    public void receiveMessage(byte[] message) {
        String stringMessage = new String(message);
        System.out.println("Received <" + stringMessage + ">");
        try {
            Trip route = mapper.readValue(stringMessage,Trip.class);
            RichRoute richRoute = routeTransformerLuxembourg.generateRichRoute(route);
            sender.sendOrder(richRoute,route.getOrigin());
        } catch (IOException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
