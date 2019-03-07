package backend.continent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ExceptionsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ContinentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String continentNotFoundHandler(ContinentNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String countryNotFoundHandler(CountryNotFoundException ex) {
        return ex.getMessage();
    }
}
