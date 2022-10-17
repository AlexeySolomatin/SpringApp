package ru.solomatin.springtest.SpringApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.solomatin.springtest.SpringApp.models.Person;
import ru.solomatin.springtest.SpringApp.repositories.PeopleRepository;
import ru.solomatin.springtest.SpringApp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonalDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonalDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException(("User not found!"));
        return new PersonDetails(person.get());
    }
}
