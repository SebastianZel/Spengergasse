package com.example.demo.Persistence;

import com.example.demo.Domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PersonenListRepo extends JpaRepository<Person, Long> {

    List<Person> findAllByName(String title);

    List<Person> findAll();

    List<Person> findAllByAddress(String address);

    List<Person> findAllByEmail(String email);

    List<Person> findAllByTelNumber(String number);

    List<Person> findAllByCity(String email);

    public Person save(Person save);

}
