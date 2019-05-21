package com.gm.producer.components.product.usecase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublishProductServiceImpl implements PublishProductService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.exchange}")
    String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    private static final Log logger = LogFactory.getLog(PublishProductServiceImpl.class);

    @Override
    public void publish(ProductDto dto) {
        logger.info("Publishing dto " + dto);
        try {
            amqpTemplate.convertAndSend(exchange, routingkey, dto);

        } catch (AmqpException e) {
            throw new MessagingException(e.getMessage(), e.getCause());
        }
    }
}
