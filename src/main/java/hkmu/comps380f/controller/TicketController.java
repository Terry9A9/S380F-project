/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hkmu.comps380f.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 *
 * @author Terry
 */
@Controller
@RequestMapping("/ticket")

public class TicketController {
    @GetMapping(value = {"","/list"})
    public String list(ModelMap model) {
        return "list";
    }

    @GetMapping(value = {"","/test"})
    public ModelAndView test(){
        return new ModelAndView("test") {
        };
    }

}
