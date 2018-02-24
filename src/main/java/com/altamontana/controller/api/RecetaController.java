package com.altamontana.controller.api;

import com.altamontana.domain.Receta;
import com.altamontana.service.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recetas")
public class RecetaController extends AbstractRestController<Receta,Integer>{

    public RecetaController(AbstractService<Receta, Integer> genericService) {
        super(genericService);
    }

    @Override
    Class<Receta> getTClass() {
        return Receta.class;
    }
}
