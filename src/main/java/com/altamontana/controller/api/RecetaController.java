package com.altamontana.controller.api;

import com.altamontana.domain.Receta;
import com.altamontana.service.AbstractService;
import com.altamontana.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recetas")
public class RecetaController extends AbstractRestController<Receta,Integer>{

    @Autowired
    public RecetaController(RecetaService recetaService) {
        super(recetaService);
    }

    @Override
    Class<Receta> getTClass() {
        return Receta.class;
    }
}
