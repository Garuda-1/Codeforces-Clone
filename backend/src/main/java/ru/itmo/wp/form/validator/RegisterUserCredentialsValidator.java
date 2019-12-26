package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.RegisterUserCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class RegisterUserCredentialsValidator implements Validator {
    private final UserService userService;

    public RegisterUserCredentialsValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return RegisterUserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            RegisterUserCredentials registerForm = (RegisterUserCredentials) target;
            if (userService.findByLoginAndPassword(registerForm.getLogin(), registerForm.getPassword()) != null) {
                errors.reject("password.login-in-user", "Login is already in use.");
            }
        }
    }
}
