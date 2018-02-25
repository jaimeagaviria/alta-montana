package com.altamontana.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebHomeController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("/page/recetas")
    public String recetas() {
        return "recetas";
    }

    @RequestMapping("/page/bitacora")
    public String bitacora() {
        return "bitacora";
    }

    @RequestMapping("/page/monitorear")
    public String monitorear() {
        return "monitorear";
    }
}
