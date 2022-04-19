package by.tms.gymprogect.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RuntimeExceptionControllerAdvice {

    private Logger logger = LogManager.getLogger(RuntimeExceptionControllerAdvice.class);

    /**
     * Catch runtime exception, add exception message in model and get page error
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleError(Model model, RuntimeException exception){
        logger.error("Exception message: {}", exception.getMessage());
        model.addAttribute("exceptionMessage", exception.getMessage());
        return "error";
    }
}
