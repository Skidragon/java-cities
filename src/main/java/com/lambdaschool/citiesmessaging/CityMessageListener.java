package com.lambdaschool.citiesmessaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityMessageListener {

//    @RabbitListener(queues = CitiesMessagingApplication.QUEUE_NAME_HIGH)
//    public void receiveMessage(CityMessage cm) {
//        log.info("Received Message: {} ", cm.toString());
//    }
}
