package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.util.exception.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sergii on 24.02.2017.
 */
@ControllerAdvice
public class ExceptionControllerHandler {

        @ExceptionHandler
        public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
            ModelAndView mav = new ModelAndView("notfound");
            mav.addObject("exception", e);
            return mav;
        }

}
