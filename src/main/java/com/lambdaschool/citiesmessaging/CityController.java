package com.lambdaschool.citiesmessaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@RestController
public class CityController {
    private final CityRepository cityRepo;
    private final RabbitTemplate rt;
    private final int HIGH_PRIORITY = 10;
    private final int MED_PRIORITY = 6;
    private final int LOW_PRIORITY = 3;

    public CityController(CityRepository cityRepo, RabbitTemplate rt) {
        this.cityRepo = cityRepo;
        this.rt = rt;
    }


    @GetMapping("/cities/afford")
    public void findAffordable() {
        ArrayList<City> cities = new ArrayList<>();
        cities.addAll(cityRepo.findAll());

        HashMap<Integer, Boolean> secretIndexes = new HashMap<>();

        //sending messages to secret queue
        for(int i = 0; i < cities.size(); i++) {
            int rand = new Random().nextInt(10);
            if(rand >= 7) {
                secretIndexes.put(i, true);
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_SECRET, new CityMessage(cities.get(i).toString(), HIGH_PRIORITY, true));
            }
        }

        for(int i = 0; i < cities.size(); i++) {
            //skip secret messages
            if(secretIndexes.get(i) == true) {
                continue;
            }

            City city = cities.get(i);

            if(city.getAffordIndex() < 6) {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES1, new CityMessage(city.toString(), MED_PRIORITY, false));
            }
            else {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES2, new CityMessage(city.toString(), LOW_PRIORITY, false));
            }
        }
    }

    @GetMapping("/cities/homes")
    public void findByHomePrices() {
        ArrayList<City> cities = new ArrayList<>();
        cities.addAll(cityRepo.findAll());

        HashMap<Integer, Boolean> secretIndexes = new HashMap<>();

        //sending messages to secret queue
        for(int i = 0; i < cities.size(); i++) {
            int rand = new Random().nextInt(10);
            if(rand >= 7) {
                secretIndexes.put(i, true);
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_SECRET, new CityMessage(cities.get(i).toString(), HIGH_PRIORITY, true));
            }
        }

        for(int i = 0; i < cities.size(); i++) {
            //skip secret messages
            if(secretIndexes.get(i) == true) {
                continue;
            }

            City city = cities.get(i);

            if(city.getPrice() > 200000) {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES1, new CityMessage(city.toString(), MED_PRIORITY, false));
            }
            else {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES2, new CityMessage(city.toString(), LOW_PRIORITY, false));
            }
        }
    }

    @GetMapping("/cities/names")
    public void findByHomeNames() {

        ArrayList<City> cities = new ArrayList<>();
        cities.addAll(cityRepo.findAll());

        for(City city: cities) {

            int rand = new Random().nextInt(11);

            if(rand > 6) {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_SECRET, new CityMessage(city.toString(), HIGH_PRIORITY, true));
            }
            else if (rand <= 6 && rand >= 4) {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES1, new CityMessage(city.toString(), MED_PRIORITY, false));
            }
            else {
                rt.convertAndSend(CitiesMessagingApplication.QUEUE_NAME_CITIES2, new CityMessage(city.toString(), LOW_PRIORITY, false));
            }
        }
    }
}
