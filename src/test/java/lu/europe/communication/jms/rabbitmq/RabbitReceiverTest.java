package lu.europe.communication.jms.rabbitmq;

import lu.europe.communication.CommunicationApplication;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class RabbitReceiverTest {

    @Autowired
    private RabbitListenerTestHarness harness;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void receiveMessage() throws InterruptedException {
        this.rabbitTemplate.convertAndSend(CommunicationApplication.queueName, "bar");

        RabbitListenerTestHarness.InvocationData invocationData = this.harness.getNextInvocationDataFor("bar", 10, TimeUnit.SECONDS);
        Object[] args = invocationData.getArguments();
        assertEquals(args[0], "bar");
        assertEquals(args[1], CommunicationApplication.queueName);
    }
}

