package lu.europe.communication.jms.rabbitmq;

import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@RabbitListenerTest(spy = false, capture = true)
public class Config {
    //This is suppose to be empty.
}
