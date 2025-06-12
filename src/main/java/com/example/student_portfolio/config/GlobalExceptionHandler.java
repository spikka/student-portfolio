package com.example.student_portfolio.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView onError(Exception ex, Model model) {
        ex.printStackTrace();                // <-- выведет полный стектрейс в консоль
        ModelAndView mv = new ModelAndView("error");
        mv.addObject("exception", ex);
        return mv;
    }
}
