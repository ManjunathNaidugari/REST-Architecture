package de.tum.in.ase.eist.service;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class PersonService {
  	// do not change this
    private final List<Person> persons;

    public PersonService() {
        this.persons = new ArrayList<>();
    }

    public Person savePerson(Person person) {
        var optionalPerson = persons.stream().filter(existingPerson -> existingPerson.getId().equals(person.getId())).findFirst();
        if (optionalPerson.isEmpty()) {
            person.setId(UUID.randomUUID());
            persons.add(person);
            return person;
        } else {
            var existingPerson = optionalPerson.get();
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setBirthday(person.getBirthday());
            return existingPerson;
        }
    }

    public void deletePerson(UUID personId) {
        this.persons.removeIf(person -> person.getId().equals(personId));
    }

    public List<Person> getAllPersons(PersonSortingOptions sortingOptions) {
        // TODO Part 3: Add sorting here
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (sortingOptions.getSortingOrder() == PersonSortingOptions.SortingOrder.ASCENDING) {
                   if (sortingOptions.getSortField() == PersonSortingOptions.SortField.ID) {
                       return o1.getId().compareTo(o2.getId());
                   } else if (sortingOptions.getSortField() == PersonSortingOptions.SortField.BIRTHDAY) {
                       return o1.getBirthday().compareTo(o2.getBirthday());
                   } else if (sortingOptions.getSortField() == PersonSortingOptions.SortField.FIRST_NAME) {
                       return o1.getFirstName().compareTo(o2.getFirstName());
                   } else {
                       return o1.getLastName().compareTo(o2.getLastName());
                   }
                } else {
                    if (sortingOptions.getSortField() == PersonSortingOptions.SortField.ID) {
                        return o2.getId().compareTo(o1.getId());
                    } else if (sortingOptions.getSortField() == PersonSortingOptions.SortField.BIRTHDAY) {
                        return o2.getBirthday().compareTo(o1.getBirthday());
                    } else if (sortingOptions.getSortField() == PersonSortingOptions.SortField.FIRST_NAME) {
                        return o2.getFirstName().compareTo(o1.getFirstName());
                    } else {
                        return o2.getLastName().compareTo(o1.getLastName());
                    }
                }
            }
        });
//        persons.sort(Comparator.comparing(sortingOptions.getSortField(),sortingOptions.getSortingOrder()));
//        persons.stream().filter(person -> person.toString().equals(sortingOptions.getSortField().name())).forEach(list::add);
//        persons.stream().filter(person -> person.getId().equals(sortingOptions.getSortField().)).forEach(list::add);
//        persons.stream().filter(person -> sortingOptions.getSortField().name().equals(person.getFirstName())).forEach(list::add);
//        return new ArrayList<>(this.persons);
        return persons;
    }
}
