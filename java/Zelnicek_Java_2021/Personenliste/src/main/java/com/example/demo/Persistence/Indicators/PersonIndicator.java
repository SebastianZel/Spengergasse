package com.example.demo.Persistence.Indicators;

import com.example.demo.Persistence.PersonenListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("healthrepository")
public class PersonIndicator implements HealthIndicator {

    private final PersonenListRepo pRepo;
    @Override
    public Health health() {
        if(repositoryHealthCheck(pRepo)){
            return Health.up().build();
        }
        else{
            return Health.down().build();
        }
    }

    private boolean repositoryHealthCheck(PersonenListRepo pRepo) {
        try{
            pRepo.findAll();
            return true;
        }catch (Exception exception){
            return false;
        }
    }
}
