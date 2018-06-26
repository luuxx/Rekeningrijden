package lu.europe.communication;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommunicationApplication {

	public static final String queueName = "foreign_route_LU";
	public static final String queueName2 = "rich_route_LU";

	public static final String routeKeyBase = "rich_route_";

	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}

	@Bean
	Queue queue2() {
		return new Queue(queueName2, true);
	}

	public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CommunicationApplication.class, args);
	}

}

