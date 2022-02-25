package com.answerdigital.colourstest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;

@RestController
@RequestMapping("/People")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        List<Person> persons = peopleRepository.findAll(); 

        if(persons != null) return new ResponseEntity<>(persons, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id); 
        Person person = new Person();

        if (optionalPerson.isPresent()) {
            person = optionalPerson.get();
            return new ResponseEntity<>(person, HttpStatus.OK); 
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        Optional<Person> optionalPerson = peopleRepository.findById(id); 
        Person person = new Person();

        if (optionalPerson.isPresent()) {
            person = optionalPerson.get();
            person.setAuthorised(personUpdate.isAuthorised());
            person.setEnabled(personUpdate.isEnabled());
            person.setColours(personUpdate.getColours());
    
    
            peopleRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
           } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
