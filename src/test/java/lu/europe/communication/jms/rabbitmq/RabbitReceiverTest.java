package lu.europe.communication.jms.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lu.europe.communication.CommunicationApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitReceiverTest {

    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    long unixTimestamp;

    Trip trip1;
    @Before
    public void setUp() throws Exception {
        trip1 = new Trip();
        trip1.setId("car-001-c2");
        trip1.setOrigin("DE");
        trip1.setVehicleWeight(2345);
        List<EULocation> part = new ArrayList<>();
        part.add(new EULocation("car-001-c2",49.844855,6.096061));
        part.add(new EULocation("car-001-c2",49.844274,6.098325));
        unixTimestamp = part.get(0).getUnixTimestamp();
        List<List<EULocation>> trip = new ArrayList<>();
        trip.add(part);
        trip1.setTrips(trip);
    }

    @Test
    public void receiveMessageSuc() throws InterruptedException, JsonProcessingException {
        List<Detail> details = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(trip1);
        Message message = MessageBuilder
                .withBody(orderJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();

        this.rabbitTemplate.convertAndSend(CommunicationApplication.queueName, message);

        RabbitListenerTestHarness.InvocationData invocationData = this.harness.getNextInvocationDataFor("r1", 10, TimeUnit.SECONDS);
        Object[] args = invocationData.getArguments();
        assertEquals(new String((byte[]) args[0]), orderJson);
    }


}

