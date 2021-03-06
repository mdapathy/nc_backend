package net.dreamfteam.quiznet.web.controllers;

import lombok.extern.slf4j.Slf4j;
import net.dreamfteam.quiznet.configs.constants.Constants;
import net.dreamfteam.quiznet.data.entities.Role;
import net.dreamfteam.quiznet.data.entities.User;
import net.dreamfteam.quiznet.exception.ValidationException;
import net.dreamfteam.quiznet.service.ActivationService;
import net.dreamfteam.quiznet.service.SettingsService;
import net.dreamfteam.quiznet.service.UserService;
import net.dreamfteam.quiznet.web.dto.DtoUser;
import net.dreamfteam.quiznet.web.dto.DtoUserSignUp;
import net.dreamfteam.quiznet.web.dto.LoginRequest;
import net.dreamfteam.quiznet.web.dto.UserLoginSuccessResponse;
import net.dreamfteam.quiznet.web.validators.LoginRequestValidator;
import net.dreamfteam.quiznet.web.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.websocket.server.PathParam;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping(Constants.SIGN_UP_URLS)
public class AuthorizationController {

    @Value("${activation.redirect.url}")
    private String activationRedirectUrl;

    private final SettingsService settingsService;

    final private UserService userService;
    final private ActivationService activationService;

    @Autowired
    public AuthorizationController(UserService userService, ActivationService activationService,
                                   SettingsService settingsService) {
        this.userService = userService;
        this.activationService = activationService;
        this.settingsService = settingsService;
    }

    @PostMapping("/log-in")
    public ResponseEntity<UserLoginSuccessResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        LoginRequestValidator.validate(loginRequest);

        User currentUser = userService.getByEmail(loginRequest.getUsername());

        if (currentUser == null) {
            currentUser = userService.getByUsername(loginRequest.getUsername());
        }

        if (currentUser == null) {
            throw new ValidationException(Constants.USER_NOT_FOUND_WITH_EMAIL_OR_USERNAME + loginRequest.getUsername());
        }

        if (!currentUser.isActivated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        userService.checkCorrectPassword(currentUser, loginRequest.getPassword());

        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(activationService.isUserVerified(currentUser))
                .success(true).build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<DtoUser> registerUser(@RequestBody DtoUserSignUp user,
                                                @RequestHeader("Lang") String language) throws ValidationException {
        UserValidator.validate(user);
        User saved = userService.save(user.toUser());
        settingsService.initSettings(saved.getId(), Role.ROLE_USER, language);

        return new ResponseEntity<>(DtoUser.fromUser(saved), HttpStatus.CREATED);
    }

    @GetMapping("/activation")
    public RedirectView activate(@PathParam("key") String key) {

        return new RedirectView(activationRedirectUrl + activationService.verifyUser(key));
    }

    @PostMapping("/anonym")
    public ResponseEntity<UserLoginSuccessResponse> getAnonymToken(@RequestParam("username") String username) {
            UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                    .token(activationService.getAnonymToken(username))
                    .success(true).build();

            return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}

