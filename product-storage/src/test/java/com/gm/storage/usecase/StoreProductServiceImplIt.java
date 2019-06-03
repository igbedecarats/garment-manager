package com.gm.storage.usecase;

import com.gm.storage.components.product.domain.Product;
import com.gm.storage.components.product.domain.ProductRepository;
import com.gm.storage.components.product.usecase.ProductDto;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = StoreProductServiceImplIt.Initializer.class)
@ActiveProfiles("test")
public class StoreProductServiceImplIt {

    @TestConfiguration
    public static class CustomizeConfig {

        @Value("${rabbitmq.queue}")
        private String queueName;

        @Bean
        public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                             Jackson2JsonMessageConverter producerJackson2MessageConverter) {
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(producerJackson2MessageConverter);
            return rabbitTemplate;
        }

        @Bean
        public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public Queue queue() {
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", StringUtils.EMPTY);
            return new Queue(queueName, true, false, false, args);
        }
    }

    @ClassRule
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management")
            .withExposedPorts(5672, 15672);

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void it_should_handle_sent_message_successfully() {
        //Given
        ProductDto dto = ProductDtoMother.createNiteJoggerShoes();

        //When
        rabbitTemplate.convertAndSend(queueName, dto);

        //Then
        Product product = productRepository.findAll().stream().filter(p -> p.getId().equals(dto.getId())).findFirst().get();

        assertEquals(dto.getId(), product.getId());

    }

    private Callable<Boolean> isUserConsumedAsync() {
        return () -> outputCapture.toString().contains("Consumed user");
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getContainerIpAddress(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672),
                    "spring.rabbitmq.user=" + "guest",
                    "spring.rabbitmq.password=" + "guest",
                    "spring.rabbitmq.virtual-host=" + "/"
            );
            values.applyTo(configurableApplicationContext);
        }

    }
}
