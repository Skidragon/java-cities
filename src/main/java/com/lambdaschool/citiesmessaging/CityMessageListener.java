package com.lambdaschool.citiesmessaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityMessageListener {

    @RabbitListener(queues = CitiesMessagingApplication.QUEUE_NAME_SECRET)
    public void receiveSecretMessages(CityMessage cm) {
        log.info("Received Message: {} ", cm.toString());
    }
    @RabbitListener(queues = CitiesMessagingApplication.QUEUE_NAME_CITIES1)
    public void receiveCities1Messages(CityMessage cm) {
        log.info("Received Message: {} ", cm.toString());
    }
    @RabbitListener(queues = CitiesMessagingApplication.QUEUE_NAME_CITIES2)
    public void receiveCities2Messages(CityMessage cm) {
        log.info("Received Message: {} ", cm.toString());
    }

}
