package de.tum.in.ase.eist.rest;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.service.PersonService;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    // TODO Part 1: Implement the specified endpoints here
   @PostMapping("persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (person.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.savePerson(person));
    }

    @PutMapping("persons/{personId}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatePerson, @PathVariable("personId")UUID personId) {
        if (!updatePerson.getId().equals(personId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.savePerson(updatePerson));
    }

    @DeleteMapping("persons/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable("personId") UUID personId) {
        personService.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("persons")
    public ResponseEntity<List<Person>> getAllPersons(
            @RequestParam(required = false)PersonSortingOptions.SortField sortField,
            @RequestParam(required = false) PersonSortingOptions.SortingOrder sortingOrder) {
//        PersonSortingOptions sortingOptions = new PersonSortingOptions();
        if(sortField == null && sortingOrder== null) {
            return ResponseEntity.ok(personService.getAllPersons(new PersonSortingOptions()));
        }
        if(sortingOrder == null) {
            return ResponseEntity.ok(personService.getAllPersons(new PersonSortingOptions(sortField)));
        }
        return ResponseEntity.ok(personService.getAllPersons(new PersonSortingOptions(sortingOrder, sortField)));
    }
}
