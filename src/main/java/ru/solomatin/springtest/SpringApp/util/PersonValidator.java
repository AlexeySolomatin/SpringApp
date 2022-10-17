package ru.solomatin.springtest.SpringApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.solomatin.springtest.SpringApp.models.Person;
import ru.solomatin.springtest.SpringApp.services.PersonalDetailsService;

@Component
public class PersonValidator  implements Validator {

    private final PersonalDetailsService personalDetailsService;

    @Autowired
    public PersonValidator(PersonalDetailsService personalDetailsService) {
        this.personalDetailsService = personalDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person)o;
        try {
            personalDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; //Всё хорошо, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким имеенм уже существует");
    }
}
