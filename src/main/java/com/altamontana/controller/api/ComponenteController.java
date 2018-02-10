package com.altamontana.controller.api;

import com.altamontana.domain.Componente;
import com.altamontana.service.AbstractService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/componentes")
public class ComponenteController extends AbstractRestController<Componente,Integer>{

    public ComponenteController(AbstractService<Componente, Integer> genericService) {
        super(genericService);
    }

    @Override
    Class<Componente> getTClass() {
        return null;
    }
}
