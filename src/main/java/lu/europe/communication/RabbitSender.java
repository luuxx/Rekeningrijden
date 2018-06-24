package lu.europe.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lu.europe.communication.jms.rabbitmq.RichRoute;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class RabbitSender {
    private final RabbitTemplate rabbitTemplate;

    Logger logger = Logger.getLogger("Rabbit Sender Logger");
    FileHandler fh;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate) throws IOException {
        fh = new FileHandler("./SenderLog.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(RichRoute order,String landcode) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(order);
        Message message = MessageBuilder
                .withBody(orderJson.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
        logger.log(Level.INFO,message.toString());
        this.rabbitTemplate.convertAndSend(CommunicationApplication.routeKeyBase +landcode, message);
    }
}

