package com.gm.storage.components.product.usecase;

import com.gm.storage.components.product.domain.ProductRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreProductServiceImpl implements StoreProductService {

    private ProductRepository productRepository;

    private static final Log logger = LogFactory.getLog(StoreProductServiceImpl.class);

    @Autowired
    public StoreProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @RabbitListener(queues="${rabbitmq.queue}", containerFactory="rabbitMQFactory")
    public void store(ProductDto dto) {
        logger.info("Storing product " + dto);
        productRepository.save(dto.toEntity());
    }
}
