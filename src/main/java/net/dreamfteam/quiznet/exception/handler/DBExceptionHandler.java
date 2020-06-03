package net.dreamfteam.quiznet.exception.handler;


import lombok.extern.slf4j.Slf4j;
import net.dreamfteam.quiznet.exception.ApplicationGlobalException;
import net.dreamfteam.quiznet.exception.DBException;
import net.dreamfteam.quiznet.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class DBExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DBException.class})
    public ResponseEntity<ErrorMessage> handleException(ApplicationGlobalException exception,
                                                        HttpServletRequest request) {

        log.error(String.format("Exception received, path: '%s'",
                request.getRequestURI()), exception);

        HttpStatus httpStatus = exception.getHttpStatus();

        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(httpStatus.value())
                .timestamp(System.currentTimeMillis())
                .error(exception.getClass().getName())
                .requestPath(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
