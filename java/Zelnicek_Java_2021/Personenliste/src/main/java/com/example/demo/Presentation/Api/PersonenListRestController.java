package com.example.demo.Presentation.Api;

import brave.Tracer;
import com.example.demo.Domain.Person;
import com.example.demo.Persistence.PersonenListRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/personenList")
@Slf4j
public class PersonenListRestController {


    private final PersonenListRepo personenlist;
    private final Tracer tracer;

    @GetMapping
    @NewSpan
    public HttpEntity<List<Person>> getAllPersons() {
        try {
            tracer.currentSpan().start();
            var response = ResponseEntity.ok(personenlist.findAll());
            List<Person> movies = personenlist.findAll();
            log.info(response.toString());

            return ResponseEntity.ok(movies);
        } finally {
            tracer.currentSpan().finish();
            ;
        }
    }

    @GetMapping("/{id}")
    @NewSpan
    public HttpEntity<Person> getPersonbyId(@PathVariable Long id) {
        try {
            tracer.currentSpan().start();
            var response = ResponseEntity.ok(personenlist.findById(id));
            log.info(response.toString());
            return personenlist.findById(id).map(ResponseEntity::ok).orElse(null);
        } finally {
            tracer.currentSpan().finish();
            ;
        }
    }

    @GetMapping("/delete/{id}")
    @NewSpan
    public ResponseEntity<Long> deletePersonById(@PathVariable Long id) {
        try {
            tracer.currentSpan().start();
            var response = ResponseEntity.ok(personenlist.findById(id));
            personenlist.deleteById(id);
            log.info(response.toString());

            return ResponseEntity.ok(id);
        } finally {
            tracer.currentSpan().finish();
            ;

        }

    }


    @Transactional(readOnly = false)
    @GetMapping("/{name}/{email}/{address}/{telNumber}/{city}")
    @NewSpan
    public Person createPerson(@PathVariable String name, @PathVariable String email, @PathVariable String address, @PathVariable String telNumber, @PathVariable String city) {
        try {

            Person p = Person.builder().name(name).address(address).email(email).telNumber(telNumber).city(city).build();
            tracer.currentSpan().start();
            var response = ResponseEntity.ok(p);
            log.info(response.toString());
            personenlist.save(p);
            return ResponseEntity.ok(p).getBody();

        } finally {
            tracer.currentSpan().finish();
            ;

        }

    }

    @Transactional(readOnly = false)
    @GetMapping("/Update/{id}/{name}/{email}/{address}/{telNumber}/{city}")
    @NewSpan
    public void update(@PathVariable Long id, @PathVariable String name, @PathVariable String email, @PathVariable String address, @PathVariable String telNumber, @PathVariable String city) {
        try {
            tracer.currentSpan().start();
            var response = ResponseEntity.ok(personenlist.findById(id));
            log.info(response.toString());
            personenlist.findById(id).map(p -> {
                p.setName(name);
                p.setAddress(address);
                p.setEmail(email);
                p.setTelNumber(telNumber);
                p.setCity(city);
                //  return p;
                return ResponseEntity.ok(p).getBody();
            }).map(personenlist::save);
        } finally {
            tracer.currentSpan().finish();
            ;
        }


    }


}


