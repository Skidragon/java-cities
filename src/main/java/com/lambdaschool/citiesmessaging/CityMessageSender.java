package com.lambdaschool.citiesmessaging;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CityMessageSender {
    private final RabbitTemplate rt;
    private final CityRepository cityRepo;

    public CityMessageSender(RabbitTemplate rt, CityRepository cityRepo) {
        this.rt = rt;
        this.cityRepo = cityRepo;
    }

}
